# Plugin Hub submission preparation

Local source is prepared with the official example structure:

- Package `com.fkeytrainer`, normal `Plugin` / `Config` and `@Provides` wiring.
- Standard `runelite-plugin.properties`, `build=standard`, Java 11 bytecode.
- Official Gradle 8.10 wrapper with its distribution checksum retained.
- `latest.release` RuneLite dependency; no extra production dependencies.
- BSD-2-Clause license, README, source-backed research, tests, manual test matrix.

Before publication, confirm author/name metadata, complete `TESTING.md`, create a
public GitHub repository, and commit/push this source. Build outputs are ignored.
The `shadowJar` task is the official template's optional development bundle; do
not submit that bundle or a precompiled JAR to the Hub.

Create a file named `plugins/f-key-trainer` in your Plugin Hub fork containing:

```properties
repository=https://github.com/YOUR_ACCOUNT/YOUR_REPOSITORY.git
commit=FULL_40_CHARACTER_COMMIT_SHA
```

These are deliberate placeholders, not an actual submission manifest. Replace
both with the published repository and exact tested commit. The PR to
`runelite/plugin-hub` should contain only that manifest addition.

Suggested PR description:

> F-Key Trainer helps players practice their existing OSRS tab keybinds by
> consuming native mouse menu operations on individually selected tab buttons.
> It uses explicit gameval widget IDs for Fixed, Resizable Classic, and Resizable
> Modern, leaving keyboard/script-driven tab changes untouched. There is no
> generated input, widget mutation, networking, or automated action. Feedback is
> an optional temporary overlay. Java 11 build and unit tests pass; include the
> completed live test results before submitting.

Follow the [official submission process](https://github.com/runelite/plugin-hub#submitting-a-plugin).
Respond to CI/reviewer comments in the same PR; if source changes, update the
manifest SHA to the newly tested commit. A future update uses that same manifest
with a new commit SHA. Do not claim Plugin Hub approval before review/merge.
