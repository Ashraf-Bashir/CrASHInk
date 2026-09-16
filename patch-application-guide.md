# CrossInk Clipping Patches — Application Guide

## Overview

Two patches addressing clipping usability in CrossInk:

1. **`0001-remove-clipping-limit.patch`** — Removes the 256 clippings per-book hard limit
2. **`0002-clickable-clippings-with-modal-dialog.patch`** — Makes clippings clickable with a Delete/Cancel modal, dismissible by tapping outside

Both patches are designed to apply cleanly on top of CrossInk release tags (RC or stable) starting from v1.5.0. They do not require any additional dependencies or configuration changes.

---

## Prerequisites

- CrossInk firmware source repository (https://github.com/uxjulia/CrossInk)
- Git
- PlatformIO with appropriate board support for your target device
- Access to clone/modify the repo

---

## Step 1: Clone or Update CrossInk

```bash
git clone https://github.com/uxjulia/CrossInk.git
cd CrossInk
```

Or if you already have a clone:

```bash
cd CrossInk
git pull origin main
```

---

## Step 2: Check Out a Release Tag

Apply patches on top of any stable or RC release. Examples:

```bash
# Stable release (recommended)
git checkout v1.5.0

# Or an RC version
git checkout v1.5.1-rc-4
```

Verify you're on the right tag:

```bash
git describe --tags --always
```

---

## Step 3: Apply the Patches

Apply both patches in order:

```bash
git apply 0001-remove-clipping-limit.patch
git apply 0002-clickable-clippings-with-modal-dialog.patch
```

Or apply them as a series in a single command:

```bash
git apply 0001-remove-clipping-limit.patch 0002-clickable-clippings-with-modal-dialog.patch
```

**Verify the patches applied cleanly:**

```bash
git status
```

You should see modified files with no merge conflicts:

```
Modified:   src/ClippingStore.h
Modified:   src/activities/reader/EpubReaderClippingListActivity.cpp
```

---

## Step 4: Build the Firmware

Build for your target device. PlatformIO environment names:

- `default` — Xteink X3/X4 (ESP32-C3)
- `sticky` — reTerminal Sticky (ESP32-S3)
- `x4-pro` — Xteink X4 Pro (ESP32-S3)
- `simulator` — Native simulator for testing on your computer

Example build commands:

```bash
# For Xteink X3/X4
pio run -e default

# For Sticky
pio run -e sticky

# For X4 Pro
pio run -e x4-pro

# For simulator testing
pio run -e simulator
```

Build output (firmware binary):

```
.pio/build/<environment>/firmware.bin
```

---

## Step 5: Rename Firmware Binary

Rename the firmware to follow the `crASHink` naming convention:

```bash
# Example for X3/X4 (default environment)
cp .pio/build/default/firmware.bin crASHink-1.5.0-patched.bin

# For all targets
cp .pio/build/default/firmware.bin crASHink-1.5.0-x3x4.bin
cp .pio/build/sticky/firmware.bin crASHink-1.5.0-sticky.bin
cp .pio/build/x4-pro/firmware.bin crASHink-1.5.0-x4pro.bin
```

---

## Step 6: Verify Patches (Optional)

Test on real hardware or simulator:

```bash
# Flash to device using Xteink tools or your preferred method
# Then test in the reader:

# 1. Open a book with many clippings (or create new ones)
# 2. Open the Clippings menu
# 3. Single tap a clipping in the list
#    → Should show a modal with "Delete" and "Cancel" buttons
# 4. Tap "Cancel" or outside the modal
#    → Modal should close, clipping remains
# 5. Single tap another clipping and tap "Delete"
#    → Clipping should be deleted from the list
# 6. Try creating >256 clippings in a single book (if possible)
#    → Should not hit a limit error
```

**On simulator:**

```bash
pio run -e simulator
# The simulator will start in your terminal with a native window
```

---

## Patch Details

### Patch 1: Remove Clipping Limit

**File**: `src/ClippingStore.h`  
**Change**: Line 13  
**Before**: `CLIPPING_MAX_PER_BOOK = 256`  
**After**: `CLIPPING_MAX_PER_BOOK = 65535`

**Rationale**:
- The clipping count is stored as `uint16_t`, so the hard limit is now the maximum value of that type (65535).
- Existing books with ≤256 clippings are unaffected.
- New books can now accumulate more clippings without hitting an artificial ceiling.
- File format versioning (version 4) already supports this; no migration needed.

---

### Patch 2: Clickable Clippings with Modal

**File**: `src/activities/reader/EpubReaderClippingListActivity.cpp`  
**Changes**: `loop()` function (lines ~350–362)

**Before**:
- Single tap on clipping in list → open detail view
- Single tap on clipping in detail view → jump back to clipping location in book
- Delete action only accessible via long-press (hold Confirm button)

**After**:
- Single tap on clipping in list → show Delete/Cancel modal
- Single tap on clipping in detail view → show Delete/Cancel modal
- Tapping outside the modal (on the scrim/background) dismisses it
- Delete/Cancel actions now discoverable and user-friendly

**Implementation**:
- Reuses the existing `FileBrowserActionActivity` modal pattern for consistency
- Captures clipping metadata before launching the modal to handle async result correctly
- No changes to clipping storage or data structure
- Modal UI matches the existing CrossInk design system

---

## Troubleshooting

### Patches Don't Apply

If `git apply` fails with "patch does not apply", ensure:

1. You're in the correct repository directory
2. You've checked out the right release tag (v1.5.0+)
3. The source files haven't been significantly modified from the base release
4. Run `git status` to see if there are uncommitted changes blocking the patch

**If the upstream release changed**, you can manually apply the changes using the line numbers and logic described in "Patch Details" above.

### Build Fails After Patching

1. Ensure all C++ syntax is correct (the patches preserve existing code style)
2. Run a clean build: `pio run -e <target> --target clean && pio run -e <target>`
3. Check for typos in `src/activities/reader/EpubReaderClippingListActivity.cpp` around the loop() function

### Behavior Issues After Flashing

- **Clippings still hit the 256 limit**: Verify patch 1 applied correctly. Run `grep CLIPPING_MAX_PER_BOOK src/ClippingStore.h` and check it shows `65535`.
- **Single tap doesn't show modal**: Verify patch 2 applied correctly. The `showClippingActionMenu(false);` calls should be present in the Confirm button release handler.
- **Existing clippings list broken**: Clear the clipping cache on the device (delete `.crosspoint/clippings/` directory on the SD card) and restart.

---

## Distributing Patched Builds

If building for other users:

1. Build for all three hardware targets (default, sticky, x4-pro)
2. Rename each binary according to the device:
   - `crASHink-<version>-x3x4.bin` for Xteink X3/X4
   - `crASHink-<version>-sticky.bin` for Sticky
   - `crASHink-<version>-x4pro.bin` for X4 Pro
3. Document in release notes:
   - Which CrossInk base version the patches were applied to
   - List of changes (remove limit, add clickable clippings)
   - Link to this guide for flashing instructions

---

## Credits

Patches authored by Ash (eng.bashir@gmail.com) with Claude assistance.  
Built on CrossInk firmware by uxjulia and the CrossPoint Reader community.

---

## License

These patches maintain the same license as CrossInk (check LICENSE in the repo).
