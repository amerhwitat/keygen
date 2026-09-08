# Chimera II Java Documentation Index

**Last synchronized:** 2026-09-09

This directory documents the Java 25 implementation track of Chimera II OS. Documentation is organized so that architecture, migration provenance, Linux desktop integration, API behavior and persistent self-learning can be understood independently.

## Primary documents

| Document | Purpose |
|---|---|
| `README.md` | Project overview, scope, build, architecture and compatibility boundaries |
| `docs/JAVA_RUNTIME_ARCHITECTURE.md` | End-to-end Java architecture and subsystem relationships |
| `docs/KERNEL_LEARNING_DATABASE.md` | Self-learning kernel loop, H2 persistence and restart behavior |
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
├── koronos/      Kernel lifecycle, learning and persistent data store
└── desktop/      Linux desktop/session orchestration
    └── freedesktop/ XDG and desktop-entry models
```

## Documentation rules

Documentation must:

1. describe the current implementation rather than an earlier proposal;
2. distinguish implemented, modeled and native-only behavior;
3. preserve the Java API/ABI compatibility intent;
4. avoid claiming that Java replaces native hardware or desktop infrastructure;
5. keep Fedora, Ubuntu, Debian and Aurora coverage synchronized with the desktop registry;
6. record meaningful architectural changes in the migration manifest/design record;
7. document persistent learning separately from native hardware semantics;
8. keep build/test instructions aligned with the CI workflow.

## Current kernel learning coverage

`KoronosKernel` performs bounded self-supervised adaptation whenever telemetry is observed. `KernelDataStore` separates kernel learning from persistence, while `H2KernelDataStore` stores observations, learning events and complete 128D model snapshots.

The production default is `/var/Cimera/Data/kernel.mv.db`. Tests inject temporary directories so CI remains isolated from system paths.

## Current desktop coverage

The Java desktop registry currently models Aurora, Fedora GNOME, Ubuntu GNOME, Debian GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, GNOME Flashback, Safe/Minimal and Headless/Server.

The menu distinguishes profile definition from host availability and validates launcher availability before normal desktop selection. Recovery profiles remain available as the deterministic fallback path.

## Current safety boundary

Desktop profile selection produces structured launch plans. The supplied process adapter uses an executable plus argument list rather than shell concatenation. REST selection does not directly execute the selected process.

Self-learning cannot directly rewrite kernel code, ISA/ABI definitions, privilege policy, executable paths or native binaries.

## Build verification

```bash
mvn test
mvn package
```

The GitHub Actions workflow targets JDK 25. CI must not launch a real graphical compositor.

## Future documentation additions

When corresponding implementation is introduced, add dedicated documents for:

- complete ISA/bit-mask tables and generated conformance vectors;
- native adapter/FFM integration;
- installer/rootfs packaging;
- graphical Aurora startup UI;
- deployment profiles for application servers and native Linux launchers.
