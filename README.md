# Chimera II OS — Java 25 / Koronos 128D

This repository is the **isolated Java implementation track** for Chimera II OS. It provides a semantic JVM model of the Chimera processor/kernel concepts, the Koronos 128D research runtime, and a Linux desktop/session orchestration layer.

> **Scope boundary:** this repository does not modify `amerhwitat/ChimeraIIOS` or `amerhwitat/test`. Native Chimera remains the source-of-record for hardware, boot, ABI, driver, compositor, and platform-specific behavior.

## Project status

- Java 25 / Maven baseline.
- Jakarta EE 11 integration.
- 8192-bit register model represented as 128 × 64-bit lanes.
- Canonical 16-byte Chimera instruction representation.
- Core R8192 ALU semantic model.
- 128D vector/state representation.
- Deterministic Koronos recurrent-learning research prototype.
- Knowledge/evidence bus and REST service boundary.
- Linux desktop startup/session model for Aurora, Fedora, Ubuntu, Debian and common desktop environments.
- Freedesktop/XDG metadata model.
- Launcher-aware availability probing and safe startup fallback.
- Explicit native-source migration/provenance manifest.
- CI verification with JDK 25 and Maven tests.

## Architecture

```text
Native Chimera source-of-record
            │
            │ semantic migration / conformance
            ▼
┌─────────────────────────────────────────────┐
│              Java 25 Runtime                │
├─────────────────────────────────────────────┤
│ Chimera CPU / ISA │ Kernel / services       │
│ 8192-bit register │ Jakarta EE REST         │
│ 16-byte instruction│ Virtual-thread runtime │
├─────────────────────────────────────────────┤
│              Koronos 128D                   │
│ vector state → recurrent state → evidence   │
│ bounded adaptation → observable output      │
├─────────────────────────────────────────────┤
│          Linux Desktop Runtime              │
│ profiles → capability probe → launcher      │
│ validation → structured launch plan         │
│ Aurora / Wayland / X11 / recovery           │
└─────────────────────────────────────────────┘
            │
            ▼
 Native Linux compositor / session / driver
```

The Java layer deliberately models native boundaries rather than pretending that a JVM can execute x86 boot code, replace a kernel, or reimplement every desktop compositor.

## Linux desktop and Aurora

The `org.chimera.desktop` package provides a data-driven startup menu covering:

- **Chimera Aurora / Wayland**
- **Fedora GNOME**
- **Ubuntu GNOME**
- **Debian GNOME**
- **KDE Plasma** (Wayland/X11 candidates)
- **Xfce**
- **Cinnamon**
- **MATE**
- **LXQt**
- **GNOME Flashback**
- **Safe / Minimal** recovery
- **Headless / Server** recovery

The registry is distribution-aware but does not bundle Fedora, Debian, Ubuntu, Aurora, GNOME, KDE or other desktop packages. A profile describes what the runtime knows how to launch; **availability is determined at runtime**.

Startup selection is fail-safe:

1. prefer the configured Aurora/default profile when its session capabilities and launcher are available;
2. otherwise select Safe / Minimal;
3. otherwise select Headless / Server.

Launcher candidates are validated without invoking a shell. Actual execution is delegated through a structured `ProcessBuilder` boundary or an application-supplied native adapter.

### Desktop API

Jakarta REST exposes:

- `GET /api/desktop/profiles` — profile inventory and availability metadata.
- `GET /api/desktop/current` — current/default desktop selection state.
- `GET /api/desktop/select/{id}` — validates a profile and returns a structured launch plan; it **does not execute** the process.

The API is intentionally separated from process execution so a graphical, TUI, web, display-manager or native Aurora frontend can render the same startup menu.

## Freedesktop/XDG integration

The Java desktop layer models common freedesktop concepts needed for interoperable desktop integration:

- desktop-entry fields;
- application categories;
- `OnlyShowIn` / `NotShowIn` constraints;
- D-Bus activation metadata;
- XDG data/config/cache/runtime locations;
- current desktop/session type;
- Wayland/X11/D-Bus/portal/audio/GPU capability observations.

It does not replace the native desktop's session manager, display manager, portal implementation or compositor.

## Chimera CPU / ISA

The Java processor model follows the documented semantic representation:

- 8192-bit general-purpose register model;
- 128 lanes × 64 bits per register;
- 16-byte canonical instruction packet;
- opcode and register fields represented explicitly;
- arithmetic, logical, shifts/rotates, multiply, move, divide/remainder and comparison semantics implemented in the current Java subset;
- privilege-sensitive operations represented by explicit Java policy/service boundaries.

The complete native ISA remains the authoritative source. Where a mechanical translation would change ABI or hardware semantics, the Java implementation uses a semantic model and records the mapping in `docs/SOURCE_MIGRATION_MANIFEST.md`.

## Koronos 128D

Koronos is a bounded research runtime around a 128-dimensional state representation. The current recurrent implementation is deterministic under a supplied seed and supports bounded self-supervised adaptation.

The 128D representation is an architectural abstraction, not a claim that physical reality has exactly 128 fundamental dimensions. The project also makes no claim of AGI or autonomous superintelligence.

## Build and test

Requirements:

- JDK 25
- Maven 3.9+ recommended

```bash
mvn test
mvn package
```

CI uses JDK 25 and verifies the Maven project without launching a real desktop compositor.

## Running in a Linux host

The Java runtime can be embedded in a native Linux launcher, service, application server or future Aurora shell. A normal desktop deployment should provide the desired compositor/session packages through the host distribution.

For example, the host may provide `gnome-session`, `startplasma-wayland`, `startplasma-x11`, `startxfce4`, `cinnamon-session`, `mate-session`, or `startlxqt`. The registry treats these as candidates and checks what is actually installed before presenting a profile as selectable.

The Java process itself is not a replacement for the Linux display manager or compositor.

## Documentation map

- [`docs/DOCUMENTATION_INDEX.md`](docs/DOCUMENTATION_INDEX.md) — documentation map and maintenance rules.
- [`docs/LINUX_DESKTOP_AURORA.md`](docs/LINUX_DESKTOP_AURORA.md) — desktop architecture, profiles, startup, recovery and XDG integration.
- [`docs/JAVA_RUNTIME_ARCHITECTURE.md`](docs/JAVA_RUNTIME_ARCHITECTURE.md) — CPU, kernel, 128D, service and desktop architecture.
- [`docs/DESKTOP_API.md`](docs/DESKTOP_API.md) — Jakarta REST and launch-plan contract.
- [`docs/SOURCE_MIGRATION_MANIFEST.md`](docs/SOURCE_MIGRATION_MANIFEST.md) — native-to-Java provenance and portability boundaries.
- [`docs/superpowers/specs/2026-09-09-linux-desktop-aurora-design.md`](docs/superpowers/specs/2026-09-09-linux-desktop-aurora-design.md) — approved desktop integration design.

## Compatibility and non-goals

This project is additive and preserves the existing Java ABI/API wherever possible. It does not:

- replace the Linux kernel;
- replace systemd or a display manager;
- reimplement GNOME, KDE Plasma, Xfce, Cinnamon, MATE or LXQt in Java;
- execute native x86 boot sectors inside the JVM;
- emulate real hardware MMIO/DMA/GPU behavior unless an explicit simulator/adapter exists;
- silently claim complete parity with the native Chimera implementation;
- bundle an entire Linux distribution or root filesystem.

For native behavior, use the native Chimera repository and the documented Java adapter boundaries.
