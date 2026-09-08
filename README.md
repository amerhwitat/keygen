# Chimera II OS — Java 25 / Koronos 128D

This repository is the **isolated Java implementation track** for Chimera II OS. It does not modify `amerhwitat/ChimeraIIOS` or `amerhwitat/test`.

## Targets

- Java SE / JDK 25 baseline.
- Maven build.
- Jakarta EE 11 integration profile.
- 8192-bit Chimera register model represented by 128 x 64-bit lanes.
- Canonical 16-byte instruction packet decoder.
- Java implementation of the core R8192 ALU semantics.
- 128-dimensional state/tensor representation.
- Recurrent learning prototype for the Koronos cognitive runtime.
- REST/Jakarta EE boundary for kernel telemetry and inference.
- Linux desktop runtime covering Fedora, Ubuntu, Debian and Aurora/Wayland profiles.
- Freedesktop/XDG desktop-entry integration and structured startup launch plans.
- Installed-launcher probing before a desktop profile is exposed as selectable.
- Safe/minimal and headless recovery fallback when the preferred desktop cannot launch.
- Explicit provenance/migration ledger for native source files.

## Linux desktop runtime

The additive `org.chimera.desktop` layer provides a data-driven startup menu for:

- Chimera Aurora / Wayland
- Fedora GNOME
- Ubuntu GNOME
- Debian GNOME
- KDE Plasma
- Xfce
- Cinnamon
- MATE
- LXQt
- GNOME Flashback
- Safe / Minimal
- Headless / Server

Profile definition and host availability are separate. The runtime detects Wayland/X11/session capabilities and probes the executable candidates for each profile before marking the profile available. Multiple candidates are supported, so KDE Plasma can prefer Wayland and fall back to X11 when the corresponding launcher is installed.

`DesktopSessionManager.defaultPlan()` prefers the Aurora default only when it is actually selectable, then falls back to Safe / Minimal and finally Headless / Server. This prevents the startup path from selecting a profile whose launcher is absent.

Actual GNOME/KDE/Xfce/Cinnamon/MATE/LXQt sessions remain native Linux software; Java does not reimplement their compositors. The Java layer owns profile selection, capability/launcher validation, structured process plans, and integration metadata.

Jakarta REST exposes `/api/desktop/profiles`, `/api/desktop/current`, and `/api/desktop/select/{id}`. The selection endpoint creates a launch plan and does not execute it.

The desktop integration follows the freedesktop desktop-entry model used for interoperable application launch metadata.

## Important scope

A mechanical C/C++/ASM-to-Java translation cannot preserve hardware/ABI behavior one-to-one. Java therefore implements the **semantic model** of the ISA/kernel while retaining the native source as the source-of-record. Native-only concerns such as boot ROM execution, MMIO, CPU instructions, DMA hardware, and kernel privilege transitions are represented by Java service interfaces/simulators.

The 128D/RNN component is a research prototype, not a claim of artificial general intelligence or autonomous superintelligence. It provides a trainable recurrent model and self-improvement hooks under explicit policy/telemetry control.

## Build

```bash
mvn test
mvn package
```

## Java version

JDK 25 is the primary target. OpenJDK publishes production-ready JDK 25 binaries; Java SE 25 defines the current Java 25 API/specification surface.

## Deep learning

The project is engine-neutral at the core and can integrate Deep Java Library (DJL). DJL provides NDArray/neural-network/training APIs and can switch among supported engines.

## Enterprise

Jakarta EE 11 is the supported enterprise profile. Jakarta EE 12 is tracked as a future/development target and is not required by the baseline build.
