# CrossInk v2.0.0 feature patches

Pinned upstream: `2cdd6117568c8877c3180d5fac8d5b20e55d1ac7` (`v2.0.0`).
Pinned SDK: `f7fa9b6871192ea27d8aa37a03beaa749462c3ab`.

Apply all 17 patches in filename order, then the two SDK patches inside the
SDK checkout. Never mix these with `patches-1.6.1`. The same feature/domain
boundaries are retained; see the [domain map](../docs/development/crashink-patch-domains.md).

The X4 Pro firmware build and 30 selected native regression targets have been
validated. On-device rendering, touch, Wi-Fi, and sleep still require a device
check after installation.

## Substantive v2 adaptations

- Preserve native language-pack and plugin identities, chapter-cache sharding,
  publisher typography, text darkness, ruled lines, word/character spacing,
  SDK display capabilities, and TLS hostname validation.
- Keep the custom clipping color/note format used by existing crASHink devices.
  Session source anchors and a separate native-sync identity sidecar bridge v2
  APIs without reinterpreting incompatible stock clipping records. Stock v2
  clipping files are not imported by this custom-format upgrade.
- Keep v2 alternating CRC/sequence progress records and add reading timestamps.
  Read old crASHink timestamp records without discarding position freshness.
- Use `reader_settings.v2.bin` for the new per-book settings schema. Old v1
  per-book settings files remain intact for rollback; per-book preferences may
  need reapplying in v2. Global preferences remain in their existing store.
- Retain image-only regional grayscale, the custom launcher/Library, frontlight
  borders, Workflowy, and power policy. No other board build environments are
  exposed.
- Each Workflowy quote includes its full `📖 Book — Author — Chapter` source,
  even underneath a `📘 Book — Author` grouping node.

Build/sign through **Build crASHink X4 Pro → baseline 2.0.0**. Select **1.6.1**
to rebuild the untouched archived firmware instead. Both use standard free
GitHub runner choices and retained PlatformIO/dependency/compiler caches.
