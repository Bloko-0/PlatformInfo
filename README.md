# PlatformInfo

A small Minecraft plugin that shows basic server information. It has separate builds for Bukkit-based servers, Velocity, BungeeCord and Fabric.

## What it does

On Bukkit, Velocity and BungeeCord, use `/platforminfo` or `/pinfo` to see:

- Server or proxy software and version
- Online player count
- Time since the plugin started

The command requires `platforminfo.use`. Bukkit operators have this permission by default.

The Fabric version works a little differently. It prints the Minecraft and Fabric Loader versions during startup and saves them to `config/platforminfo/startup-report.txt`. There is no Fabric command.

## Installation

Download the JAR for your platform from [Releases](https://github.com/Bloko-0/PlatformInfo/releases) and put it in the appropriate `plugins` or `mods` folder.

| File | Platform |
| --- | --- |
| `PlatformInfo-bukkit-1.0.0.jar` | Spigot, Paper, Purpur, Folia |
| `PlatformInfo-velocity-1.0.0.jar` | Velocity 3.4 API / Java 21 |
| `PlatformInfo-velocity-current-1.0.0.jar` | Velocity 4.2 API / Java 25 |
| `PlatformInfo-bungeecord-1.0.0.jar` | BungeeCord |
| `PlatformInfo-fabric-1.0.0.jar` | Fabric dedicated servers |

Use only one Velocity build per proxy. Restart the server or proxy after installing it.

## Building from source

Open the repository as a Gradle project in IntelliJ IDEA and use JDK 25 for Gradle.

On Windows:

```bat
gradlew.bat verifyRelease
```

On Linux or macOS:

```sh
./gradlew verifyRelease
```

The finished JARs, source ZIP and checksums go into `build/release/`.

The project is split into `common/` for report formatting and a module for each platform. Tests cover report formatting, commands and Fabric startup output. The build also checks compilation against several older and newer APIs.

Compatibility checks are not the same as testing on a running server. See [COMPATIBILITY.md](COMPATIBILITY.md) for the versions checked and the remaining limits.

Made by Bloko.
