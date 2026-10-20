# PlatformInfo

A small Java utility with separate adapters for Bukkit-family servers, Velocity, BungeeCord, and Fabric dedicated servers.

PlatformInfo demonstrates shared Java code, platform entry points, command permissions, automated checks, and separate release artifacts. Created by Bloko.

## Features

On Bukkit-family servers and proxies, `/platforminfo` (alias `/pinfo`) shows the platform, software version, online player count, and time since the plugin started. Access requires `platforminfo.use`. Bukkit operators receive it by default; grant it explicitly through the proxy's permission system.

The Fabric adapter logs the Minecraft and loader versions during startup and writes `config/platforminfo/startup-report.txt`. It uses Fabric Loader APIs only, without Minecraft mappings or Fabric API. It does not add an in-game command or track players.

No network requests, metrics collection, player names, IP addresses, world changes, or persistent player data are involved.

## Choose one artifact for each server process

| Artifact | Destination | API baseline and requirements |
| --- | --- | --- |
| `PlatformInfo-bukkit-1.0.0.jar` | Server `plugins/` | Spigot API 1.8.8; Java 8 bytecode. Intended for Spigot, Paper, Purpur, and Folia. Use the Java version required by your server. |
| `PlatformInfo-velocity-1.0.0.jar` | Proxy `plugins/` | Velocity API 3.4.0; Java 21 or newer. |
| `PlatformInfo-velocity-current-1.0.0.jar` | Proxy `plugins/` | Velocity API 4.2.1; Java 25 or newer. |
| `PlatformInfo-bungeecord-1.0.0.jar` | Proxy `plugins/` | BungeeCord API 1.16-R0.4; Java 8 bytecode. |
| `PlatformInfo-fabric-1.0.0.jar` | Dedicated server `mods/` | Fabric Loader 0.12.0 or newer; Minecraft 1.14 or newer declared in metadata; use the Java version required by Minecraft. |

Install only one Velocity variant per proxy. Restart the relevant process after copying its JAR.

The Bukkit adapter uses legacy command APIs, declares `api-version: 1.13` for modern servers, and declares Folia support. It performs no world/entity operations and schedules no tasks. Older Bukkit servers ignore the modern API declaration.

These are target ranges and API baselines, not a claim that every server version has passed a live test. Paper and Purpur use the Bukkit artifact; Folia uses that artifact with the Folia metadata flag. Forge, NeoForge, Sponge, Bedrock servers, and other platforms are not implemented in this release.

## Verification

Run `gradlew.bat verifyRelease` on Windows, or `./gradlew verifyRelease` on other systems, with JDK 25 installed. The wrapper downloads Gradle and dependencies on the first build.

The build runs 14 automated tests: shared report formatting, Bukkit command registration/permissions/console dispatch in MockBukkit, proxy command behavior with Mockito, and Fabric startup-report writing with a mocked loader. The Velocity command tests run against both API lines.

Additional source compilation checks cover Spigot 1.20.1, Paper 1.21.11, BungeeCord 1.21-R0.3, and Fabric Loader 0.12.12 and 0.19.3. The packaged adapters compile against Spigot 1.8.8, BungeeCord 1.16-R0.4, Fabric Loader 0.16.14, and Velocity 3.4.0 / 4.2.1.

Packaging checks confirm the entry-point metadata, shared report class, and expected bytecode level in each installable JAR. These automated checks do not start real Minecraft servers or establish runtime coverage across every historical version. See [COMPATIBILITY.md](COMPATIBILITY.md).

## Source and builds

Open the project directory in IntelliJ IDEA as a Gradle project and select JDK 25 for the Gradle JVM. Each adapter has its own module; the common report lives in `common/`.

`verifyRelease` produces five installable JARs, the complete source ZIP, and `SHA256SUMS.txt` in `build/release/`. Source archives exclude IDE state, build caches, and Git metadata. GitHub Actions repeats the build and uploads the release artifacts.

[Releases](https://github.com/Bloko-0/PlatformInfo/releases) · [Issues](https://github.com/Bloko-0/PlatformInfo/issues)
