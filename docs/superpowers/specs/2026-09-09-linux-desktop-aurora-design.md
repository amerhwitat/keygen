# Chimera II Linux Desktop + Aurora Integration Design

## Status

**Approved and implemented — 2026-09-09.**

Implementation is isolated to `amerhwitat/keygen`. The repositories `amerhwitat/test` and `amerhwitat/ChimeraIIOS` are not modified by this Java integration track.

This document is retained as the design record; implementation details are maintained in the operational desktop guide and API documentation.

## Goal

Provide a Java 25 desktop-runtime layer that models common Linux desktop/session choices across Fedora, Ubuntu and Debian, integrates Chimera Aurora as a first-class Wayland-oriented flavor, and exposes a deterministic startup/session selection model.

## Architecture

```text
DesktopProfileRegistry
          │
          ▼
DesktopCapabilityDetector
          │
          ├── session capabilities
          ├── launcher availability
          └── host environment
          │
          ▼
DesktopStartupMenu
          │
          ▼
DesktopSessionManager
          │
          ▼
DesktopLaunchPlan
          │
          ▼
ProcessDesktopAdapter / native adapter
```

The Java layer is an orchestration/compatibility model. It does not replace the Linux kernel, display manager, compositor, GPU stack or desktop applications.

## Profile model

The registry is data-driven and currently covers:

- Chimera Aurora / Wayland;
- Fedora GNOME;
- Ubuntu GNOME;
- Debian GNOME;
- KDE Plasma with Wayland/X11 candidates;
- Xfce;
- Cinnamon;
- MATE;
- LXQt;
- GNOME Flashback;
- Safe / Minimal recovery;
- Headless / Server recovery.

A profile has stable identity and metadata independent of whether it is installed on a particular host.

## Availability model

Availability is calculated at runtime. A normal desktop profile requires:

1. a compatible session capability, and
2. at least one installed launcher candidate.

The detector uses environment inspection and executable resolution rather than executing arbitrary commands. This makes the startup menu safe to evaluate before a process is launched.

## Startup policy

The default policy is deterministic:

```text
Aurora/default available?
        │ yes
        ▼
     Aurora
        │ no
        ▼
Safe / Minimal available?
        │ yes
        ▼
 Safe / Minimal
        │ no
        ▼
 Headless / Server
```

Explicit selection of an unavailable profile returns a structured selection error.

## Structured launch

`DesktopLaunchPlan` contains an executable, argument list and environment map. The supplied `ProcessDesktopAdapter` uses `ProcessBuilder(List<String>)`.

No profile value is interpolated into a shell command. Applications may supply a policy-controlled adapter, test double, display-manager bridge or native Aurora launcher.

## Freedesktop/XDG

The implementation models common desktop-entry and XDG concepts required for interoperability:

- `.desktop` type/name/generic-name;
- executable/icon metadata;
- categories;
- desktop visibility constraints;
- D-Bus activation metadata;
- XDG data/config/cache/runtime paths;
- current desktop/session type;
- Wayland/X11 and common desktop capability observations.

Native desktop autostart and session management remain host responsibilities.

## REST boundary

The Jakarta EE application exposes:

- `GET /api/desktop/profiles`
- `GET /api/desktop/current`
- `GET /api/desktop/select/{id}`

The selection resource validates and returns a launch plan but does not execute it. This keeps web/API control separate from native process execution.

## Compatibility

Existing Java 25, Maven, Jakarta EE 11, Chimera CPU/ISA and Koronos 128D APIs are additive and remain compatible with the Java implementation track. The desktop package does not redefine the native ABI.

## Testing requirements

The implemented test strategy covers:

- stable profile metadata;
- Fedora/Ubuntu/Debian/Aurora discovery;
- launcher availability;
- deterministic startup ordering;
- unavailable-profile rejection;
- structured launch arguments;
- controlled Wayland/X11/headless detection;
- desktop-entry round trips;
- deterministic Koronos 128D initialization/output;
- Jakarta resource behavior without starting a compositor.

CI must never launch a real desktop compositor.

## Non-goals

- reimplementing GNOME, KDE Plasma, Xfce, Cinnamon, MATE or LXQt in Java;
- replacing Linux/systemd/display managers;
- bundling distribution packages;
- claiming Aurora is a complete standalone Linux distribution;
- claiming the Java semantic CPU is hardware-equivalent without conformance evidence.
