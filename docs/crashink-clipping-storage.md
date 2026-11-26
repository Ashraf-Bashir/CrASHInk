# Shared clipping format (CrossInk v2 / crASHink)

## Quote core

crASHink now writes the exact CrossInk v7 binary core documented in
[file-formats.md](file-formats.md#crosspointclippingsbooktype_crc32pathbin):
version byte 7, u16 count, title/author/book-path strings, scoped deletion IDs,
then standard page/word/source positions, timestamps, sync keys/flags, chapter
labels and u16-length quote text. There are no extra fields or trailing data
inside the core. A pinned, unmodified stock v7 reader body is used in native
compatibility tests.

Files stay under `/.crashink/.clippings/epub_<crc32(path)>.bin`, following the
user's consolidated SD layout. Stock CrossInk discovers files under
`/.crosspoint/clippings/` instead. To use a crASHink file in stock firmware,
copy the `.bin` there with its existing filename and keep the EPUB at the same
path. The format is shared; the two firmware directories are not live mirrors.
When crASHink has no local file for a book, opening that book validates and
imports its stock v7 file without changing the stock source. An existing local
file wins; the importer never silently overwrites a separate crASHink history.

Stock's current implementation limits books to 256 quotes and quotes to 4 KiB.
crASHink retains 65,535 quotes and 60 KiB per quote, subject to allocation and
SD capacity. Stock firmware may reject files over its limits; identical bytes
do not bypass its guards. No claim of literally unlimited storage is made.

## Colors and notes (companion schema 1)

A companion is `core.bin.meta-<64 lowercase SHA256 hex characters>`. The name
contains the SHA256 of the entire associated core. A 32-byte `core.bin.meta`
pointer identifies the last committed companion for matching after a stock
rewrite. Companion files are not clipping `.bin` files and are not listed as
books or clipping databases.

All numbers are little-endian. The companion contains:

- u32 magic `0x314d4343`, u8 schema 1, u16 record count, 32-byte core SHA256.
- One u16 sorted-entry rank per core record, in core order.
- Fixed 39-byte entries sorted by identity: SHA256 identity (32 bytes), color
  u8, note length u16, absolute note offset u32.
- Exact UTF-8 note bytes, at most 1,024 bytes per note.
- A trailing SHA256 of all preceding companion bytes.

Quote identity hashes little-endian timestamp u32, spine u16, then the exact
UTF-8 quote text. The paired fast path reads metadata by ordinal without
hashing individual quotes. If stock added, removed or reordered records, the
reader streams each quote through SHA256 and binary-searches the companion;
metadata never follows a row number. New or text-changed stock quotes default
to Yellow with no note. Ambiguous duplicate identities are not assigned a
possibly incorrect annotation, and the prior companion is retained for recovery.
Stock firmware neither displays nor modifies these companion files.

Metadata is loaded once with the book index; page turns use cached colors and
note offsets. Hashing uses a 128-byte streaming buffer, not a whole-quote copy.
Saves use a checked temporary offset/index allocation, approximately 56 bytes
plus a two-byte rank per retained quote. A failed allocation leaves the old
files intact. Full text remains on SD. Native saves use atomic rewrites instead
of the former custom in-place append path, so quote additions can do more SD
I/O than before; no hardware latency claim is made.

## Migration, publication and recovery

Legacy crASHink versions 1–6 and 8 remain readable. On the first successful book
load/save they convert to native v7 with a `.pre-native.bak` copy of the exact
original file. Existing `.pre-v8.bak` files remain untouched. New v7 cores and
companions must be kept together when backing up crASHink; stock needs only
the core but cannot show colors or notes.

For a save, write/sync/close the new core temporary file, write/checksum/sync
its companion, and publish the companion first. Publish the core through its
existing `.bak` rotation last. A companion whose core was never published is
not applied to the old file. On failed core publication the old backup is
restored; the old hash-named companion is still valid. On a color/note-only
change the core bytes match and only the companion is atomically replaced.

Update the small companion pointer after core publication. If that pointer
write fails, the core's exact hash still locates its durable companion. The
previous normal generation is removed only after a successful pointer update;
rename transactions and ambiguous stock rewrites retain their recovery copy.
Corrupt companions block destructive writes rather than dropping annotations.

Native scoped sync IDs and tombstones survive rewrites. Replaced/deleted native
quotes produce scoped deletion entries. Existing custom sync bindings remain
readable, and preparing uploads also writes their active keys into the native
core so stock and crASHink see the same IDs.

The archived `patches-1.6.1` set is unchanged. Older custom firmware cannot read
the new v7 core: downgrade using the retained legacy backup, keeping all files
until you verify your highlights on the reader.
