# Verification

## Automated

Run `./gradlew clean build` under Java 11. HTML results are in
`build/reports/tests/test/index.html`.

The suite covers:

- Default locks across 14 tabs and 3 layouts; every independent config toggle.
- All 42 explicit widget mappings and unknown/inactive layouts.
- Master disable, live toggle changes, missing/hidden widgets, logged-out state.
- All non-widget action types, dynamic children, invalid operation numbers,
  decorative icons, and tab contents failing open.
- Right-click/low-priority native widget operations and already-consumed events.
- Optional feedback, expiry, replacement, clearing, and plugin shutdown cleanup.
- Event-bus isolation: script and varc events cause no client/widget mutations.

The script-isolation unit test does not execute RuneScape's script engine or
synthesize F-keys. The author has tested mouse blocking and keyboard switching
in all three layouts. The checklist below covers additional regression checks.

## Manual test checklist

Perform these yourself in a safe area. Do not automate game input.
Repeat in Fixed, Resizable Classic, and Resizable Modern (both wide and narrow
window widths / bottom-line arrangements):

1. Assign a Prayer keybind in OSRS. Start on Inventory. Mouse-click the locked
   Prayer icon: stay on Inventory and see one brief reminder.
2. Press Prayer's configured keybind: Prayer opens. Click an actual prayer in
   its panel: it works. Repeat with Combat, Equipment, Inventory, and Magic.
3. Repeat using a non-default keybind and with RuneLite Key Remapping enabled.
4. In Modern, test clicking an already open locked tab and pressing its keybind
   again. The mouse must not collapse it; native keyboard behavior is preserved.
5. Click each unlocked tab. Enable its lock and verify that its icon's mouse
   operations are blocked. Check Account and Friends/Ignore are not reversed.
6. Test right-click selection on locked tabs, including Quests/Journal subviews,
   Prayer/Magic filtering, channel views, and Logout's World Switcher shortcut.
   Unlock them to regain those shortcuts. Tab contents must remain interactive.
7. Turn off feedback: clicks remain blocked, with no reminder or chat messages.
   Disable the master switch, then the plugin: all tab clicks work immediately.
8. Change layouts, resize, hop worlds, log out/in, and restart the plugin.
   Confirm no stale locks or reminders. Test Interface Styles and Stretched Mode.
9. Open/close a bank, shop, and another modal interface. Normal game-driven
   interface changes and their mouse controls must remain unaffected.
10. Confirm unrelated RuneLite-added tab menu options still work. Replacement
    tab plugins that invoke scripts directly are outside this plugin's scope.

## Recorded local run: 2026-10-04

- Java: Eclipse Temurin 11.0.32.1; Gradle wrapper 8.10.
- RuneLite dependency: 1.13.1 (`latest.release` at verification time).
- Final `clean build`: successful; 21 tests, zero failures/errors/skips.
- Normal JAR inspected: only plugin classes, no test/dependency classes.
- Classfile major version 55 confirms Java 11 bytecode.
- Development launch: RuneLite logged `Plugin FKeyTrainerPlugin is now running`
  and completed client initialization. No game input was generated.
- Author confirmed mouse blocking and keybind switching work in Fixed, Resizable
  Classic, and Resizable Modern.
- The remaining checks in the full manual checklist have not been individually
  recorded.
