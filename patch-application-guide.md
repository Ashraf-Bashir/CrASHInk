# crASHink patch application guide

The X4 Pro build uses one patch directory. Apply the entire numbered series to a clean CrossInk checkout in filename order, then apply the SDK series to its FreeInk SDK submodule. The build workflow selects a stable CrossInk tag. The consolidated series was verified against CrossInk v1.6.1.

```sh
# Run from the crASHink checkout.
patch_dir="$PWD/patches"
git clone --branch v1.6.1 --depth 1 https://github.com/uxjulia/CrossInk.git source
git -C source submodule update --init --recursive
for patch in "$patch_dir"/*.patch; do
  git -C source apply "$patch" || exit
done
for patch in "$patch_dir"/sdk/*.patch; do
  git -C source/freeink-sdk apply "$patch" || exit
done
cd source
pio run -e x4-pro
```

Make changes in the owning patch. Shared source files can appear in several patches, so recheck later patches after editing an earlier one.

| Patch | Owns |
|---|---|
| `0000-settings-and-branding.patch` | Settings shell and crASHink branding |
| `0001-sd-storage-and-migration.patch` | Hidden SD paths and migration |
| `0002-status-bar.patch` | Status icons and clock placement |
| `0003-buttons-and-gestures.patch` | Shared buttons and gesture routing |
| `0004-fonts-and-typography.patch` | Font selection and text rendering |
| `0005-clippings.patch` | Clipping storage, highlight rendering, editing, and color |
| `0006-readest.patch` | Readest sync and destination preview |
| `0007-chapter-index.patch` | Collapsible EPUB chapter index |
| `0008-book-images.patch` | EPUB image contrast, grayscale, and refresh |
| `0009-frontlight-and-borders.patch` | Frontlight and light-leak borders |
| `0010-library-and-home.patch` | Library, home navigation, covers, and book actions |
| `0011-power-and-sleep.patch` | Power management, auto-sleep, and sleep images |
| `0012-sd-firmware-updates.patch` | Firmware picker and SD installs |
| `0013-workflowy.patch` | Workflowy export and integration |

The SDK series owns the display driver changes for grayscale and the SecureNet HTTPS hostname handling.
