# Compatibility evidence

Compatibility is recorded at three different levels:

- **Compilation:** the adapter source compiles against the listed published API.
- **Automated behavior:** tests exercise code using MockBukkit or mocked proxy/loader interfaces.
- **Live runtime:** a real server starts and the feature works there. No exhaustive live-server matrix has been run for this release.

## Release 1.0.0

| Adapter or platform | Build evidence | Behavioral evidence | Live runtime coverage |
| --- | --- | --- | --- |
| Spigot/Bukkit | Spigot API 1.8.8-R0.1-SNAPSHOT and 1.20.1-R0.1-SNAPSHOT | Bukkit command tests use MockBukkit 4.116.3 and Paper API 1.21.11 | Not run |
| Paper | Bukkit adapter also compiles against Paper API 1.21.11-R0.1-SNAPSHOT | MockBukkit registration, operator access, denial, console and alias | Not run |
| Purpur | Shares the Bukkit adapter; no separate Purpur API compilation | No Purpur-specific runtime tests | Not run |
| Folia | Same Bukkit adapter; Folia support flag, no world/entity access or scheduling | No Folia-specific runtime tests | Not run |
| Velocity 3.4 API line | 3.4.0-SNAPSHOT, Java 21 bytecode | Permission checks, formatted command output, invalid arguments | Not run |
| Velocity 4.2 API line | 4.2.1-SNAPSHOT, Java 25 bytecode | Same command tests run against the newer API | Not run |
| BungeeCord | 1.16-R0.4 and 1.21-R0.3 | Permission guard, alias metadata, report output | Not run |
| Fabric Loader | 0.12.12, 0.16.14, and 0.19.3; Java 8 bytecode | Startup report with mocked Loader API | Not run |

Minecraft versions and proxy API versions are different compatibility axes. A proxy supporting a client protocol does not mean its plugin API matches every proxy release.

## Deliberate limits

- One JAR cannot cover all Minecraft platforms: entry points and APIs differ.
- The Bukkit artifact targets 1.8.8 onward through stable APIs; that range is not an exhaustive certification.
- The Fabric metadata accepts Minecraft 1.14 onward because this utility only reads loader metadata. That broad declaration does not certify every Minecraft/loader combination or future release.
- Fabric startup reporting is narrower than the server/proxy command feature. No mapped Minecraft classes are bundled or referenced.
- No Forge, NeoForge, Sponge, or Bedrock adapter is included.
- Future API changes can require a new adapter or build even when existing metadata accepts a version.
