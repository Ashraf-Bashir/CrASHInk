# CrossInk v2.0.0 feature patches

Pinned upstream: `2cdd6117568c8877c3180d5fac8d5b20e55d1ac7` (`v2.0.0`).
Pinned SDK: `f7fa9b6871192ea27d8aa37a03beaa749462c3ab`.

Apply all 17 patches in filename order, then the two SDK patches inside the
SDK checkout. Never mix these with `patches-1.6.1`. The same feature/domain
boundaries are retained; see the [domain map](../docs/development/crashink-patch-domains.md).

The X4 Pro firmware build and selected native regression checks have been
validated. On-device rendering, touch, Wi-Fi, and sleep still require a device
check after installation.

## Substantive v2 adaptations

- Preserve native language-pack and plugin identities, chapter-cache sharding,
  publisher typography, text darkness, ruled lines, word/character spacing,
  SDK display capabilities, and TLS hostname validation.
- Adopt persistent source-position anchors and stock-compatible v7 clipping cores.
  Colors and notes use SHA256-checked companion metadata under the consolidated
  `/.crashink/.clippings` folder. Legacy custom v1–v6/v8 files migrate with an
  exact `.pre-native.bak`; older `.pre-v8.bak` copies remain untouched. Stock v7
  files import on book open when no local history exists, without moving the
  stock source. crASHink keeps its 65,535-quote / 60-KiB limits; stock firmware
  still applies its own 256-quote / 4-KiB guards. See
  [storage and recovery details](../docs/crashink-clipping-storage.md).
  Source anchors are captured for new selections; old passages migrate only
  after their positions are verified. Ambiguous repeated text remains on the
  fallback matcher. Edits invalidate old anchors and partial UTF-8 selections
  retain their byte boundaries.
- Highlight dragging follows word/line gaps and tolerates a missing contact frame
  for 50 ms before committing. Reader idle scans/prewarming pause during touch.
- Chapter headings and navigation labels stay bold; full/partial chapter layouts
  rebuild once under cache version 119 / `0xE8` to apply the new glyph widths.
- Highlight colors are Yellow, Green, Red, and Blue, with 25%, 50%, 75%, and
  100% black swatches. Yellow retains its exact pattern; Red uses contrasting
  text. Filters use names and Readest sync maps the four colors both ways.
  Stored IDs 0/1/2 remain Yellow/Red/Blue and Green=3. Retired Orange ID 4 loads
  as Yellow, old Orange defaults fall back to Yellow, and Readest Orange notes
  map to Yellow. One checked page snapshot is shared by Red/Blue ribbons so
  overlapping pieces cannot invert glyphs twice.
- Multiword touch selections save on release without a Lookup/Clip prompt.
  Single-word holds retain lookup; saved quote controls use non-flashing refreshes.
- Clipping rewrites use checked scratch allocation and publish the matching
  companion before the atomic core commit. Notes/colors do not add per-page SD
  scans. Native sync IDs and scoped deletions survive rewrites; custom sync
  bindings migrate into prepared native cores. The prior custom append shortcut
  is replaced by checked native rewrites, so large-book saves can do more I/O.
- Keep v2 alternating CRC/sequence progress records and add reading timestamps.
  Read old crASHink timestamp records without discarding position freshness.
- Use `reader_settings.v2.bin` for the new per-book settings schema. Old v1
  per-book settings files remain intact for rollback; per-book preferences may
  need reapplying in v2. Global preferences remain in their existing store.
- Library filtering uses read-only completion peeks and bounded batches of up
  to 16 records, checking a six-millisecond budget between records. Visible covers still load,
  while off-screen prefetch waits until filtering finishes. Background refresh
  reuses folder checkpoints and repaints progress only when the phase or percent
  changes. Saved view caches remain reusable on later opens.
- Library and EPUB generation share versioned thumbnail filenames; valid cached
  covers recover from transient paint failures without another conversion.
- The normal sleep path reaches hardware deep sleep after shutting down storage,
  display, and frontlight; a storage failure restores input instead. Regression
  tests execute the real sleep-entry function and check this sequence.
- A short Power press wakes X4 Pro even when released during initialization,
  independently of the awake short-press shortcut setting. Wake-release guards
  still prevent the same gesture from immediately putting it back to sleep.
- Add a native Plugins entry to the launcher. Seven apps can be held and dragged
  across nine slots, swapping occupied slots or using empty ones. Save the small
  arrangement file under `/.crashink/.launcher.bin` with checksum and backup
  recovery. Physical selection follows the saved visual order. The bottom
  ribbon remains unchanged; disabled plugins ask for confirmation on first use.
- Receive-updates progress uses non-flashing refreshes, including during installation.
- Retain image-only regional grayscale, the custom launcher/Library, frontlight
  borders, Workflowy, and power policy. No other board build environments are
  exposed.
- Each Workflowy quote includes its full `📖 Book — Author — Chapter` source,
  even underneath a `📘 Book — Author` grouping node.

Build/sign through **Build crASHink X4 Pro → baseline 2.0.0**. Select **1.6.1**
to rebuild the untouched archived firmware instead. Both use standard free
GitHub runner choices and retained PlatformIO/dependency/compiler caches.
