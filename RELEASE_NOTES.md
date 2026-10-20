# PlatformInfo 1.0.0

Initial release of a shared Java information utility with five installable artifacts.

- Bukkit artifact for Spigot, Paper, Purpur, and Folia.
- Separate Velocity 3.4 API / Java 21 and 4.2 API / Java 25 builds.
- BungeeCord adapter with command permissions.
- Fabric Loader utility that logs and saves startup version information.
- Complete IntelliJ-compatible Gradle source project and SHA-256 checksums.

Server and proxy adapters provide `/platforminfo` and `/pinfo`, requiring `platforminfo.use`. The Fabric artifact provides startup diagnostics only.

Verified by 14 automated tests, five additional API compilation checks, and JAR metadata/bytecode checks. This release has not been tested on every historical version or through a live-server runtime matrix. Consult COMPATIBILITY.md before deployment.
