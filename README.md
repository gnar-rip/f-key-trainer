# F-Key Trainer

Keep clicking your tabs out of habit? F-Key Trainer blocks mouse clicks on the
tabs you choose, so you have to use your keybinds instead.

Click Prayer out of habit. Nothing happens. Oh yeah—F-key.

## Setup

1. Set your tab keybinds in OSRS settings.
2. Open **F-Key Trainer** in RuneLite's plugin settings.
3. Choose which tabs to lock.

Combat, Inventory, Equipment, Prayer, and Magic are locked by default. The other
tabs are optional. Turn off **Enable trainer** whenever you want mouse clicks back.

**Show feedback when blocked** adds a brief “Use your tab keybind” reminder.
You can turn it off without changing your locks.

## What gets blocked?

Only the tab buttons. Once you've opened a tab, everything inside it works as
usual. Your existing keybinds stay the same.

Right-click options on a locked tab button are blocked too, including shortcuts
like spell filtering and World Switcher. Unlock that tab to use those shortcuts.

Friends and Ignore share one toggle, as do the chat channel tabs. Plugins that
replace the normal tab buttons may bypass the lock.

Works with **Fixed**, **Resizable Classic**, and **Resizable Modern**. Mouse
blocking and keyboard switching have been tested in all three.

## Development

Not yet available on the Plugin Hub. To run it locally, use JDK 11:

```bash
./gradlew build
./gradlew run
```

Set `JAVA_HOME` to your JDK 11 installation if you have multiple Java versions.
On Windows, use `gradlew.bat`. Jagex account users can follow RuneLite's
[development login guide](https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts).

[Testing notes](docs/TESTING.md) · [API and implementation notes](docs/RESEARCH.md)

By **justLush** · [BSD-2-Clause license](LICENSE)
