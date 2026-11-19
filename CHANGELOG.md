## [Unreleased]

### Added

- Wrapped Workflowy tasks render tags and time ranges below the task body in a much smaller font. Compact tasks use a smaller inline font. This is display-only; stored text and edit-cursor positions are preserved, with metadata included in paging.

- Workflowy task time ranges share the light-grey highlighting used for tags, including wrapped ranges.

- A zoomed Workflowy item shows a down triangle that returns to its parent list when tapped. Collapsed items show a left triangle; the Back control remains available.

- New Workflowy tasks show all tag choices immediately. Selected tags are grouped before the task body, preserve the todo marker and cursor, and avoid duplicate prefix tags.

- Workflowy bulk actions include scheduling consecutive time blocks, turning items into todos or bullets, moving to the top/bottom or a parent/date, and adding an item above/below the selection. Edit and Zoom are available for a single selected item.
- Bulk moves preserve selection order, conversions restore the previous type on failure, and selections stay available when only part of a batch can be queued.

- Library/Home has a direct Workflowy calendar shortcut. New calendar blocks default to 30 minutes.
- Workflowy’s time-sort button moves timed items into chronological order above a reusable native divider, preserving the order of other items below it.

- A Workflowy bottom-toolbar button moves unfinished todos in the current list to the following day using the background operation queue. Add remains the rightmost button.

- Workflowy’s email toolbar button filters today’s todo list to reply/response actions. Tap again to show all tasks; the task counter and bulk selection follow the filter.

- Workflowy item actions include Schedule: choose a spot in today’s calendar, adjust the time, and save to return to the task in its list. The time-block picker can also schedule an existing task from today.
- The Workflowy calendar adds Schedule agenda view, vertical hour zoom, completed events with faded backgrounds and strikethrough, and background refresh every minute.

- Workflowy has a time calendar with scrollable day and Monday–Sunday week views. Tasks formatted as `8:30 to 12:00 Title` appear as blocks; `am` and `pm` times are supported.
- Tap an empty time slot to create a task, or a block to open its task and children. Hold a block to move it across times and days, or drag its edges to change its time range in 15-minute steps.

- Workflowy has a Move menu with parent and relative-day shortcuts, a Turn into menu, and day arrows beside the date.
- The Workflowy keyboard suggests common tags when typing `#`, including `#CRITICAL` and `#P0` through `#P4`.
- A tappable Workflowy queue lists pending operations in order and marks the affected rows while requests run.

- In-book image controls are grouped under Layout → Images and saved per EPUB, with a new monochrome mode.

- Zoomed Workflowy items show a tappable breadcrumb with shortened ancestor names and an overflow menu.

- Workflowy multi-select persists across pages and supports Select all, completion changes, moving to a date, and confirmed deletion.

- Workflowy has a month calendar, a text-only editor that opens at the tapped character, and scrollable error details.
- Workflowy supports Undo for completion, reordering, and moving. An eight-second Undo window precedes permanent deletion.
- Drag a Workflowy row down and right to nest it under a sibling. The drop line shows the new level.
- Offline captures are saved to the SD card and sent on reconnect. A header badge shows the pending count.
- Workflowy can add items above or below a sibling and move an item to the top or bottom of its sibling list. An item starting with `[]` becomes a todo.

### Changed

- Workflowy task toolbar order is Back, Calendar, Email, Show completed, Wrap, Refresh, Sort by time, Move unfinished, Undo, Add. Calendar and Email are adjacent, with Add at the far right.

- Workflowy times match existing calendar entries exactly: `7:15pm - 9:00pm`, with lowercase suffixes, no space before am/pm, and a spaced hyphen.

- Workflowy calendar labels, schedule times, time controls, and newly saved task ranges use 12-hour AM/PM format. Time columns reserve enough space for the longer labels.

- The reply filter applies to the current day or zoomed item, including matching bullets. It keeps the current location.
- Workflowy status messages are centered and clipped between the left actions and clock across task, calendar, queue, and time-picker views. Selected calendar resize edges have a bold bar and contrasting grip.

- Workflowy toolbars group navigation and view controls before actions, keeping Add at the far right. Item menus put Edit and completion first and Delete last.

- Workflowy calendar blocks show event titles without repeating their time ranges. New ranges use a dash. Add stays at the far right of each toolbar, and one pending-action indicator sits beside the clock.

- Workflowy removes the calendar icon, gives its tappable date more width, and puts the task counter on the far right. Relative dates keep the regular header font.

- Workflowy displays loading, connection, pending actions, and errors above the outline, with pagination below. Relative day labels use a consistent font size. Navigation icons use stronger strokes on the light background.

- Workflowy puts Refresh in the bottom toolbar and uses the top row for the full date, day arrows, task count, and calendar. The pending badge keeps pagination visible and includes requested item expansion.
- New Workflowy tasks start with an editable checkbox marker. Tag completion includes `@OUTSIDE` and `@INSIDE` and capitalizes the next letter after insertion.

- More Workflowy actions update locally and roll back on failure. Network changes run in the background without a loading popup.
- Workflowy typing reuses the unchanged keyboard and refreshes the edited screen region.

- Book preparation and Library index builds run in the background. Workflowy keeps the outline visible during requests.

- Clipping shares send the quote and any saved note directly to Workflowy over saved Wi-Fi. Sharing also dismisses the highlight toolbar or editor. Sending runs in the background and switches off Wi-Fi if sharing enabled it.

- The date, completed count, Calendar, and Refresh are at the top. Back, Show completed, Add, Undo, and Wrap are at the bottom. Wrapped items use the remaining page space and show inline ellipses on both sides of a page break. Completed items appear below open ones. Wrap and Back use distinct icons without a fill.
- Workflowy tries saved Wi-Fi networks in the background. Text edits, new items, checkboxes, and queued captures save without blocking the outline.
- Arrow taps zoom into the item. Child lists are loaded on demand, cached within a memory limit, and omitted from the outline until zoomed. Known leaves have no arrow.

### Fixed

- Workflowy’s email filter also filters the zoomed parent note, instead of leaving non-matching parent text above reply/respond tasks. Breadcrumb navigation remains available.

- Workflowy loading and operation messages center on the screen across task and calendar views, shifting only to avoid status icons or the clock.

- Workflowy tag backgrounds follow the rendered text advance, leaving spaces before and between #/@ tags unhighlighted.

- Ordinary screen transitions and book opening use fast refresh in both light and dark modes instead of forcing a cleaning pulse. Manual refresh and the configured reader cleaning cadence remain available.
- Zoomed Workflowy breadcrumbs use the text width for short day and parent labels, avoiding large empty gaps.

- Workflowy status text is horizontally centered independently of its log arrow. Time-picker loading/errors share the same header lane, with clipping that protects the clock and left icons.

- Completed items in the Workflowy task list show a checked checkbox alongside strikethrough, including completed bullet-type items. Their checkbox and Mark incomplete actions can restore them; calendar events retain strikethrough without a checkbox.

- The Library icon keeps a single base line, with the extra active-tab underline removed.

- Calendar swipes take priority over taps, preventing scrolling gestures from opening a task or a time-block picker.

- Workflowy cancels a superseded day fetch when refreshing or switching dates.

- Workflowy retries saved Wi-Fi without automatically opening password entry after a failed reconnect. It shows Loading while retrieving the first outline.

- Workflowy preserves the `#` in tags when a new title begins with `[]`.

- The X4 Pro keyboard accepts taps through the edge of each row and uses smaller display updates on SSD1677 panels when possible.

- The Workflowy calendar draws immediately when opened, including while the outline is loading in the background.

- Workflowy Calendar, Refresh, and selection accept recognized taps without requiring a separate touch edge. Selected rows are shaded light gray.

- Workflowy vertical swipes take precedence over tap-to-edit.

- Workflowy closes background reads before sending a foreground save. Errors show in a tappable banner with scrollable details, and Undo stays visible when unavailable.
- The Library and Workflowy handle taps on the frontlight, reader touch, Readest, and Saved Items status icons.

## [v1.5.1] - 2026-09-10

### Added

- Xteink X4 Pro and X4 Classic support, including device-specific firmware and USB Drive access; X4 Pro also supports direct USB file transfers.
- The built-in EPUB optimizer can keep cover art in color while still resizing it to a reader-safe baseline JPEG.
- Custom BMP boot screens, selected in the File Browser or rotated from `/bootscreen` or `/.bootscreen`; sleep screens can also be selected from any folder.
- Quick Lock, assignable button combinations, and shortcuts for Previous Page and Nearby Position Sync. Quick Actions can also be assigned to Power + Up and X4 Pro Home-button gestures.
- Configurable touch page-turn gestures, pinch-to-resize text, two-finger rotation and swipe actions, and a tap-to-hide reader status bar.
- Selectable keyboard layouts, switchable from the keyboard's language key.
- Clippings from dictionary lookups on touch devices, plus selection of text inside EPUB tables.

### Changed

- Touch EPUB readers use a half-height, five-tab menu. Sticky opens the menu with a swipe up and book details with a swipe down; X4 Pro frontlight controls include reading stats and reader shortcuts.
- Screen margins have separate Top/Bottom and Left/Right controls, adjustable up to 200 pixels.
- Night Mode applies system-wide on ESP32-S3 devices; frontlit readers can disable periodic full-screen refreshes.
- Waking keeps the sleep screen visible until the reader or Home is ready, unless a custom boot screen is enabled.
- Font choices show available point sizes, Download Fonts replaces the font-manager label, and Wi-Fi passwords are visible during entry.
- Reader controls, shortcut pickers, touch targets, and File Browser settings are easier to reach; Book Options is last in the button reader menu.
- EPUB indexing, image decoding, fonts, and reading-state updates use fewer resources; the web optimizer prefers natural boundaries when splitting chapters.

### Removed

- The undocumented X4 Pro power-button double-click frontlight toggle.
- Built-in reader-font emoticons and hand gestures; SD-card fonts retain emoji fallback support.

### Fixed

- Clipping highlights stay aligned after font changes, retain multi-paragraph text, and remain readable in Dark Mode. Selection stays on its final page, and browsing saved clippings responds reliably.
- Dictionary lookup respects landscape controls and selected fonts, handles repeated lookups more reliably, and returns to the reader cleanly when dismissed.
- EPUB tables retain column widths and wrap long labels; mixed-direction text, Arabic/Persian shaping, ruby annotations, and footnote styling render correctly.
- EPUB contents links, split-chapter navigation, footnote resumes, and end-of-book exits preserve the intended reading position.
- Large EPUBs, image pages, and SD-font preparation recover more safely from limited memory and SD read errors.
- XTC/XTCH page turns no longer overlap, tables of contents show all entries, and covers retain their grayscale detail. TXT font-size controls and Home progress work reliably.
- Quick Resume, custom sleep images, transparent overlays, and X3/X4 wake refreshes avoid blank screens, grid artifacts, and lingering images.
- Long-press shortcuts no longer trigger an extra action on release; Quick Actions, Quick Lock, and Dark Mode shortcuts respond consistently.
- Touch scrolling, page gestures, font-download cancellation, and reader settings behave reliably across orientations and UI scales.
- KOReader Sync preserves orientation and settings, handles missing remote positions, and avoids repeated screen flashes during network transitions.
- Nearby sync, OPDS search, file listings, and image actions handle input and errors more reliably; Calibre Wireless shows the full IP address.
- S3 sleep, charger detection, and power-button wake behavior are more reliable. USB Drive recovers from storage failures, USB transfers avoid watchdog errors, and updates reject firmware for a different board.
- Book-specific settings stay separate from global defaults, and Recent Books and KOReader credentials survive network restarts.

## [v1.5.0] - 2026-08-08

### Added

- X4 Pro readers can lock the Home button while reading, with a Power-button shortcut to toggle it.
- End-of-book suggestions can now be opened directly by tapping their rows on touch devices.
- Quick Actions lets readers assign up to five favorite reader commands to one Power, Back, or Menu shortcut.

### Fixed

- EPUB tables now lay out a row at a time in both Incremental and Full Section indexing, keeping regular tables readable without whole-table buffering.
- Touch support for Seeed Studio Sticky
- Nearby File Transfer can send EPUB, TXT, XTC, XTCH, PNG, and BMP files directly between two CrossInk devices without a Wi-Fi network.
- Recent Books and image-file long-press actions can send files directly to a nearby CrossInk device.
- Dictionary lookup and lookup history
- EPUB books can use a dedicated SD-card dictionary font while keeping a different reader font.
- EPUB books can set a dedicated dictionary font size independently of the reader font size.
- Dictionary font and size defaults can be set globally from Settings > Reader > Font Options, with per-book choices still taking precedence.
- Reusable dictionary SD-font builder with IPA coverage and per-family ZIP packaging
- RTC-enabled devices can now choose the date format and numeric separator shown in headers from Settings > System > Device.
- The web EPUB optimizer now splits oversized chapters into memory-friendlier sections before sending them to the reader.
- Reader indexing can now use `Incremental` or `Full Section` mode globally or per book; changing modes keeps the current chapter readable and applies when the next chapter needs indexing.
- Look Up Word can now be assigned to short- and long-press Power button shortcuts.
- EPUB readers can now choose from five word-spacing levels, from normal through extra-wide.
- EPUB inline-image pages on X3 now use the grayscale-aware display base before the image grayscale overlay, reducing the moment where images appear too dark before settling.
- EPUB publisher small-caps styling now renders ASCII lowercase text as smaller capital letters without needing extra font files.
- When incremental EPUB indexing runs out of memory at the first unindexed page, the reader now silently restarts once and resumes the book with a fresh heap.

### Changed

- PSRAM-equipped readers now keep EPUB grayscale and image-cache working buffers in external memory, preserving more internal RAM for layout and reducing repeated SD reads on image pages.
- Reader font sizes now persist as actual point sizes, keeping the closest matching size when font families or installed files change.
- SD-card fonts now include the built-in reader fallback stack for common symbols, emoji, and selected CJK glyphs while retaining Noto Sans fallback coverage.
- Downloadable SD-card fonts are now rendered with the same darker anti-aliasing as the built-in reading fonts.
- Full-section EPUB indexing now prepares one-page chapters and direct jumps to a chapter's last page, while avoiding repeated checks after the next chapter is ready.
- EPUB grayscale rendering now reuses its 8 KB strip buffer across stable pages, reducing repeated heap allocation and release during long reading sessions.
- Reading progress is now saved in batches during ordinary page turns, immediately after layout changes, and when leaving a book, reducing repeated SD-card writes without carrying stale pagination into the next session.
- SD-card font discovery now waits until a custom font is selected or font settings are opened, reducing SD-card work during normal startup with built-in fonts.
- EPUB page turns using SD-card fonts now prepare the next page's glyphs while the reader is idle.
- Dictionary lookups now reuse open index files for stem matching, reducing repeated SD-card work after a miss.
- The web file manager now batches directory listings into fewer network packets, improving large-folder response time.
- Firmware releases now identify the supported device type: X3/X4 or Seeed Sticky.
- Image-heavy EPUB chapters now index by reading image headers first and extract each full image only when its page is shown.
- EPUB books with repeated byte-identical stylesheets now parse each unique stylesheet only once when building caches.
- SD-card fonts now reuse their page-sized glyph buffers, reducing heap fragmentation during long reading sessions.
- Firmware builds now prioritize usable heap over oversized system timer stacks and maximum WiFi throughput, leaving more memory for reading and network operations.
- Downloaded-font size range options now show their actual point-size ranges instead of firmware build names.
- KOReader Sync and authentication, OTA updates, and OPDS browsing now restart into a lightweight network mode that leaves reader and Home data unloaded, providing more contiguous memory for WiFi and secure connections.
- The web file manager can now delete non-empty folders recursively and, when hidden files are shown, remove hidden or system-managed SD card items after confirmation.
- SD-font, OPDS catalogs, and other unneeded settings now stay out of memory while reading unless their settings are open.
- EPUB books can now keep more saved clippings without loading every clipping's text into memory while reading.

### Removed

- The font download manager no longer offers a Download All action; fonts can still be downloaded individually or updated together.

### Fixed

- Book menu tab navigation, popup scrolling, customized Reading Stats hints, and short button presses after low-power mode now work reliably.
- Sleep screens now honor the current orientation, avoid X4 transition flashes, fall back to a valid wallpaper when needed, and handle low-memory image decoding without rebooting.
- Choosing Set Cover uses the selected image in place, and Home no longer repeatedly generates missing EPUB covers.
- Finished-book suggestions are now collected before an EPUB is moved to `/Read`.
- Manage Fonts now opens and scans large catalogs more safely on X3/X4, reports low-memory failures instead of restarting, and returns to Font Options when cancelled.
- Network screens refresh cleanly on X4; long errors wrap correctly; saved Wi-Fi networks and KOReader connections recover more reliably after restart or a missing address.
- Translated Wi-Fi and clock labels no longer truncate text or time values, and clock sync no longer risks a reboot while saving settings on memory-constrained X3/X4 devices.
- KOReader Sync no longer crashes during time setup, re-triggers while connecting, or loses precise EPUB positions; CrossPoint-only data stays on the official CrossPoint Sync server.
- Firmware updates reject images for the wrong chip family, and saved Wi-Fi settings safely handle concurrent access and corrupted values.
- EPUB opening, reflow, and background indexing now handle fragmented memory more safely, retry recoverable work, remain responsive to input and setting changes, and show useful errors instead of rebooting or silently returning Home.
- Low-memory EPUB grayscale and sleep rendering now fall back safely without leaving stale display content.
- Full-section indexing preserves more memory for large chapters and cancels speculative work on page turns, keeping the reader responsive.
- SD-card font and clipping work now release temporary data at the right time, preserving memory for reflow, dictionary use, covers, and thumbnails on X3/X4.
- EPUBs with book-specific built-in fonts no longer load an unnecessary global SD-card font, and custom fonts retain ligatures.
- EPUB styling choices apply before style caches load; CSS-heavy books use less temporary memory; and disabling Embedded Style consistently skips unused stylesheet work.
- EPUB layout now keeps CJK ruby and spaces, Russian paragraph continuations, Focus Reading, underline/strikethrough runs, and right-to-left text correct.
- EPUBs with flowing `<br>` elements, image-led or decorative chapter headings, unsupported images, and dense final pages now lay out without excess gaps, clipping, dropped images, or misleading low-memory warnings.
- EPUB footnote and cross-reference previews now show complete notes, including targets in the middle of a paragraph.
- Saved EPUB positions, clipping highlights, and selections now stay accurate after font, orientation, or indexing changes; selections also remain readable in dark mode and on memory-tight pages.
- Dictionary misses can switch dictionaries without leaving the reader, and dictionary read failures now report an error instead of a false “not found.”
- Reader popups, KOReader Wi-Fi labels, Lyra battery headers, and the sleep message now remain correctly oriented and positioned.
- Manual refreshes preserve EPUB and TXT text anti-aliasing; XTC and XTCH status bars show the configured time-left estimate.
- Watchdog panics with captured diagnostics open crash reporting, while reset-only events return normally; power-button wake timing no longer depends on SD-card startup.
- The web file manager and uploads now handle simulator/device ports and stalled connections safely; unsupported settings stay hidden, and the optimizer removes empty chapter stubs without breaking table-of-contents links.

## [v1.4.0.1] - 2026-07-28

### Added

- Updates to support Xteink device detection so the correct display panel driver is used.

## [v1.4.0] - 2026-07-10

### Added

- Dashboard UI theme for the Home screen, showing the current book cover and reading stats.
- Nearby Position Sync for sending or applying the current EPUB position between two CrossInk devices over ESP-NOW.
- Web EPUB optimizer support for CrossInk location metadata, so optimized EPUBs can keep better progress and stable page numbers.
- Reading Stats support for XTC and XTCH books, including reader menus, Home and sleep screen stats, mark finished, delete stats, and preserving stats when clearing book caches.
- Web file manager image previews, so PNG, JPEG, BMP, GIF, and WebP files can be viewed inline before downloading.

### Changed

- Large EPUBs, SD-card font-heavy books, and cover thumbnails now open, index, and generate more reliably under low-memory conditions.
- Home and sleep screens now load more cover and thumbnail data only when needed, reducing reader startup work and reusing cached cover data where possible.
- Built-in reader font choices have been reduced to Lexend Deca and Bitter, reducing firmware size while keeping fallback glyph coverage.

### Removed

- Teensy firmware builds are no longer produced for releases or release candidates.

### Fixed

- EPUB render-mode and Safe Mode toast messages now clear reliably, even when the reader is low on memory.
- EPUB Reading Stats no longer drops unsaved page-turn counts after viewing the stats screen mid-session.
- KOSync is more reliable with many SD-card fonts installed, reducing low-memory failures during secure sync requests and uploads.
- Web file manager actions now handle filenames with special characters safely and reject unsafe rename characters before saving.
- Auto Turn interval settings and related action prompts opened from long-press shortcuts now stay open after releasing the shortcut button.
- EPUB footnote previews no longer show clipped status-bar labels or misleading reader progress indicators, and clipping selection now works from footnote previews.
- Font selection no longer reopens the font preview after choosing a font.
- EPUB chapters with stale publisher style data now rebuild it instead of opening without the book's styling.
- Large SD-card font EPUBs no longer overlap characters after font or line-spacing changes, and clipping selection can fall back to a built-in UI font when needed.
- EPUB cover and thumbnail generation is more reliable with custom SD-card fonts selected and optimized books under low-memory conditions.
- Web EPUB optimizer now preserves more PNG and SVG artwork on-device, including transparent PNGs, dividers, and images in malformed or XML-declared chapters.
- Unsupported SVG images in EPUB chapters are now skipped silently instead of triggering low-memory image warnings.
- Nearby Position Sync now silently restarts back into the reader after using ESP-NOW, matching other WiFi sync flows and reducing post-sync memory fragmentation.
- EPUB page cache loading now uses fewer small heap allocations, reducing fragmentation-related reader failures.
- EPUB grayscale page turns on X3 now use the grayscale-aware display base, reducing the moment where new text appears too dark before the anti-aliased overlay finishes.
- EPUB chapters with many inline anchors, footnote links, malformed XHTML, large publisher styles, or SD-card fonts are less likely to fail or get stuck on the indexing screen.
- EPUB opening and image rendering now recover from more low-memory conditions instead of rebooting, including landscape image pages and books that need lighter render modes.
- EPUB clipping selection now follows right-to-left line order when selecting Hebrew and other RTL text.
- Lyra Carousel no longer shows a blank carousel after returning from WiFi-related File Transfer screens and moving between the menu row and book row.
- Generated SD-card font packages now include the same core glyph coverage as built-in reader fonts.
- Manage Fonts no longer crashes while loading or reloading large SD-card font lists.
- Minimal Home no longer swaps to another recent book when returning from Settings when Back button is mapped to the first button.
- Cancelling a font download now stops on the first Cancel button press instead of needing several presses.
- The `Inverted` sleep cover filter now keeps book covers unchanged on Minimal and Dashboard sleep screens while switching the background to white.
- Rare EPUB open or thumbnail crashes during ZIP decompression are fixed.

## [v1.3.4] - 2026-06-24

### Added

- File Browser now indexes large SD-card folders so directories with many books can be browsed without loading every filename into memory at once.
- EPUB text clipping with saved highlights, clipping lists, and Kindle-style `/My Clippings.txt` export.
- `Create Clipping` is now available as a reader shortcut for short/long Power, long-press Menu, and long-press Back actions.
- Per-book EPUB options for font, layout, styling, reading aids, and render modes, including `CrossInk Default`, `Balanced`, and `Light` modes for difficult books.
- Arena allocator (`lib/Memory/Arena.h`) for burst-then-discard allocation patterns - reduces heap fragmentation during EPUB parsing and page layout over long reading sessions.
- Optimized EPUBs now store location metadata at `META-INF/x-locations.json`.
- X3 SD-card writes now use the RTC for file timestamps when the clock is available.

### Changed

- The EPUB reader menu now splits the growing menu into 3 screens, labels per-book settings as `Book Options`, and avoids showing duplicate `Orientation` controls.
- The `Inverted` sleep cover filter now flips Minimal and Reading Stats sleep screens to black text on a white background.

### Fixed

- Quick Resume no longer shows a blank page after EPUB next-chapter indexing.
- Calibre Wireless transfer status no longer stacks the last received-file message on top of the upload percentage.
- X3 Tilt Direction now labels left/right choices as `Left-Right` and `Right-Left`, with existing left/right preferences migrated to keep the same physical tilt behavior.
- EPUB layout now honors publisher page-break CSS, avoids stretching justified spaces before closing punctuation, and keeps large CSS rule sets in a smaller disk-backed lookup cache.
- EPUB first-open conversion now uses more compact OPF manifest lookups and streams cover-wrapper parsing to avoid large temporary heap buffers on books with huge manifests.
- EPUB chapters that run out of memory now retry with `Balanced`, `Light`, and final `Safe Mode` rendering before showing an error, apply the same fallbacks during next-chapter pre-indexing, and let book action menus reset a book's reader settings if Safe Mode still cannot open it.
- EPUB reader font-size changes now restore the current chapter position by content instead of jumping far backward after re-indexing.
- Reading Stats now use the reader's last live book time-left estimate instead of showing a separate fallback estimate.
- Per-book reading stats now migrate compatible legacy `stats.bin` files into the `stats_v5.bin` flow instead of resetting when only the old filename exists.
- Lyra Carousel Home menu rendering now avoids extra label allocations that could crash builds under low memory.
- Lyra Carousel Home cover refresh no longer risks a reboot when memory is tight after returning to or selecting a recent book.
- EPUB image-heavy chapters no longer risk a reboot while saving their reading cache under low memory.
- TXT readers now stay open when pressing a page-turn button at the end of the file.
- Long-press reader shortcuts that open another screen no longer close or confirm it again when releasing the shortcut button.
- RoundedRaff's header battery icon and percentage now sit lower to avoid clipping at the top edge.
- Lyra Carousel now keeps the Home header current when rendering the menu or restoring cached carousel frames, preventing stale battery and clock values while navigating between books.
- Web file manager multi-delete now handles larger selections without failing after a small batch.
- Portuguese EPUBs now use Portuguese hyphenation rules instead of leaving long words unhyphenated when Hyphenation is enabled.
- Progressive JPEG EPUB covers now render more smoothly in generated cover and thumbnail BMP assets.
- EPUB section layout now flushes long text runs earlier when Focus Reading or Guide Dots are enabled, reducing low-memory failures on difficult books.
- Footnotes in EPUBs with very large shared notes sections no longer cause long stalls when opened.
- Firmware updates now follow GitHub asset redirects before streaming the install.
- Tiled grayscale rendering now serializes display transfers on the shared SPI bus to avoid display glitches during SD activity.

## [v1.3.3] - 2026-06-13

### Added

- `File Browser Display` in `Settings > System > Files & Cache` for choosing one-line or two-line file browser rows across all themes, while preserving Minimal users' existing two-line display on upgrade.
- `Hide File Extension` in `Settings > System > Files & Cache` for expanding file-browser filenames by hiding the right-side extension label.
- Device Name in Settings > System > Device for customizing the KOReader Sync and Nearby Stats Sync device label.
- Additional shortcut options and new ability to add custom shortcuts for Long-press Back Action.
- Delete Reading Stats actions in the EPUB reader and book action menus for clearing one book's stats without deleting its cache.

### Changed

- CrossInk settings now save to `/.crosspoint/crossink-settings.json`, with a one-time fallback migration from `/.crosspoint/settings.json`, so switching between firmware builds is less likely to reset preferences.
- The X3 clock visibility setting is now phrased as `Hide Clock`, with existing `Show Clock` preferences migrated to the matching hide behavior.

### Fixed

- RoundedRaff's date shown in settings now sits lower on X3 devices instead of overlapping the battery.
- Clear Bookmark List now asks for confirmation before deleting a book's bookmarks.
- Clear Reading Cache now preserves per-book reading stats while continuing to leave all-time reading stats untouched.
- Moving finished EPUBs to `/Read` now consistently preserves reading progress, per-book stats, bookmarks, and resume state.
- Book settings option lists now return to the submenu they were opened from when pressing Back.
- Lyra Carousel now refreshes its cached Home icon row after OPDS, Reading Stats, or Bookmarks icons appear or disappear.
- KOReader Sync failure screens now wrap long error messages and shut down WiFi cleanly before returning to the book.
- Sleep Screen > Cover now generates the current book cover on demand instead of falling back to the dark sleep screen when the setting is changed after opening a book.
- File Browser now previews PNG images instead of trying to open them as EPUBs, and hides common macOS and Windows metadata files.
- File Browser now refreshes immediately after falling back to the root folder from a stale saved path.
- File Browser now stops loading oversized folders before low memory can crash the device and shows a memory error instead.
- TXT reader long-press Power page turns now work when Long Power Button is set to Page Turn.
- SD-card font read failures no longer risk a reboot while cleaning up the failed file read.
- Page Overlay sleep screens no longer force EPUB chapters to re-index after waking.
- Page Overlay sleep screens now use the current screen as the overlay background outside the reader instead of trying to rebuild a stale book page.

## [v1.3.2] - 2026-06-10

### Added

- Current date in the top-right Settings header on X3 devices.
- Dark Reader Mode for EPUB and TXT reading screens, plus shortcut actions for the power button and front-button long press.
- File Browser long-press folder action for choosing a custom sleep-image folder instead of only `/.sleep` or `/sleep`.
- Expanded X3 Reading Stats, including streaks, time charts, editable dates, all-time backups, reset controls, an idle-time threshold, and the `Minimal Stats` sleep screen.
- `Reset Reading Pace` in the EPUB reader menu when Time Left is enabled, for clearing only the time-left pace estimate while keeping book reading stats.

### Changed

- Display, Reader, and Controls settings now open list menus instead of cycling through options one by one.
- The X3 clock visibility setting is now phrased as `Hide Clock`, with existing `Show Clock` preferences migrated to the matching hide behavior.
- Reading time and time-left pace tracking now ignore page intervals longer than the configured idle-time threshold.
- Web portal pages now use shared templates, stylesheet, and logo assets, reducing on-device page size and improving browser caching.
- Already-cached EPUBs now open directly to the first page without an extra book-loading popup refresh.
- Reader font-size choices now show point sizes like `10 pt` instead of names like `Tiny`.

### Fixed

- Inverted reader menus now honor orientation-aware side-button navigation.
- EPUB book time-left estimates now wait for more session pace samples and use a progress-based floor after pace data exists, reducing swings from unusually short or long pages.
- Deleting an EPUB book cache now preserves that book's reading stats and pace data.
- X3 clock settings now have clearer UTC offset editing, and `Sync Date/Time` can use saved WiFi networks automatically.
- Home, Lyra Carousel, WiFi setup, and SD-card font flows now release memory more aggressively to avoid freezes or crashes on constrained builds.
- Vietnamese settings labels no longer show replacement diamonds after generated translation offsets shifted.
- KOReader Sync now lands correctly at chapter starts and shows more specific connection guidance.
- EPUB bookmarks saved under the old unstable path hash now show up again, including for books moved to `/Read`.
- SD-card font downloads now use versioned direct S3-hosted HTTP endpoints with CRC validation, avoiding GitHub release redirects and ESP32-C3 TLS stalls when loading the font catalog.
- EPUB text blocks now keep the book's alignment style when an inline image appears before the text.

## [v1.3.1] - 2026-05-28

### Added

- EPUB reading-position improvements, including bookmark anchors, bookmark preview snippets, and optional chapter/book time-left estimates.
- Nearby Reading Stats sync with separate totals for this device and all synced CrossInk readers.
- Per-server OPDS filename settings so downloaded books can use either Author - Title or Title - Author.
- EPUB render heap diagnostics that include the largest allocatable block, not just total free heap.

### Changed

- Moved the X3 reader clock into a new top-centered status bar and moved clock settings to Settings > System > Device.
- Reworked Display, Reader, Controls, in-reader options, and larger System settings groups so related options open as submenus.
- Improved OPDS and font download responsiveness by reducing progress-update overhead and temporarily disabling WiFi power saving during transfers.
- Book selection now shows a loading popup before EPUB indexing or cache loading begins.
- Delayed the automatic finished-book prompt until the reader leaves the chapter where they reach 99%.

### Fixed

- WiFi settings screen now keeps the displayed MAC address consistent with the router-visible WiFi address.
- Reader UI issues with inverted menu button hints, Lyra Carousel popups, and Auto Page Turn interval persistence.
- Web uploads and KOReader Sync progress saves now preserve progress, stats, settings, and valid resume data for refreshed book files.
- OPDS low-memory handling now shows a specific parser-buffer memory message and releases SD-card fonts before catalog loading.
- EPUB cache, CSS, table, SD-card font, and allocation failure paths now recover, retry, or stop cleanly under low memory instead of opening unstyled pages, failing unnecessarily, or risking a reboot.
- EPUB text with invisible word-joiner characters no longer shows replacement diamonds for missing font glyphs.
- Clarified the low-memory EPUB image warning so it says some or all images may be missing.

## [v1.3.0] - 2026-05-21

### Added

- Back/Cancel support while downloading books from OPDS catalogs.
- Recent Books long-press menu in both List and Grid views with delete, cache delete, completion, and remove-from-recents actions.
- Minimal sleep screen option that shows the current book cover and reading progress on a dark background.
- More detailed WiFi connection debug logs for scans, selected networks, status changes, disconnect reasons, and timeouts.
- 9pt `Itty Bitty` reader font size, plus build flags for omitting Itty Bitty and Large reader font assets in size-constrained firmware variants.
- In-reader confirmation message when a shortcut turns tilt-to-turn on or off.

### Fixed

- WiFi and OPDS connection-flow edge cases: manual Settings connections now show the connected status before continuing, copied or corrupted saved-password files are rejected before use, OPDS retries show loading before requests, and large OPDS feeds fail safely under low memory instead of rebooting.
- Reader and Home UI polish issues, including landscape status-bar settings, missing Vietnamese labels, File Browser and Lyra Carousel icon alignment, cover thumbnail artifacts, and duplicate Home progress/stat loading.
- EPUB cache and low-memory handling now use stable cache folder keys, migrate older cache folders where possible, rebuild stale section caches, lay out very long text blocks earlier, stream table fallback content when heap is tight, and clarify the warning text.
- Sleep-entry, network, and SD-card font download reliability improvements: cached sleep-screen assets are reused, OPDS pages idle normally after load, the X3 tilt sensor sleeps outside the reader, WiFi power saving is disabled during transfers, WebDAV stack usage is lower, longer stalls are tolerated, interrupted font files are retried, and active reader fonts are freed when needed.
- Remaining reader service edge cases, including an XTC chapter selector crash on memory-constrained builds, SD-card font size selection, SD-card font-size shortcuts skipping manually installed sizes, and KOReader Sync login compatibility with self-hosted servers that return valid JSON on success.

### Changed

- Modified upstream "page-as-sleep" behavior into a new `Sleep Screen > Quick Resume` option, which also keeps `Quick Resume on Timeout` on, and renamed the timeout-only toggle.
- Improved reader and browser menu behavior by moving the Footnotes shortcut above Select Chapter, wrapping long book titles in action menus, and reducing progress-screen repaint work during OPDS and SD font downloads.

## [v1.2.11.1] - 2026-05-15

### Changed

- Removed Medium font size from `xlarge` build to get it below the size limit

### Fixed

- Lyra Carousel is now included by activating the build flag `DCROSSINK_ENABLE_LYRA_CAROUSEL=1`

---

## [v1.2.11] - 2026-05-14

### Added

- New personal theme: "Minimal"
- Custom sleep timer picker so `Time to Sleep` can be set from 1 to 30 minutes instead of cycling fixed presets.
- In-reader Controls shortcut for customizing buttons without leaving the book.
- Bookmark cleanup shortcuts: hold Select on a bookmark to delete it, or hold Open on a book in Bookmarks to clear that book's bookmark list.
- Confirmation message after deleting a book's cache from the reader or File Browser.
- File Browser long-press action for deleting an EPUB or XTC book's cache.
- Downloaded-font size range setting so SD-card fonts can use compact, default, or large point-size sets.
- File Browser long-press action for marking EPUB books as finished or unfinished.

### Changed

- Hardened deep sleep entry by shutting WiFi down before waiting for the power button to be released.
- Raised the web file-transfer filename limit from 100 to 150 bytes so longer uploaded filenames are preserved.
- Made the in-reader Reader Options menu include the same Reader settings and actions as Settings > Reader.
- Split SD-card font descriptions and supported languages into separate lines in the font download screen.

### Fixed

- Inline EPUB images no longer disappear in landscape when their bottom edge slightly overlaps the screen margin.
- Reduced unnecessary low-memory image suppression for JPEG-heavy EPUB chapters and added CSS heap diagnostics during chapter rebuilds.
- Allowed wider inline JPEG images in EPUBs to render when they still fit the total pixel and heap safety limits.
- SD-card font picker no longer reopens immediately after selecting a font from Settings > Reader > Font Family.
- In-reader font-size changes now work for SD-card fonts.
- In-reader SD-card font changes now rebuild the current EPUB page layout consistently.

## [v1.2.10] - 2026-05-11

### Added

- `Recent Books View` setting so the dedicated Recent Books screen can switch between the classic list and a 3x3 cover grid.
- More flexible reader controls, including orientation-aware front/side button settings, nav-only or all-button front inversion, tilt page turn shortcuts, and side-button long-press rotation actions.
- Per-session auto page turn interval picker with values from 5 to 120 seconds.
- File Browser Home/Back long-press action for toggling hidden files and folders.
- EPUB rendering and diagnostics improvements, including visible `<hr>` separators and heap logs around section rebuilds, image extraction, page serialization, and sleep-cache rebuilds.
- Reader font coverage for block redactions, black-square ornaments, Greek category letters, and turned-comma punctuation (PR #104).
- Simulator tools for testing sleep/wake behavior and smoke-testing common screens and EPUB reader menus.

### Changed

- Reduced Controls settings section spacing so the grouped controls fit better on X3 screens.
- Made front reader long-press actions trigger when the hold delay is reached while normal page turns still trigger on release.
- Used the fast EPUB spine/TOC indexing path for books with 300+ spine entries so heavily split books build `book.bin` faster on first open.
- Allowed the web file manager and WebDAV to browse dot-prefixed hidden files when hidden files are enabled, matching the device file browser.

### Fixed

- Reader button and shortcut behavior, including X3 power-button wake filtering, folder delete long-press timing, and WiFi scan/connect screens that could not be exited while work was in progress.
- RoundedRaff home-menu, keyboard, and button-hint rendering issues so Settings remains reachable and compact labels no longer overlap or disappear.
- Font and glyph handling now reduces persistent SD-card font advance-cache memory, releases optional font caches before image extraction only when heap is tight, and shows a visible replacement symbol when compact UI fonts lack `U+FFFD`.
- KOReader Sync authentication diagnostics and an in-reader sync crash, including clearer handling when a server or proxy returns non-JSON content.
- EPUB text rendering for redactions, whitespace-only XHTML text nodes, simple black CSS span backgrounds, list bullets in `<li><p>...</p></li>` items, and very long base64-like text runs.
- EPUB image, thumbnail, and section-rebuild stability so image-heavy chapters use less temporary memory, scale images more reliably, avoid stale dimensions, and suppress optional image work earlier under heap pressure.
- EPUB low-memory and cache safety now skips optional next-chapter indexing and sleep-page cache rebuilds when heap is tight, fails safely with a malformed-book warning and Home exit path, rebuilds incompatible fork-written caches, and handles low-memory CSS parsing, truncated SD writes, invalid serialized strings, and failed temp-cache promotion.
- Home no longer crashes after clearing reading cache when the source EPUB cache is missing.
- Reader prewarm behavior now skips image decoding, keeps mixed-style font glyphs cached together, and avoids section rebuilds for render-quality-only option changes.
- Concurrent render/storage crashes are avoided by serializing `GfxRenderer` scratch-buffer access, shared SPI bus access, and failed SPI lock cleanup.
- Recent Books, EPUB/XTC thumbnail caches, deleted-folder metadata, and XTC cover scaling now keep cached book data in sync and grid covers fill their slots correctly.
- Simulator build configuration now lets SDL2 and simulator-provided network/OTA shims compile cleanly.

---

## [v1.2.9.1] - 2026-05-03

### Changed

- Cleaned up EPUB table rendering by removing synthetic row/cell labels and defaulting table cells to readable left alignment
- Allow simple EPUB tables with full-width note rows so a single `colspan` cell spanning the whole table no longer forces the entire table back to paragraph fallback

### Fixed

- Power-button shortcut conflicts outside the reader so reader-only actions fall back to `Confirm` while Sleep, Refresh, Screenshot, Sync Progress, and File Transfer remain real power actions.
- Potential crash when using `Go to %` in EPUBs.
- Potential crash when entering sleep with Page Overlay enabled if the cached EPUB page data is invalid.
