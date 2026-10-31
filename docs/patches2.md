# Reorganized patch series: patches2

`patches2/` is the candidate ten-patch series. `patches/` remains the active,
unchanged series, and `.github/workflows/build.yml` still builds from `patches/`.
No build workflow was dispatched for this reorganization.

Apply every candidate patch once, in filename order, to clean upstream source.
Build after the entire series is applied. These patches share settings and reader
integration code; they are not independent feature switches. Do not apply both
folders to the same source tree.

## Structure and migration

The table lists the main feature migrations. Shared hunks were reconciled against
the final source rather than concatenating old patch files.

| Candidate patch | Original patches / extracted changes |
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

## Equivalence verification

- Original patch repository revision: `066e2075f74fc13a6b8368a052654c0678dfaa87`.
- Upstream baseline: CrossInk v1.6.1,
  `9914146eeae7b46b300f475a16c32426fc02ec1f`.
- Pinned FreeInk SDK: `699370183fa3a0e33c9cb83a36f701bbb6022095`.
- Both the 26 original patches and the 10 candidate patches apply cleanly from
  that baseline and produce Git tree
  `16d6526e9270f5223a741b1bd2d57cb1ff50661e`.
- Equality covers the entire indexed source tree, including file contents,
  additions/deletions, executable modes and submodule references. No firmware
  behavior change is intended or introduced by regrouping.
- All 26 original patch files remain byte-for-byte unchanged.

**Native result:** all 29 selected test executables passed, covering the library,
clipping storage/list/rendering/editing, cross-page adjustment, Readest sync,
chapter index, grayscale/image refresh and frontlight behavior.

Native verification was run against a separate checkout materialized from the
candidate tree, not against an already-patched working directory.

## X4 Pro firmware build

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
timing. The active GitHub workflow still uses `patches/`; it was not changed or
dispatched for this check.
