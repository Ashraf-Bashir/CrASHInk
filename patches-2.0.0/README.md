# CrossInk v2.0.0 port — in progress

Pinned upstream: `2cdd6117568c8877c3180d5fac8d5b20e55d1ac7` (`v2.0.0`).
Pinned SDK: `f7fa9b6871192ea27d8aa37a03beaa749462c3ab`.

This directory is deliberately **not enabled in the build workflow**. The
complete 17-domain stack must be reconciled and built before it becomes a
supported target. Do not mix it with `patches-1.6.1`.

The first four domains are reconciled drafts, in order: settings/branding,
SD storage, status bars, and buttons/gestures. They are not a complete or
firmware-validated port. See the shared [domain map](../docs/development/crashink-patch-domains.md).

Substantive adaptations so far:

- Keep v2 language-pack settings and plugin device identity.
- Retain v2 chapter-cache sharding while moving its roots under hidden
  crASHink storage.
- Preserve v2 clipping format v7 and source/sync identity fields.
- Retain per-bar hide/battery styles and font sizes.
- Port side-button double presses to v2's unified reader button controller,
  preserving immediate physical page turns when no long/double action exists.

Remaining: reconcile fonts, clippings, Readest, chapters, images, frontlight,
Library/Home, power/sleep, SD updates, Workflowy, background work, EPUB styling,
Wi-Fi updates, and the matching SDK patches; then run the relevant tests and
X4 Pro build. The draft font reconciliation is still local work in progress.
