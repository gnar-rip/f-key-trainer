# API and Plugin Hub research — 2026-10-04

Research preceded implementation. Local snapshots were downloaded to `/tmp` for
inspection; none of that external source is bundled into the plugin.

## Source baseline

| Repository | Inspected revision | Purpose |
| --- | --- | --- |
| [RuneLite](https://github.com/runelite/runelite/tree/ddd0669ef4d7494fd420cb507e3c1fa62bab8023) | `ddd0669ef4d7494fd420cb507e3c1fa62bab8023` | Current API, widget definitions, config, event bus, overlays |
| [Official example](https://github.com/runelite/example-plugin/tree/46e14f2c448fab6cbc599df1015b9631dcf1fc52) | `46e14f2c448fab6cbc599df1015b9631dcf1fc52` | Gradle wrapper, Java 11, launcher, standard build metadata |
| [Plugin Hub](https://github.com/runelite/plugin-hub/tree/fce897cde0f107f25454603b3ca7d34f461e417d) | `fce897cde0f107f25454603b3ca7d34f461e417d` | Current manifests, submission requirements, accepted history |
| [RuneLite CS2 scripts](https://github.com/runelite/cs2-scripts/tree/8a5462a44a1554be82fd02e49659e230a18e4491) | `8a5462a44a1554be82fd02e49659e230a18e4491` (2026-09-30) | Native mouse and keyboard tab paths |

Plugin Hub's `runelite.version` and resolved stable build dependency were 1.13.1.
Current master can be ahead of that release; compilation verifies the used APIs
also exist in the stable dependency.

## Input path and chosen interception

[MenuOptionClicked](https://github.com/runelite/runelite/blob/ddd0669ef4d7494fd420cb507e3c1fa62bab8023/runelite-api/src/main/java/net/runelite/api/events/MenuOptionClicked.java)
is dispatched for left-click menu actions as well as selections from a right-click
menu. `consume()` prevents the selected menu action from reaching vanilla action
handling. It does not disable a widget or a keyboard handler.

The inspected scripts show separate entry points:

- [`toplevel_init`](https://github.com/runelite/cs2-scripts/blob/8a5462a44a1554be82fd02e49659e230a18e4491/scripts/%5Bclientscript%2Ctoplevel_init%5D.cs2)
  installs an **on-op** listener on each tab stone. Native widget menu actions
  use `CC_OP` (operations 1–5) / `CC_OP_LOW_PRIORITY` (operations 6–10).
- [`toplevel_redraw`](https://github.com/runelite/cs2-scripts/blob/8a5462a44a1554be82fd02e49659e230a18e4491/scripts/%5Bproc%2Ctoplevel_redraw%5D.cs2)
  installs the root **on-key** listener and configures tab options/subviews.
- [`toplevel_keypress`](https://github.com/runelite/cs2-scripts/blob/8a5462a44a1554be82fd02e49659e230a18e4491/scripts/%5Bproc%2Ctoplevel_keypress%5D.cs2)
  resolves the user's keybind with `keybind_get_slot`, then directly invokes
  `toplevel_sidebutton_op`. It does not create or select a menu entry.
- [`toplevel_sidebutton_op`](https://github.com/runelite/cs2-scripts/blob/8a5462a44a1554be82fd02e49659e230a18e4491/scripts/%5Bproc%2Ctoplevel_sidebutton_op%5D.cs2)
  calls the normal tab-switch procedure; the plugin never intercepts that procedure.

Thus menu-event consumption blocks native mouse menu actions while leaving
keyboard and direct script-driven interface changes alone. No guessing from
recent key timestamps, mouse coordinates, menu text, or selected-tab state is
needed. A raw mouse listener would add geometry, event-thread, menu-selection,
and scaling problems without improving this native interception.

The handler requires an active plugin, enabled configuration, logged-in client,
not-yet-consumed event, native widget action, static child index `-1`, operation
1–10, exact active-layout allowlisted tab ID, enabled tab toggle, and a present,
visible widget. All other events pass through unchanged. It consumes all native
operations on a locked tab button, so right-click shortcuts cannot bypass the lock.
RuneLite-added menu action types are not consumed.

**Provenance limit:** this public event has no hardware-input/source flag. A
third-party plugin explicitly fabricating an identical menu event is
indistinguishable. Native programmatic tab changes bypass menu events, but this
implementation does not claim to identify arbitrary synthetic events from other
plugins. Similarly, custom overlay tabs that call scripts directly bypass it.

## Widgets and layout

[WidgetInfo](https://github.com/runelite/runelite/blob/ddd0669ef4d7494fd420cb507e3c1fa62bab8023/runelite-api/src/main/java/net/runelite/api/widgets/WidgetInfo.java)
is deprecated. Its historical tab aliases were useful for cross-checking layout
families, but some semantic names are stale (notably `IGNORES_TAB` for `STONE8`).
Production code uses only current
[`gameval.InterfaceID`](https://github.com/runelite/runelite/blob/ddd0669ef4d7494fd420cb507e3c1fa62bab8023/runelite-api/src/main/java/net/runelite/api/gameval/InterfaceID.java).

| Layout | Active root | Tab component class |
| --- | --- | --- |
| Fixed | `InterfaceID.TOPLEVEL` | `InterfaceID.Toplevel` |
| Resizable Classic | `InterfaceID.TOPLEVEL_OSRS_STRETCH` | `InterfaceID.ToplevelOsrsStretch` |
| Resizable Modern / bottom-line | `InterfaceID.TOPLEVEL_PRE_EOC` | `InterfaceID.ToplevelPreEoc` |

RuneLite's own
[OverlayOrigin](https://github.com/runelite/runelite/blob/ddd0669ef4d7494fd420cb507e3c1fa62bab8023/runelite-client/src/main/java/net/runelite/client/ui/overlay/OverlayOrigin.java)
uses `isResized()` plus the top-level root to distinguish Classic and Modern.
This plugin checks `Client.getTopLevelInterfaceId()` directly and accepts only
the three known roots, so spectator/mobile/unknown roots cannot default to Classic.
Modern's moved Logout button and different row arrangements retain their own
`STONE10` and other named component IDs. No arithmetic over child IDs is used.

The same suffixes apply to all three component classes:

| Component | Current tab / toggle |
| --- | --- |
| `STONE0` | Combat Options (also Sailing Options when repurposed by OSRS) |
| `STONE1` | Skills |
| `STONE2` | Quests / Journal |
| `STONE3` | Inventory |
| `STONE4` | Worn Equipment |
| `STONE5` | Prayer |
| `STONE6` | Magic |
| `STONE7` | Chat Channels, including Friends Chat and Clan views |
| `STONE8` | Account Management |
| `STONE9` | Shared Friends / Ignore button |
| `STONE10` | Logout tab button, not the action inside the panel |
| `STONE11` | Settings |
| `STONE12` | Emotes |
| `STONE13` | Music |

`toplevel_redraw` explicitly sets Account/Community/Useful links on slot 8,
and swaps Friends List/Ignore List operations on slot 9 according to
`friends_panel`. Independent Friends and Ignore top-level locks would therefore
misrepresent the current interface; one toggle covers that shared button.
Decorative `ICON*` widgets and `SIDE*` panel containers are never targets.

## Accepted plugin comparisons

Inspected three new plugins present in Hub commits dated **2026-10-04**, using
the accepted revisions, rather than assuming repository HEAD is accepted:

- [Where Was I?](https://github.com/RustyLength/rml-where-was-i/tree/9cea1038c40c30fc2ef86270f96988c808f07ebb),
  added in Hub `cdec4f9`: normal config provider, plugin metadata, lifecycle-managed
  overlay, and cleanup. Its journal/storage scope is unnecessary here.
- [Varrock Stray Cats](https://github.com/CornyDonkeh/Varrock-Stray-Cats/tree/c668d0331f91dfffb3b4a844db5649dc1a0069b7),
  added in Hub `008d30c`: `gameval` constants, config annotations, explicit callback
  cleanup, and `build=standard`. Repository HEAD had moved; the accepted SHA was
  separately fetched and inspected.
- [Barrows Crypt Kill Tracker](https://github.com/MaeveMcT/barrows-crypt-kill-tracker/tree/8dcafabc7860a2a7577c0e9eb0843e307993ae1c),
  added in Hub `27f45f0`: Java tests, event subscribers, overlay lifecycle, and
  state clearing. Its gameplay-specific logic is not reused.

Also inspected two directly relevant currently accepted plugins:

- [Custom Game Tabs](https://github.com/FrederickCampbell/custom-game-tabs/tree/f7c861fe0a19995d401cabb3361a4812ed43a88d):
  Modern `STONE*` mapping and the `CC_OP` / static child `-1` menu representation.
  Its old label fallbacks were **not** treated as authority for Account/Ignore.
  Its direct-script custom buttons illustrate the documented compatibility limit.
- [Global F Keys](https://github.com/SirGirion/GlobalFKeys/tree/3ce1c6c52a0e3e6f45e9c43b020429f48126a880):
  key-listener/config lifecycle comparison. No key capture/remapping approach was
  carried into this plugin.

## Compliance review

Reviewed the [current Plugin Hub README](https://github.com/runelite/plugin-hub/blob/fce897cde0f107f25454603b3ca7d34f461e417d/README.md),
[Plugin Hub Review](https://github.com/runelite/runelite/wiki/Plugin-Hub-Review),
[Rejected or Rolled Back Features](https://github.com/runelite/runelite/wiki/Rejected-or-Rolled-Back-Features),
and [Jagex's third-party client guidelines](https://secure.runescape.com/m=news/third-party-client-guidelines?oldschool=1).

Review focuses on security and game-rule compliance; approval does not certify
functionality. This implementation has no identified policy conflict within its
narrow static, user-configured tab-button suppression scope. Final approval is
reserved to RuneLite reviewers.

Production source has no reflection, native calls, processes, network/file I/O,
telemetry, packet access, input generation, script calls, widget mutation, or
server actions. It neither changes click zones nor makes the inventory background
click-through, and it does not remove NPC/player combat actions. The temporary
feedback says only to use the user's tab keybind, without combat recommendations.

The standard build has no extra production dependencies. JUnit/Mockito are local
test dependencies; they are not bundled in the normal plugin JAR. Gradle downloads
are build-time activities. The development client also performs RuneLite's own
normal networking, independently of plugin behavior.
