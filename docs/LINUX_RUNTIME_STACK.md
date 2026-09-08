# Chimera II Linux Runtime and Service Stack

**Last synchronized:** 2026-09-09

## Scope

Koronos exposes a host-runtime catalog for Linux rather than copying third-party binaries into the Java repository. This keeps the Java artifact reproducible and lets Fedora, Ubuntu, Debian, Aurora and Kali use their native package/signing infrastructure.

## Runtime catalog

| Component | Target release / policy | Role |
|---|---|---|
| Python | 3.14.7 | scripting, automation, tooling |
| OpenJDK | 26.0.2.1 | current Java runtime; build track remains Java 25 for compatibility |
| Firefox | latest stable | open-source browser |
| Google Chrome | latest stable | Chromium-based browser |
| Bash | latest distro-stable | POSIX-compatible shell environment |
| Zsh | 5.9.2 | interactive shell |
| PowerShell | 7.6.2 | cross-platform automation |
| .NET | 10.0.400 | current stable/LTS application runtime |
| NGINX | 1.30.4 stable | HTTP/reverse proxy service |
| BIND 9 | 9.20.27 | authoritative/recursive DNS service |
| Postfix | 3.11.7 | open-source mail transport |

Version information is maintained as a catalog and should be refreshed from upstream before each release. Java 26 is the current feature release while Java 25 remains the current LTS release; the project build therefore stays on Java 25 unless a deliberate compatibility migration is approved.

## Kali security environment

The catalog exposes Kali metapackages rather than vendoring an entire Kali filesystem. Supported profiles include information gathering, web assessment, vulnerability analysis, forensics, reverse engineering, exploitation, passwords, wireless, cryptography/steganography, sniffing/spoofing and reporting.

The initial top-tool catalog includes Nmap, Burp Suite, Metasploit Framework, Wireshark, Aircrack-ng, Hydra, John, NetExec, Responder and sqlmap. These are intended for authorized security assessment, lab work and defensive engineering.

Kali's own metapackage mechanism is preferred because it tracks the current package dependency graph and updates. The `kali-tools-top10` metapackage is particularly useful for a compact security profile.

## Installation model

The Java layer should generate or consume distribution-specific package plans; it should not execute an arbitrary shell string. Any future installer must:

1. detect distribution and architecture;
2. use the native package manager;
3. verify repository signatures through the package manager;
4. record installed versions and hashes in the kernel database;
5. never silently replace a security-sensitive executable;
6. expose unavailable packages as capability failures.

## Server roles

A normal Chimera installation may expose HTTP, DNS and mail services as optional service profiles. These services are not automatically enabled merely because their packages are present. Service activation must be explicit and auditable.

## Important boundary

“Embedded” in this architecture means **integrated into the Chimera runtime catalog, startup model and service lifecycle**. It does not mean that Firefox, Chrome, Kali, .NET or other third-party projects are copied into the Java source tree. Their licenses, updates, signatures and native dependencies remain under their respective projects.
