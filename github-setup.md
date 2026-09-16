# GitHub Setup for crASHink Patched Builds

## Recommended Repository Structure

To maintain and distribute your patched CrossInk builds:

```
your-github-account/crASHink
├── README.md                              # Overview and quick start
├── patches/
│   ├── 0001-remove-clipping-limit.patch
│   ├── 0002-clickable-clippings-with-modal-dialog.patch
│   └── patch-application-guide.md
├── builds/                                # Pre-built binaries (optional)
│   └── v1.5.0-patched/
│       ├── crASHink-1.5.0-x3x4.bin
│       ├── crASHink-1.5.0-sticky.bin
│       └── crASHink-1.5.0-x4pro.bin
├── .gitignore                             # Exclude build artifacts
└── CHANGELOG.md                           # Track patch releases

```

## Initial Setup Steps

### 1. Create New Repository on GitHub

```bash
# Create a new empty repo named "crASHink" on github.com

# Clone it locally
git clone https://github.com/your-username/crASHink.git
cd crASHink
```

### 2. Add Patches and Documentation

```bash
mkdir -p patches builds

# Copy patch files
cp /path/to/0001-remove-clipping-limit.patch patches/
cp /path/to/0002-clickable-clippings-with-modal-dialog.patch patches/
cp /path/to/patch-application-guide.md patches/
```

### 3. Create README

```bash
cat > README.md << 'EOF'
# crASHink — Patched CrossInk Firmware

Custom builds of [CrossInk](https://github.com/uxjulia/CrossInk) e-reader firmware for Xteink X3/X4 and other ESP32 devices.

## Changes in Patched Builds

1. **Unlimited Clippings**: Removes the 256 clippings per-book limit
2. **Clickable Clippings**: Single tap shows Delete/Cancel modal; tap outside to dismiss

## Quick Start

See [patches/patch-application-guide.md](patches/patch-application-guide.md) for:
- Prerequisites
- How to apply patches to a fresh CrossInk checkout
- Building for your device (X3/X4, Sticky, X4 Pro)
- Troubleshooting

Pre-built binaries available in the [Releases](../../releases) section.

## Releases

- **v1.5.0-patched**: Based on CrossInk v1.5.0
  - `crASHink-1.5.0-x3x4.bin` — Xteink X3/X4
  - `crASHink-1.5.0-sticky.bin` — reTerminal Sticky
  - `crASHink-1.5.0-x4pro.bin` — Xteink X4 Pro

## Credits

Patches by Ash (eng.bashir@gmail.com).  
Built on [CrossInk](https://github.com/uxjulia/CrossInk) firmware.

## License

Same as CrossInk (see original repo).
EOF
```

### 4. Create .gitignore

```bash
cat > .gitignore << 'EOF'
# Build artifacts (binaries are tracked; intermediate objects are not)
*.o
*.a
*.so
.pio/
platformio.local.ini
compile_commands.json

# Temporary files
*.tmp
*.bak
*.swp
*~

# macOS
.DS_Store

# VS Code
.vscode/
EOF
```

### 5. Create CHANGELOG

```bash
cat > CHANGELOG.md << 'EOF'
# Changelog

## [1.5.0-patched] — 2026-09-16

### Added
- Unlimited clippings per book (increased from 256 limit)
- Single-tap action modal for clippings (Delete/Cancel)
- Modal dismissible by tapping outside

### Changed
- Clipping interaction now single-tap instead of long-press only

### Base
- Built on CrossInk v1.5.0 (stable)

### Devices
- Xteink X3/X4 (`crASHink-1.5.0-x3x4.bin`)
- reTerminal Sticky (`crASHink-1.5.0-sticky.bin`)
- Xteink X4 Pro (`crASHink-1.5.0-x4pro.bin`)
EOF
```

### 6. Commit and Push

```bash
git add README.md .gitignore CHANGELOG.md patches/
git commit -m "Initial commit: CrossInk patches for unlimited clippings and clickable interaction"
git push -u origin main
```

## Building and Releasing Pre-built Binaries

After creating the repository structure:

### 1. Build All Targets

```bash
# Clone CrossInk, check out v1.5.0, apply patches, build
git clone https://github.com/uxjulia/CrossInk.git crossink-build
cd crossink-build
git checkout v1.5.0
git apply ../crASHink/patches/0001-remove-clipping-limit.patch
git apply ../crASHink/patches/0002-clickable-clippings-with-modal-dialog.patch

# Build for each target
pio run -e default
pio run -e sticky
pio run -e x4-pro

# Copy binaries
mkdir -p ../crASHink/builds/v1.5.0-patched
cp .pio/build/default/firmware.bin ../crASHink/builds/v1.5.0-patched/crASHink-1.5.0-x3x4.bin
cp .pio/build/sticky/firmware.bin ../crASHink/builds/v1.5.0-patched/crASHink-1.5.0-sticky.bin
cp .pio/build/x4-pro/firmware.bin ../crASHink/builds/v1.5.0-patched/crASHink-1.5.0-x4pro.bin
```

### 2. Create a GitHub Release

```bash
# Using gh CLI (install from https://cli.github.com)
cd ../crASHink
gh release create v1.5.0-patched \
  --title "CrossInk v1.5.0 with Clipping Patches" \
  --notes "Removes 256-clipping limit and adds single-tap delete modal" \
  builds/v1.5.0-patched/crASHink-1.5.0-x3x4.bin \
  builds/v1.5.0-patched/crASHink-1.5.0-sticky.bin \
  builds/v1.5.0-patched/crASHink-1.5.0-x4pro.bin
```

Or upload binaries manually via GitHub's web UI.

## Future Patch Versions

When applying patches to a new CrossInk release (e.g., v1.5.1):

1. Create new patch branches if needed
2. Update CHANGELOG.md with new release section
3. Build new binaries and create new GitHub release
4. Link to patch-application-guide.md for manual builds

This keeps your repository as the authoritative source for your customizations while maintaining clean upstream relationships with CrossInk.
