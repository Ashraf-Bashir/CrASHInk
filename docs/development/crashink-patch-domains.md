# crASHink patch domains and versioned baselines

`patches-1.6.1/` is the preserved v1.6.1 feature stack. Its existing patch
contents remain frozen. `patches-2.0.0/` is the completed port with the same
domain names and order, targeting the exact v2.0.0 stable baseline. Both versions
are independently selectable in the build workflow.

Each row below is one domain, not one individual change. Shared settings,
translations and ActivityManager files may occur in several patches: each
patch owns only the integration for its domain. Later patches may extend
earlier domains; application order is significant. Tests belong beside the
feature in that feature's patch. Compatibility fixes are folded into these
patches, never an additional migration-fixes or miscellaneous patch.

| Patch filename (same in each versioned directory) | Domain / ownership |
| --- | --- |
| `0000-settings-and-branding.patch` | Settings registration, branding and X4 Pro target configuration |
| `0001-sd-storage-and-migration.patch` | Hidden SD paths and non-destructive migration |
| `0002-status-bar.patch` | Clock, Wi-Fi, reader and app status bars |
| `0003-buttons-and-gestures.patch` | Buttons, touch and gesture routing |
| `0004-fonts-and-typography.patch` | Fonts and text metrics |
| `0005-clippings.patch` | Clipping storage, ordering, editing, colors and highlight rendering |
| `0006-readest.patch` | Readest authentication, position and clipping synchronization |
| `0007-chapter-index.patch` | Chapter navigation and index |
| `0008-book-images.patch` | Book image decoding, contrast and refresh regions |
| `0009-frontlight-and-borders.patch` | Frontlight gestures and independently sized leakage borders |
| `0010-library-and-home.patch` | Library catalog, covers, launcher and shared app navigation |
| `0011-power-and-sleep.patch` | Idle deadlines, sleep images and power management |
| `0012-sd-firmware-updates.patch` | SD firmware discovery and installation |
| `0013-workflowy.patch` | Workflowy tasks, calendar, quote sharing and offline outbox |
| `0014-background-work.patch` | Background job scheduling, cancellation and UI handoff |
| `0015-epub-styling.patch` | EPUB publisher styling and layout rules |
| `0016-wifi-firmware-updater.patch` | Authenticated Wi-Fi firmware receiving and signature checks |

SDK patches are kept under the matching version's `sdk/` directory. Their
ownership is grayscale display support and authenticated HTTPS respectively.
They are pinned to that upstream release's SDK gitlink, not another version's
SDK. The old and new firmware share the signing identity, but not patch files
or automatically selected upstream revisions.

## Port review rules

- Preserve new upstream behavior unless it conflicts with an explicitly chosen
  crASHink behavior. Record substantive adaptations with the new baseline.
- Check overlapping diffs in order; applying a later patch must not restore an
  obsolete implementation from v1.6.1.
- Keep existing save/cache migrations non-destructive. Rebuilding v1.6.1 must
  remain possible independently of the v2.0.0 adaptation.
- Only advertise a version as buildable after its complete stack and SDK
  patches apply and the X4 Pro firmware compiles.

## Building the preserved firmware

In GitHub Actions, run **Build crASHink X4 Pro** with **baseline 1.6.1**.
The workflow fetches the pinned upstream commit and checks the SDK commit,
applies only `patches-1.6.1/` and its `sdk/` patches, then builds and signs the
firmware as before. It never follows the newest tag automatically.

`patchsets.json` is the source of truth for revisions and readiness. The
resolver verifies `SHA256SUMS` before applying the archived stack, preventing
accidental changes to the preserved version. The 2.0.0 option is the default
after completing the port, selected native tests, and X4 Pro compilation.
Select 1.6.1 explicitly for the archived build. Future work must not update the
archived stack as a side effect of adapting a newer version.
