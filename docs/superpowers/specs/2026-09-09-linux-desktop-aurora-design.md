# Chimera II Linux Desktop + Aurora Integration Design

## Status

Approved by the user on 2026-09-09. Implementation remains isolated to `amerhwitat/keygen`; `amerhwitat/test` and `amerhwitat/ChimeraIIOS` are not modified.

## Goal

Add a Java 25 desktop-runtime layer that models the common desktop/session capabilities used across Fedora, Ubuntu, and Debian, integrates the existing Aurora/Wayland concept as a first-class Chimera flavor, and exposes a data-driven desktop startup/session selection menu.

## Architecture

The desktop layer is an orchestration and compatibility model, not a claim that Java replaces native Linux desktop stacks. Java discovers and describes host capabilities, selects a session profile, prepares environment/launcher metadata, and delegates actual compositor/display-manager execution to native commands or optional adapters.

The implementation uses a stable core API:

```text
DesktopRuntime
 ├── DesktopProfileRegistry
 ├── DesktopCapabilityDetector
 ├── DesktopStartupMenu
 ├── DesktopSessionManager
 ├── FreedesktopIntegration
 └── NativeDesktopAdapter
       ├── Wayland adapter
       ├── X11 compatibility adapter
       └── command/process adapter
```

Profiles are data-driven. The baseline registry contains Aurora, Fedora GNOME, Ubuntu GNOME, Debian GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, GNOME Flashback, Safe/Minimal, and Headless/Server. A profile records desktop name, distribution affinity, display protocol preference, startup command candidates, environment variables, capabilities, and whether it is a native host session or a compatibility/model entry.

## Linux desktop coverage

The project will cover common cross-distribution desktop concepts rather than embedding distribution packages into the JVM:

- Fedora: GNOME/Wayland baseline, with KDE Plasma and common Xfce/Cinnamon/MATE/LXQt profiles where installed.
- Ubuntu: GNOME/Wayland baseline, with common KDE Plasma, Xfce, Cinnamon, MATE, and LXQt profiles where installed.
- Debian: GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, and GNOME Flashback profiles where installed.
- Aurora: dedicated Chimera Aurora/Wayland profile, preserving the existing Aurora integration concept while using Java as the session orchestration boundary.

The registry must distinguish **profile availability** from **profile definition**. A profile can be known to the system but unavailable on a particular host.

## Startup menu

The menu is a model/API first, allowing a GUI, TUI, web UI, or native launcher to render it later. Each entry has:

- stable ID
- display name
- distribution
- desktop environment
- session type (`wayland`, `x11`, `headless`)
- availability state
- default flag
- launch command candidates
- safety/recovery flag

Selection must validate that the profile exists and is available, then return a launch plan. The Java runtime does not silently execute arbitrary strings from configuration; commands are represented as structured executable + argument lists and validated against the detected host.

## Freedesktop/Aurora integration

Add Java models for XDG/freedesktop concepts needed by a desktop shell:

- `.desktop` application entries
- XDG desktop/session environment
- MIME/application association metadata
- desktop launch categories
- Wayland/X11 session detection
- optional portal capability detection

Aurora remains a flavor/profile and does not become a fork of every desktop environment.

## Security and failure handling

- Never execute an unavailable profile.
- Never interpolate untrusted strings into shell commands.
- Use `ProcessBuilder` argument arrays rather than shell concatenation.
- Treat environment detection as advisory and fail closed for unknown launchers.
- Provide Safe/Minimal and Headless fallback profiles.
- Return structured errors for unsupported sessions rather than crashing the kernel.

## Testing

JUnit tests will verify:

1. all baseline profiles have stable IDs and required metadata;
2. Fedora, Ubuntu, Debian, and Aurora profiles are discoverable;
3. availability detection distinguishes installed and missing commands;
4. startup menu ordering and default selection are deterministic;
5. launch plans use structured arguments and reject unavailable profiles;
6. Wayland/X11/headless detection behaves correctly for controlled environments;
7. `.desktop` parsing/generation round trips supported fields;
8. Jakarta REST can expose profile inventory and current selection without starting a desktop process.

No test may launch a real compositor in CI.

## Compatibility

The existing Java 25, Maven, Jakarta EE 11, Chimera CPU/ISA, Koronos 128D, and cognition APIs remain unchanged. The desktop runtime is additive. Native source-of-record repositories are not modified.

## Non-goals

- Reimplementing GNOME, KDE Plasma, Xfce, Cinnamon, MATE, or LXQt in Java.
- Replacing the Linux kernel, systemd, display managers, Wayland compositors, GPU drivers, or X.Org.
- Bundling distribution packages into the Java repository.
- Claiming Aurora is a complete standalone Linux distribution unless a separate native image/rootfs project is created.
