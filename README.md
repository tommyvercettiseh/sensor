# Sensor

Minimal RuneLite status sensor for clear on-screen state detection.

## v0.1

Shows one compact overlay with:

- **COMBAT** — green when combat was detected recently, otherwise red.
- **SKILLING** — green when the local player has an active animation outside combat, otherwise red.
- **STATE** — `COMBAT`, `SKILLING`, or `IDLE`.

Combat is held for 5 seconds after a hitsplat is detected on the local player or the actor the local player is interacting with. Skilling is intentionally broad in v0.1: any active player animation outside combat counts as skilling.

## Local run

Use Java 11 and Gradle 8.10:

```powershell
$jdk = (Get-ChildItem "C:\Java" -Directory | Where-Object Name -Like "jdk-11*" | Select-Object -First 1).FullName
$env:JAVA_HOME = $jdk
$env:Path = "$jdk\bin;$env:Path"

& "C:\Gradle\gradle-8.10\bin\gradle.bat" run --no-daemon
```

This project is visualization-only. It does not click, move, or automate gameplay.
