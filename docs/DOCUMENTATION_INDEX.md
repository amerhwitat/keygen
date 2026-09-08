# Chimera II Java Documentation Index

**Last synchronized:** 2026-09-09

This directory documents the Java 25 implementation track of Chimera II OS. Documentation is organized so that architecture, migration provenance, Linux desktop integration, persistent self-learning, Linux runtime integration, GNU/open-source interoperability, licensing, cross-platform interoperability and trusted-node federation can be understood independently.

## Primary documents

| Document | Purpose |
|---|---|
| `README.md` | Project overview, scope, build, architecture and compatibility boundaries |
| `docs/JAVA_RUNTIME_ARCHITECTURE.md` | End-to-end Java architecture and subsystem relationships |
| `docs/KERNEL_LEARNING_DATABASE.md` | Self-learning kernel loop, H2 persistence and restart behavior |
| `docs/LINUX_RUNTIME_STACK.md` | Python, Java, browsers, shells, PowerShell, .NET, HTTP, DNS, mail and Kali integration |
| `docs/LINUX_RUNTIME_INSTALLATION.md` | Distro-aware package/service planning and installation security boundaries |
| `docs/CROSS_PLATFORM_COMPATIBILITY.md` | IPv4/IPv6 scanners, Windows/Win32 compatibility, W2K-ASM provenance, filesystems, SMB, AD, NFS, Samba and macOS/Finder |
| `docs/GNU_CROSS_PLATFORM_SERVICES.md` | GNU utilities, Inetutils/GnuTLS service roles and Windows MSYS2/Cygwin/WSL2 interoperability |
| `docs/LICENSING.md` | GPLv3-or-later project license and third-party licensing boundaries |
| `docs/KORONOS_DISTRIBUTED_RUNTIME.md` | High concurrency, native process orchestration and trusted Chimera node communication |
| `docs/LINUX_DESKTOP_AURORA.md` | Linux desktop profiles, Aurora, availability, startup and recovery |
| `docs/DESKTOP_API.md` | Jakarta REST, desktop selection and launch-plan contract |
| `docs/SOURCE_MIGRATION_MANIFEST.md` | Native-to-Java provenance and non-portable boundaries |
| `docs/superpowers/specs/2026-09-09-linux-desktop-aurora-design.md` | Approved design record and implementation requirements |
| `docs/superpowers/specs/2026-09-09-gnu-cross-platform-services-design.md` | GNU services and licensing design |
| `docs/superpowers/plans/2026-09-09-gnu-cross-platform-services.md` | GNU services and licensing implementation plan |

## Source organization

```text
src/main/java/org/chimera/
├── api/          Jakarta REST resources
├── cognition/    Koronos 128D and knowledge/evidence runtime
├── compat/       GNU, Windows/Linux/Unix/macOS/filesystem/network compatibility catalogs
├── core/         8192-bit register and CPU semantic foundation
├── koronos/      Kernel lifecycle, learning, concurrency and persistence
├── network/      Trusted-node identity, trust policy, signed messages and federation transport
├── runtime/      Linux runtime/service/security catalog and command planning
└── desktop/      Linux desktop/session orchestration
    └── freedesktop/ XDG and desktop-entry models
```

## Current compatibility coverage

`GnuPlatformCatalog` inventories common GNU utilities, network services and Windows delivery providers. `NetworkScannerCatalog` inventories common IPv4/IPv6 discovery and scanning tools including Nmap. `FileSystemCatalog` covers major Windows, Linux, Unix/BSD and Apple filesystems plus SMB/NFS and pseudo-filesystem families. `WindowsCompatibilityCatalog` models Win16, Win32, Win64/PE32+, modern Windows Server and ARM64 targets. `NetworkInteroperabilityCatalog` models Samba, NFS, SMB browsing, LDAP, Kerberos and mDNS roles. `MacNetworkCatalog` models macOS Tahoe 26 networking and Finder integration.

The Library `W2K-ASM.txt` is integrated by architectural provenance metadata through `W2kAsmCompatibilityManifest`; the full supplied corpus is not redistributed into the public repository because it carries Microsoft Confidential/proprietary notices.

## Documentation rules

Documentation must:

1. describe the current implementation rather than an earlier proposal;
2. distinguish implemented, modeled and native-only behavior;
3. preserve the Java API/ABI compatibility intent;
4. avoid claiming that Java replaces native hardware, browsers, desktop compositors or operating-system services;
5. keep Fedora, Ubuntu, Debian and Aurora coverage synchronized with the desktop registry;
6. record meaningful architectural changes in the migration manifest/design record;
7. document persistent learning separately from native hardware semantics;
8. document external runtime versions as refreshable catalog data;
9. keep trust and process execution behind explicit security boundaries;
10. keep build/test instructions aligned with the CI workflow;
11. preserve third-party copyright and license terms when integrating external software.

## Current kernel coverage

`KoronosKernel` performs bounded self-supervised adaptation whenever telemetry is observed. `KernelDataStore` separates kernel learning from persistence, while `H2KernelDataStore` stores observations, learning events and complete 128D model snapshots under `/var/Cimera/Data` by default.

The kernel also exposes virtual-thread I/O execution, bounded CPU worker execution and isolated native process launching. The process boundary accepts structured argument lists rather than shell command strings.

## Current federation coverage

`TrustedChimeraNode`, `ChimeraTrustStore`, `PersistentChimeraTrustStore` and `ChimeraNodeMessage` provide explicit Ed25519 identity, durable public-key trust and signed-message verification. `ChimeraFederationClient` gates outbound traffic on explicit peer trust and requires HTTPS outside localhost/lab endpoints. Payload tampering and stale messages remain rejected by signed-message verification.

## Current Linux runtime coverage

`LinuxRuntimeCatalog` models Python, OpenJDK, Firefox, Chrome, Bash, Zsh, PowerShell, .NET, NGINX, BIND 9 and Postfix, plus Kali security metapackages and a top-tool catalog. `LinuxRuntimeManager` converts that inventory into distro-aware dnf/apt and systemd command plans without executing them. The catalog does not vendor third-party binaries; native package managers remain responsible for installation and signatures.

## Current licensing coverage

The original Java-track source and original documentation are licensed under GPL-3.0-or-later. `docs/LICENSING.md` defines the boundary between project-owned code and external components. External dependencies, operating systems, GNU projects, Microsoft components and Apple components retain their own licensing terms.

## Build verification

```bash
mvn test
mvn package
```

The GitHub Actions workflow targets JDK 25 for reproducible builds. A separate OpenJDK 26 runtime is cataloged as the current Java feature release. CI must not launch a real graphical compositor or privileged system service.
