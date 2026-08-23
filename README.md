# Sensor

Sensor is a small RuneLite plugin that shows two compact activity indicators: **Combat** and **Skilling**.

The goal is simple: give a clear, at-a-glance indication of whether the local client currently appears to be in combat or actively skilling, without adding a large panel or changing normal gameplay.

## Features

- Separate **Combat** and **Skilling** status cards.
- Each card can be enabled or disabled independently.
- Green indicates an active state; red indicates an inactive state.
- Cards use RuneLite's normal **Alt-drag** overlay controls for positioning.
- Cards are resizable and scale their contents with the selected size.
- Detection is intentionally generic rather than tied to one specific training method.

## Detection

### Combat

Combat is considered active for a short period after a hitsplat is detected on the local player or on the actor the local player is interacting with.

### Skilling

Skilling uses two lightweight signals outside combat:

- XP gains in non-combat skills.
- A currently active local-player animation as a fallback.

A short hold period keeps the indicator stable between individual XP drops or repeated actions.

This allows the card to work across activities such as Woodcutting, Firemaking, Fishing, Mining, Cooking, Herblore and similar skills without maintaining a separate hard-coded list of actions for every method.

## Why use it?

RuneLite already provides detailed information through many plugins, but sometimes a simple yes/no activity indicator is more useful than another full panel. Sensor is intended for players who prefer a compact layout, use multiple RuneLite windows, stream or record gameplay, or simply want a minimal visual confirmation of activity state.

## Configuration

Sensor intentionally keeps its configuration small:

- **Show combat card**
- **Show skilling card**

Position and size are handled through RuneLite's native overlay controls rather than custom window-management code.

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

It only reads RuneLite client state required to draw the two local activity indicators.

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
