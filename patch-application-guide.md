# crASHink patch application guide

crASHink builds upstream CrossInk with the numbered patches in `patches/`.
Keep feature changes in those patch files; the upstream firmware source in
this repository is a reference snapshot.

## Apply the complete series

The GitHub Actions build finds the newest CrossInk release tag, clones it,
initializes its submodules, and applies every patch in filename order before
building the X4 Pro firmware. The current series was checked against
CrossInk `v1.6.1`.

For a local build, clone upstream into a separate working directory and use
its selected release tag:

```sh
git clone --branch v1.6.1 --depth 1 https://github.com/uxjulia/CrossInk.git source
git -C source submodule update --init --recursive
```

From `source`, apply every patch from your crASHink checkout in ascending
filename order, then run `pio run -e x4-pro`. Apply the complete numbered
series: individual patches may use helpers supplied by later patches.
Do not apply them over the reference source snapshot in this repository.

## Patch 0002: highlight editor

`patches/0002-highlight-editor.patch` replaces the previous
`0002-clipping-tap-to-delete.patch`. It retains highlight tap detection and
opens an editor instead of the delete-only popup.

- **Extend start / Trim start:** add or remove one word at the beginning.
- **Trim end / Extend end:** remove or add one word at the end.
- The preview updates immediately; swipe up/down to page through long text.
- **Save** applies the draft atomically; **Cancel** or Back discards it.
- **Delete** requires a second confirming tap and removes the highlight.

Start and end follow reading order, including RTL text. Extensions can cross
rendered pages and paragraph boundaries within the current chapter. They stop
at chapter boundaries; table-column selections can be trimmed but cannot be
extended into another cell. Trimming keeps at least one word; use Delete to
remove the whole highlight. Edits stay within the existing clipping size limit.
If the original text cannot be matched exactly, editing is refused rather than
changing a fuzzy match. Delete remains available.

Saved edits invalidate the old layout ordinals so the reader highlights the
new text. Readest detects the changed clipping text on the next sync. Historical
entries in `My Clippings.txt` are not rewritten.

## Verification

The patch includes `test/highlight_word_edit`, covering the actual EPUB text
resolver and word adjustment with punctuation, Unicode, Arabic, inline tags,
paragraph boundaries, soft hyphens, long adjacent words, chapter edges, and
size/last-word limits. It is registered in the native CMake/CTest suite.

On the X4 Pro, tap a highlight, try each edge control, inspect the preview,
and Save. Reopen the book to check persistence. Repeat with Cancel and check
that the original survives. Confirm Delete separately. Test a highlight that
crosses a page and one near a chapter boundary. A failed SD-card write should
keep the original clipping and leave the editor open. No cache reset is needed.
Do not delete `/.crosspoint/clippings/`: it contains saved highlights.
