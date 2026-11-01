# Reorganized patch series: patches2

`patches2/` is the active ten-patch series used by `.github/workflows/build.yml`.
`patches/` retains the original 26 patches as an unchanged reference.
The pipeline switch does not dispatch a build automatically.

Apply every patch in `patches2/` once, in filename order, to clean upstream source.
Build after the entire series is applied. These patches share settings and reader
integration code; they are not independent feature switches. Do not apply both
folders to the same source tree.

## Structure and migration

The table lists the main feature migrations. Shared hunks were reconciled against
the final source rather than concatenating old patch files.

| Active patch | Original patches / extracted changes |
| --- | --- |
| `0000-settings-page.patch` | Shared settings navigation and CrASHink tab from `0000-status-icon-layout.patch`, `0005-readest-clippings-sync.patch`, and `0012-negative-spacing-and-margins.patch`. Also preserves the existing logo and boot build-version support from old 0000. |
| `0001-status-icons.patch` | Icon layout from `0000-status-icon-layout.patch`; `0003-frontlight-status-indicator.patch`; `0004-touchscreen-status-indicator.patch`; `0011-highlighter-status-icon.patch`; Readest icon drawing from `0005-readest-clippings-sync.patch`. |
| `0002-clippings.patch` | `0001-clipping-limit-64k.patch`; `0002-highlight-editor.patch`; `0006-long-clippings.patch`; `0007-instant-highlight.patch`; `0008-skip-my-clippings-export.patch`; `0015-clipping-list-pagination.patch`; `0017-collapse-highlights-by-chapter.patch`; `0020-on-page-highlight-editing.patch`; `0021-exact-highlight-render-boundaries.patch`; `0023-cross-page-highlight-adjustment.patch`. Shared clipping matching code from old 0005 is included here. |
| `0003-readest.patch` | `0005-readest-clippings-sync.patch`; `0016-readest-optimized-position-sync.patch`. Shared settings/icon/clipping infrastructure is supplied by the preceding patches. |
| `0004-chapter-index.patch` | `0018-collapse-nested-chapter-index.patch`. |
| `0005-buttons-and-gestures.patch` | `0009-side-buttons-double-press.patch`; `0019-swipe-only-page-turns.patch`. |
| `0006-fonts-and-typography.patch` | `0010-text-weight-setting.patch`; `0012-negative-spacing-and-margins.patch`. |
| `0007-images.patch` | `0013-invert-images.patch`; `0014-skip-cleanup-small-images.patch`; `0024-isolate-image-refresh-regions.patch`. Also reconciles shared settings masks and serialization after typography is present. |
| `0008-frontlight.patch` | `0022-preserve-frontlight-on-firmware-restart.patch`. Its existing mixed release-note text is preserved byte-for-byte. |
| `0009-library.patch` | `0025-cached-library-cover-grid.patch`, including shared Home cover sizing, asynchronous covers, paced filters/change detection, index caching, and worker lifecycle hooks. |

Feature-specific preferences, translations and tests follow their feature. The
shared settings-page patch supplies navigation and layout. Later patches extend
common files where their feature needs integration; this is intentional and
requires the documented order.

## Initial consolidation equivalence verification

- Original patch repository revision: `066e2075f74fc13a6b8368a052654c0678dfaa87`.
- Upstream baseline: CrossInk v1.6.1,
  `9914146eeae7b46b300f475a16c32426fc02ec1f`.
- Pinned FreeInk SDK: `699370183fa3a0e33c9cb83a36f701bbb6022095`.
- At consolidation commit `a2b02aee`, the 26 original patches and 10 candidate patches applied cleanly from
  that baseline and produce Git tree
  `16d6526e9270f5223a741b1bd2d57cb1ff50661e`.
- Equality covers the entire indexed source tree, including file contents,
  additions/deletions, executable modes and submodule references. No firmware
  behavior change was introduced by regrouping. Later library enhancements are
  maintained only in active patch `0009-library.patch`; the archived folder is frozen.
- All 26 original patch files remain byte-for-byte unchanged.

**Native result:** all 29 selected test executables passed, covering the library,
clipping storage/list/rendering/editing, cross-page adjustment, Readest sync,
chapter index, grayscale/image refresh and frontlight behavior.

Native verification was run against a separate checkout materialized from the
candidate tree, not against an already-patched working directory.

## Initial X4 Pro firmware build

A full `pio run -e x4-pro` from the candidate source succeeded on 2026-10-31.
Compilation, linking, image generation, touch-gate audit and OTA partition-size
checks passed. No source fixes were needed.

- PlatformIO Core: 6.2.0; Espressif platform: 55.3.37.
- Target: ESP32-S3 / X4 Pro, with the pinned SDK above.
- Clean candidate build duration: 217.42 seconds (local verification, not a
  GitHub Actions timing measurement).
- Static RAM: 104,660 / 327,680 bytes (31.9%).
- Program flash: 5,763,209 / 6,553,600 bytes (87.9%).
- Generated firmware image: 5,763,712 bytes.
- OTA partition headroom: 789,888 bytes.

This verifies firmware compilation, not on-device behavior or library-opening
timing. The firmware check was completed before switching the workflow to
`patches2/`. No GitHub workflow was dispatched for that check or the pipeline switch.

## Current library behavior

The active library patch keeps a 3 × 3 grid with centered, tightly spaced columns.
Cover mode puts Sort by and Reverse order in the ⋮ menu and combines book count
and page into one compact footer. Status changes do not resize the cover grid.

Completed view membership/counts persist in disposable SD snapshots. Interrupted
filters save their processed position on exit and resume rather than rereading
books already checked. Completion changes, file changes and index refreshes
invalidate the relevant cached result; ordinary reading-time/page-count stats
saves do not. A fresh view may need one filter pass before its full cache exists.

Cover pixels reuse Home's optional PSRAM cache. Visible cover results repaint as
a batch; background preloading does not repaint the page. Missing covers show a
book outline and title. **⋮ → Refresh covers on this page** retries the visible
nine covers, removing their thumbnail/source-image/negative caches without
rebuilding the book index or clearing saved view counts.

Latest verification: 133 native tests passed, including zero book-record reads on
warm filter restore, interrupted-filter resume, cache integrity/invalidation,
cover batching, worker lifecycle, swipe cancellation and stats persistence.
The updated X4 Pro firmware compiled, linked and passed the OTA size check.

Marking a book finished edits current view membership directly, rebasing retained
history rows if completion removes a recent entry. It does not restart filtering
or reset the current cover page. Deleted indexed files rebuild the index before
returning; missing book paths are rejected before entering a reader. Swipe
cancellation stays latched until a fresh touch, so a later release cannot open a
cover on the destination page. Physical page buttons page the cover grid.

Home cover mode keeps the newest book featured and swipes the six lower covers
through a persistent SD opening order, with no fixed history-book count. The old
18-entry recent metadata list remains a bounded compatibility cache for existing
screens; it no longer limits Home cover browsing. Existing entries seed the disk
ordering atomically. Books already evicted by older firmware cannot be recovered.

Only paths and opening order are persisted in the new history files, never book
contents or duplicate covers. Opening order uses a monotonic counter and therefore
works without a correct device clock. Home reads one page directly instead of
loading/scanning the complete history. It reuses Library's thumbnail path/header
validation, optional cover-pixel cache, and the same disk-only cover worker.
Cached cover assets are reused at matching dimensions; missing sizes are generated
in the background, visible slots first, then later history pages while idle.
Swipes stay active during conversion. The worker is joined before reader/child
navigation, frontlight overlays or sleep; partially written thumbnails are never
painted. Rename and removal update the disk ordering as well as legacy recents.

Verification includes 100-book disk history and Home paging with an empty legacy
metadata cache, bounded reads per page, duplicate-free promotion, rename/removal,
write/rename failure recovery, and atomic migration retry. The complete ten-patch
series applies cleanly to the pinned upstream source.

## Direct firmware downloads

The build workflow uses `actions/upload-artifact@v7` with `archive: false` and a
single `.bin` file. New runs provide the versioned firmware file directly in
Artifacts, without a ZIP wrapper. Existing older run artifacts are unchanged.
See [GitHub's direct-artifact download announcement](https://github.blog/changelog/2026-02-26-github-actions-now-supports-uploading-and-downloading-non-zipped-artifacts/).
