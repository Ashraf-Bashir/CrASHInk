# CrossInk v2.0.0 feature patches

Pinned upstream: `2cdd6117568c8877c3180d5fac8d5b20e55d1ac7` (`v2.0.0`).
Pinned SDK: `f7fa9b6871192ea27d8aa37a03beaa749462c3ab`.

Apply all 17 patches in filename order, then the two SDK patches inside the
SDK checkout. Never mix these with `patches-1.6.1`. The same feature/domain
boundaries are retained; see the [domain map](../docs/development/crashink-patch-domains.md).

The X4 Pro firmware build and 61 selected native regression checks have been
validated. On-device rendering, touch, Wi-Fi, and sleep still require a device
check after installation.

## Substantive v2 adaptations

- Preserve native language-pack and plugin identities, chapter-cache sharding,
  publisher typography, text darkness, ruled lines, word/character spacing,
  SDK display capabilities, and TLS hostname validation.
- Adopt persistent source-position anchors and upstream-style checked clipping
  indexes while retaining custom colors, notes, and large quote support. The
  custom v8 format reads v1–v6 files and keeps a `.pre-v8.bak` copy on the first
  successful upgrade. Restoring that copy is a manual step when downgrading.
  Source anchors are captured for new selections; old passages migrate only
  after their positions are verified. Ambiguous repeated text remains on the
  fallback matcher. Edits invalidate old anchors and partial UTF-8 selections
  retain their byte boundaries.
- Clipping rewrites use checked scratch allocation and atomic publication.
  Mutations mark the native clipping-sync queue. Native sync identities remain
  in a separate sidecar; stock v2 clipping files are not imported. This keeps
  colors/notes compatible without confusing the two on-disk formats.
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
- Add a native Plugins entry to the launcher. Seven apps can be held and dragged
  across nine slots, swapping occupied slots or using empty ones. Save the small
  arrangement file under `/.crashink/.launcher.bin` with checksum and backup
  recovery. Physical selection follows the saved visual order. The bottom
  ribbon remains unchanged; disabled plugins ask for confirmation on first use.
- Retain image-only regional grayscale, the custom launcher/Library, frontlight
  borders, Workflowy, and power policy. No other board build environments are
  exposed.
- Each Workflowy quote includes its full `📖 Book — Author — Chapter` source,
  even underneath a `📘 Book — Author` grouping node.

Build/sign through **Build crASHink X4 Pro → baseline 2.0.0**. Select **1.6.1**
to rebuild the untouched archived firmware instead. Both use standard free
GitHub runner choices and retained PlatformIO/dependency/compiler caches.
