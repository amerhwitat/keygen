# Chimera II Linux Desktop + Aurora Runtime

## 1. Purpose

The Java desktop layer is the **session orchestration and compatibility boundary** for Chimera II OS. It provides one deterministic model for selecting a Linux desktop/session while leaving the actual kernel, display manager, compositor, GPU stack, audio stack and desktop applications native.

The design is intentionally distribution-aware without attempting to turn the JVM into a Linux distribution.

## 2. Supported profile families

| ID | Distribution | Desktop/session | Preferred protocol | Recovery |
|---|---|---|---|---|
| `aurora` | Chimera | Aurora | Wayland | No |
| `fedora-gnome` | Fedora | GNOME | Wayland | No |
| `ubuntu-gnome` | Ubuntu | GNOME | Wayland | No |
| `debian-gnome` | Debian | GNOME | Wayland | No |
| `kde-plasma` | Generic Linux | KDE Plasma | Wayland, then X11 | No |
| `xfce` | Generic Linux | Xfce | X11 | No |
| `cinnamon` | Generic Linux | Cinnamon | X11 | No |
| `mate` | Generic Linux | MATE | X11 | No |
| `lxqt` | Generic Linux | LXQt | X11 | No |
| `gnome-flashback` | Generic Linux | GNOME Flashback | X11 | No |
| `safe-minimal` | Generic Linux | Safe / Minimal | host-defined | Yes |
| `headless` | Generic Linux | Headless / Server | none | Yes |

A profile may be registered even when the corresponding desktop is not installed. **Definition and availability are separate states.**

## 3. Runtime availability

`DesktopCapabilityDetector` observes the host environment and executable availability. It can detect:

- Wayland through `WAYLAND_DISPLAY` and `XDG_SESSION_TYPE`;
- X11 through `DISPLAY` and `XDG_SESSION_TYPE`;
- D-Bus through `DBUS_SESSION_BUS_ADDRESS`;
- desktop portals through environment/tool discovery;
- audio through PulseAudio/PipeWire command discovery;
- GPU support through common OpenGL/Vulkan discovery tools.

Launcher checks use `PATH` resolution and direct executable checks. They do not execute arbitrary shell text.

For a normal desktop profile to become selectable, the runtime requires a compatible session capability and at least one valid launcher candidate. This prevents a menu from advertising an installed-looking option that cannot actually start.

## 4. Startup selection

The startup stack is:

```text
DesktopProfileRegistry
        │
        ▼
DesktopCapabilityDetector
        │
        ▼
DesktopStartupMenu
        │
        ├── available profile entries
        └── recovery entries
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

The default policy is:

1. select Aurora when the Aurora profile is configured, compatible and launchable;
2. otherwise use Safe / Minimal;
3. otherwise use Headless / Server.

Explicit selection of an unavailable profile fails with a structured `DesktopSelectionException` instead of attempting a blind process launch.

## 5. Structured launch security

A `DesktopLaunchPlan` contains:

- executable;
- ordered argument list;
- environment additions/overrides.

The runtime uses `ProcessBuilder(List<String>)` at the supplied process boundary. It does not concatenate profile data into a shell command. This prevents shell interpretation from becoming an accidental part of the desktop-selection API.

Applications embedding the Java runtime can replace `ProcessDesktopAdapter` with a policy-controlled adapter, sandbox, native launcher, test double or display-manager integration.

## 6. Aurora boundary

Aurora is a first-class Chimera profile with a Wayland preference. The Java project does **not** claim to implement the Aurora compositor.

A native Aurora compositor/desktop may consume the same profile and launch-plan model. This keeps platform-specific rendering, Wayland protocol handling, GPU access and compositor scheduling outside the Java semantic runtime.

## 7. Fedora, Ubuntu and Debian

The profile registry models the common desktop/session patterns used by Fedora, Ubuntu and Debian. Distribution packages remain host responsibilities. The Java layer should therefore not assume that a command is installed merely because a distribution is selected.

Examples of launcher candidates include:

- GNOME: `gnome-session --session=gnome`;
- Ubuntu GNOME: `gnome-session --session=ubuntu`;
- KDE Plasma: `startplasma-wayland`, then `startplasma-x11`;
- Xfce: `startxfce4`;
- Cinnamon: `cinnamon-session`;
- MATE: `mate-session`;
- LXQt: `startlxqt`;
- GNOME Flashback: `gnome-session --session=gnome-flashback-metacity`.

These are candidates, not promises that every host provides them.

## 8. Freedesktop and XDG

The Java integration provides models/codecs for common desktop-entry metadata and XDG runtime locations. Supported concepts include:

- `Type`, `Name`, `GenericName`;
- `Exec` and `Icon`;
- `Categories`;
- `OnlyShowIn` and `NotShowIn`;
- `DBusActivatable`;
- XDG data/config/cache/runtime directories;
- current desktop and session type.

The desktop-entry model is intended for interoperability with native Linux desktops. It is not a replacement for a native desktop database or session manager.

Autostart remains a native-session responsibility. The Java runtime can represent startup metadata and generate a structured plan but does not supersede the host's native autostart mechanism.

## 9. REST interface

`KoronosApplication` exposes the desktop resource below `/api`:

```text
GET /api/desktop/profiles
GET /api/desktop/current
GET /api/desktop/select/{id}
```

The selection endpoint validates the requested profile and returns its launch plan. It does not execute the desktop process. See `docs/DESKTOP_API.md` for the contract.

## 10. GUI/TUI/web startup menus

`DesktopStartupMenu` is deliberately a model/API rather than a specific graphical toolkit. The same entries can later drive:

- a JavaFX or Swing chooser;
- a native Aurora/Qt chooser;
- a text console menu;
- a display-manager integration;
- a web administration UI.

This avoids coupling the core Java runtime to one rendering stack.

## 11. Failure and recovery

The runtime is designed to fail safely:

- missing launcher → profile unavailable;
- unsupported session protocol → profile unavailable;
- invalid profile ID → structured selection error;
- default desktop unavailable → Safe / Minimal;
- Safe / Minimal unavailable → Headless / Server;
- real compositor failures → reported to the native process boundary.

CI tests do not launch real compositors.

## 12. Native responsibilities

The following remain native/platform responsibilities:

- Linux kernel and system calls;
- systemd and display managers;
- Wayland/X11 compositor execution;
- GPU drivers and hardware acceleration;
- PipeWire/PulseAudio execution;
- desktop applications;
- real bootloader/firmware execution;
- native DMA, MMIO, interrupt and driver behavior.

The Java runtime provides explicit abstractions for these boundaries rather than silently replacing them.
