# Chimera II Java Documentation Index

**Last synchronized:** 2026-09-09

This directory documents the Java 25 implementation track of Chimera II OS. Documentation is organized so that architecture, migration provenance, Linux desktop integration, persistent self-learning, Linux runtime integration and trusted-node federation can be understood independently.

## Primary documents

| Document | Purpose |
|---|---|
| `README.md` | Project overview, scope, build, architecture and compatibility boundaries |
| `docs/JAVA_RUNTIME_ARCHITECTURE.md` | End-to-end Java architecture and subsystem relationships |
| `docs/KERNEL_LEARNING_DATABASE.md` | Self-learning kernel loop, H2 persistence and restart behavior |
| `docs/LINUX_RUNTIME_STACK.md` | Python, Java, browsers, shells, PowerShell, .NET, HTTP, DNS, mail and Kali integration |
| `docs/LINUX_RUNTIME_INSTALLATION.md` | Distro-aware package/service planning and installation security boundaries |
| `docs/KORONOS_DISTRIBUTED_RUNTIME.md` | High concurrency, native process orchestration and trusted Chimera node communication |
| `docs/LINUX_DESKTOP_AURORA.md` | Linux desktop profiles, Aurora, availability, startup and recovery |
| `docs/DESKTOP_API.md` | Jakarta REST, desktop selection and launch-plan contract |
| `docs/SOURCE_MIGRATION_MANIFEST.md` | Native-to-Java provenance and non-portable boundaries |
| `docs/superpowers/specs/2026-09-09-linux-desktop-aurora-design.md` | Approved design record and implementation requirements |

## Source organization

```text
src/main/java/org/chimera/
├── api/          Jakarta REST resources
├── cognition/    Koronos 128D and knowledge/evidence runtime
├── core/         8192-bit register and CPU semantic foundation
├── koronos/      Kernel lifecycle, learning, concurrency and persistence
├── network/      Trusted-node identity, trust policy, signed messages and federation transport
├── runtime/      Linux runtime/service/security catalog and command planning
└── desktop/      Linux desktop/session orchestration
    └── freedesktop/ XDG and desktop-entry models
```

## Documentation rules

Documentation must:

1. describe the current implementation rather than an earlier proposal;
2. distinguish implemented, modeled and native-only behavior;
3. preserve the Java API/ABI compatibility intent;
4. avoid claiming that Java replaces native hardware, browsers, desktop compositors or Linux services;
5. keep Fedora, Ubuntu, Debian and Aurora coverage synchronized with the desktop registry;
6. record meaningful architectural changes in the migration manifest/design record;
7. document persistent learning separately from native hardware semantics;
8. document external runtime versions as refreshable catalog data;
9. keep trust and process execution behind explicit security boundaries;
10. keep build/test instructions aligned with the CI workflow.

## Current kernel coverage

`KoronosKernel` performs bounded self-supervised adaptation whenever telemetry is observed. `KernelDataStore` separates kernel learning from persistence, while `H2KernelDataStore` stores observations, learning events and complete 128D model snapshots under `/var/Cimera/Data` by default.

The kernel now also exposes virtual-thread I/O execution, bounded CPU worker execution and isolated native process launching. The process boundary accepts structured argument lists rather than shell command strings.

## Current federation coverage

`TrustedChimeraNode`, `ChimeraTrustStore`, `PersistentChimeraTrustStore` and `ChimeraNodeMessage` provide explicit Ed25519 identity, durable public-key trust and signed-message verification. `ChimeraFederationClient` gates outbound traffic on explicit peer trust and requires HTTPS outside localhost/lab endpoints. Payload tampering and stale messages remain rejected by signed-message verification.

## Current Linux runtime coverage

`LinuxRuntimeCatalog` models Python, OpenJDK, Firefox, Chrome, Bash, Zsh, PowerShell, .NET, NGINX, BIND 9 and Postfix, plus Kali security metapackages and a top-tool catalog. `LinuxRuntimeManager` converts that inventory into distro-aware dnf/apt and systemd command plans without executing them. The catalog does not vendor third-party binaries; native package managers remain responsible for installation and signatures.

## Current desktop coverage

The Java desktop registry currently models Aurora, Fedora GNOME, Ubuntu GNOME, Debian GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, GNOME Flashback, Safe/Minimal and Headless/Server.

The menu distinguishes profile definition from host availability and validates launcher availability before normal desktop selection. Recovery profiles remain available as the deterministic fallback path.

## Build verification

```bash
mvn test
mvn package
```

The GitHub Actions workflow targets JDK 25 for reproducible builds. A separate OpenJDK 26 runtime is cataloged as the current Java feature release. CI must not launch a real graphical compositor or privileged system service.
