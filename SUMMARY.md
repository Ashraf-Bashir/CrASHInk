# CrossInk Clipping Patches — Complete Package

## Contents

This package contains two git patches and comprehensive documentation for extending CrossInk firmware with clipping improvements.

### Patch Files

1. **`0001-remove-clipping-limit.patch`** (31 lines)
   - Removes the hard 256 clippings per-book limit
   - Increases `CLIPPING_MAX_PER_BOOK` from 256 to 65535
   - Single-line change to `src/ClippingStore.h`
   - File format backward compatible (uses existing uint16_t storage)

2. **`0002-clickable-clippings-with-modal-dialog.patch`** (67 lines)
   - Makes clippings clickable with a Delete/Cancel modal
   - Changes single-tap behavior from "open detail view" to "show action modal"
   - Modal dismissible by tapping outside (the scrim/background)
   - Reuses existing `FileBrowserActionActivity` modal pattern for consistency
   - Modifies `src/activities/reader/EpubReaderClippingListActivity.cpp`

3. **`0006-readest-clippings-sync.patch`**
   - Two-way sync with [Readest](https://readest.com) (X4 Pro)
   - Settings → System → Readest Sync: enter Readest email + password once; they are saved (password
     obfuscated with the device MAC). Also "Reset Book Matches" and "Disconnect"
   - Reader menu → **Sync Readest** does everything in one pass:
     - finds the book in your Readest library by title similarity; a confident match (same file, or a
       word-for-word title clearly ahead of the rest) is used directly, otherwise you pick from the top 3.
       The choice is remembered per book
     - pulls Readest highlights the device doesn't have as clippings
     - pushes device clippings Readest doesn't have as **grey** highlights (KOReader xpointers)
     - syncs reading position, furthest wins (jumps forward, or sends the device position to Readest)
   - Success screens close by themselves after ~2 s; errors stay until dismissed
   - Runs after a clean network reboot like KOReader sync, pushes in batches of 10, and parses Readest
     responses through JSON filters to keep heap use low

### Documentation

1. **`patch-application-guide.md`** (260 lines)
   - Complete step-by-step guide for applying patches
   - Prerequisites and setup instructions
   - Build commands for all three device targets (X3/X4, Sticky, X4 Pro)
   - Binary naming convention: `crASHink-<version>-<target>.bin`
   - Verification steps on hardware and simulator
   - Troubleshooting section

2. **`github-setup.md`** (198 lines)
   - How to create a GitHub repository for crASHink patched builds
   - Recommended folder structure and files
   - README and CHANGELOG templates
   - Steps to build and distribute pre-compiled binaries
   - Instructions for creating GitHub releases

3. **`SUMMARY.md`** (this file)
   - Quick reference of what's included and how to use it

---

## Quick Start

### For Individual Users (Apply Patches Yourself)

1. Clone CrossInk: `git clone https://github.com/uxjulia/CrossInk.git && cd CrossInk`
2. Check out a release: `git checkout v1.5.0`
3. Apply patches: `git apply 0001-remove-clipping-limit.patch 0002-clickable-clippings-with-modal-dialog.patch`
4. Build: `pio run -e default` (or `sticky`, `x4-pro`)
5. Find binary: `.pio/build/default/firmware.bin`

See **`patch-application-guide.md`** for detailed instructions.

### For Maintainers (Create a GitHub Repository)

1. Create a new GitHub repo called `crASHink`
2. Copy patches to `patches/` folder
3. Add `README.md`, `CHANGELOG.md`, `.gitignore` (templates in `github-setup.md`)
4. Build for all targets and upload binaries to GitHub Releases
5. Users can download pre-built binaries or apply patches themselves

See **`github-setup.md`** for detailed instructions.

---

## Patch Details

### Patch 1: Remove Clipping Limit

**What it does:**
- Allows unlimited clippings per book (previously capped at 256)
- The new limit is 65535 (maximum value of `uint16_t`)

**Why it works:**
- CrossInk stores clipping counts as `uint16_t`, so the new limit is still well-defined
- Existing books with ≤256 clippings are unaffected
- No migration or file format changes needed

**File changed:**
- `src/ClippingStore.h` line 13

---

### Patch 2: Clickable Clippings with Modal Dialog

**What it does:**
- Single tap on a clipping in list view → shows Delete/Cancel modal
- Single tap on a clipping in detail view → shows Delete/Cancel modal
- Tap outside the modal → dismisses it without action
- Modal UI follows existing CrossInk design patterns

**Why it works:**
- Improves discoverability (delete action was previously only via long-press)
- Uses the existing `FileBrowserActionActivity` modal infrastructure
- No changes to data storage or file formats
- Works on all device types (button and touch inputs)

**File changed:**
- `src/activities/reader/EpubReaderClippingListActivity.cpp` (loop() function)

---

## Device Support

Patches are tested and apply cleanly to CrossInk releases v1.5.0 and later.

### Build Targets

- `default` → Xteink X3/X4 (ESP32-C3) → `crASHink-<version>-x3x4.bin`
- `sticky` → reTerminal Sticky (ESP32-S3) → `crASHink-<version>-sticky.bin`
- `x4-pro` → Xteink X4 Pro (ESP32-S3) → `crASHink-<version>-x4pro.bin`
- `simulator` → Native computer testing → `.pio/build/simulator/firmware.bin`

---

## Testing Checklist

After building and flashing:

- [ ] Open a book with existing clippings
- [ ] Open the Clippings menu
- [ ] Single tap a clipping → modal appears with "Delete" and "Cancel"
- [ ] Tap "Cancel" → modal closes, clipping remains
- [ ] Single tap another clipping and tap "Delete" → clipping is deleted
- [ ] Try tapping outside the modal → modal closes without action
- [ ] Create many clippings (>256) → no "limit reached" error
- [ ] Verify highlights still display correctly
- [ ] Verify long-press still works as a fallback

---

## Troubleshooting

**Patches don't apply?**
- Ensure you're on the correct release tag (v1.5.0+)
- Check that you haven't modified the source files

**Build fails?**
- Clean build: `pio run -e <target> --target clean && pio run -e <target>`
- Check for typos in the modified C++ files

**Firmware doesn't boot after flashing?**
- Erase device flash completely before re-flashing
- Double-check you're flashing the correct binary for your device
- Try the original (unpatched) v1.5.0 to rule out hardware issues

**Clippings still hit the 256 limit?**
- Verify Patch 1 applied: `grep CLIPPING_MAX_PER_BOOK src/ClippingStore.h`
- Should show `65535`, not `256`

**Single tap doesn't show modal?**
- Verify Patch 2 applied: look for `showClippingActionMenu(false);` calls
- Try clearing cached clippings and re-testing

---

## Credits

- **Patches by**: Ash (eng.bashir@gmail.com)
- **Built on**: [CrossInk](https://github.com/uxjulia/CrossInk) firmware by uxjulia
- **CrossInk fork of**: [CrossPoint Reader](https://github.com/crosspoint-reader/crosspoint-reader)

---

## License

These patches maintain the same license as CrossInk. See the original CrossInk repository for license details.

---

## Next Steps

1. **Apply patches** following `patch-application-guide.md`
2. **Build firmware** for your device(s)
3. **Flash** to your e-reader using Xteink tools or your preferred method
4. **Test** using the checklist above
5. **(Optional) Share** by creating a GitHub repository following `github-setup.md`

---

**Last Updated**: 2026-09-16  
**Patches for**: CrossInk v1.5.0+  
**Package Version**: 1.0
