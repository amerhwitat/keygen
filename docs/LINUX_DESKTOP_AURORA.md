# Chimera II Linux Desktop + Aurora Runtime

The Java track models common Linux desktop/session choices while leaving the actual Linux compositor, display manager, GPU stack and kernel native.

## Profiles

The baseline registry includes:

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

A profile can exist even when its launcher is not installed. Runtime availability is detected separately from the profile definition.

## Startup model

`DesktopStartupMenu` produces deterministic entries. `DesktopSessionManager` converts a selected profile into a `DesktopLaunchPlan`. The plan contains an executable, an argument list and environment map, so the runtime does not concatenate untrusted shell strings.

`ProcessDesktopAdapter` is the only supplied process-launch boundary. Applications embedding the runtime can replace it with a policy-controlled adapter or a test double.

## Wayland/X11 detection

The capability detector uses `XDG_SESSION_TYPE`, `WAYLAND_DISPLAY`, `DISPLAY`, D-Bus environment information and command probes. Detection is advisory and does not start a compositor.

## Freedesktop

The runtime provides a small Java model/codec for common `.desktop` fields and an XDG environment model. It follows the freedesktop desktop-entry model used by interoperable Linux desktop environments. The normative specification is the freedesktop Desktop Entry Specification 1.5.

Autostart remains a freedesktop/native-session concern; the Java runtime can model its metadata but does not replace the desktop's native startup manager.

## Aurora

Aurora is represented as a first-class Chimera flavor with a Wayland preference. This does not claim that the JVM implements the Aurora compositor. It provides the session-selection and integration boundary needed for a later native compositor or launcher.

## Enterprise API

Jakarta REST exposes:

- `/api/desktop/profiles`
- `/api/desktop/current`
- `/api/desktop/select/{id}`

The selection endpoint creates a launch plan; it does not launch the desktop process.
