# Sensor

Sensor is a small RuneLite plugin that gives clear, at-a-glance feedback for two common gameplay states: **combat** and **skilling**.

The goal is simple: make it easy to see whether the client currently considers you active in combat or actively skilling, without adding a large dashboard or changing normal gameplay.

## Features

- Separate **Combat** and **Skilling** status cards.
- Each card can be enabled or disabled independently.
- Green indicates an active state; red indicates an inactive state.
- Cards can be moved with RuneLite's normal **Alt-drag** overlay controls.
- Cards are resizable and scale their content with the selected size.
- Combat and skilling detection are intentionally generic rather than tied to one activity.

## Detection

### Combat

Combat is considered active for a short period after a hitsplat is detected on the local player or on the actor the local player is interacting with.

### Skilling

Skilling uses two lightweight signals outside combat:

- XP gains in non-combat skills.
- A currently active local-player animation as a fallback.

A short hold period keeps the indicator stable between individual XP drops or repeated actions.

This makes the indicator useful across activities such as Woodcutting, Firemaking, Fishing, Mining, Cooking, Herblore and similar skills without maintaining a separate hard-coded list of actions for every method.

## Why use it?

RuneLite already exposes a lot of detailed information, but sometimes a compact yes/no status is more useful than another full panel. Sensor is intended for players who want a minimal visual confirmation of activity state, especially when using compact layouts, multiple RuneLite panels, streaming layouts or other screen arrangements where space matters.

## Configuration

The plugin keeps configuration intentionally small:

- **Show combat card**
- **Show skilling card**

Position and size are handled by RuneLite's native overlay controls instead of custom window logic.

## Privacy and game interaction

Sensor is read-only and display-only.

It does **not**:

- click or move the mouse;
- send keyboard input;
- choose actions;
- interact with NPCs, objects or widgets;
- automate gameplay;
- communicate with an external server;
- collect or upload account data.

It only reads RuneLite client state required to draw the two local status indicators.

## Local development

Java 11 and Gradle 8.10 are used for local development.

```powershell
$jdk = (Get-ChildItem "C:\Java" -Directory | Where-Object Name -Like "jdk-11*" | Select-Object -First 1).FullName
$env:JAVA_HOME = $jdk
$env:Path = "$jdk\bin;$env:Path"

& "C:\Gradle\gradle-8.10\bin\gradle.bat" run --no-daemon
```

## License

BSD 2-Clause License. See `LICENSE`.
