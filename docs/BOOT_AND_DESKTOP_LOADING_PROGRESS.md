# Chimera II Boot and Desktop Loading Progress

## Spitfire bootloader

The Spitfire boot path now has a shared `SpitfireBootProgress` model. Boot stages can be reported independently while the model computes a bounded 0–100% completion value. The model is rendering-neutral so firmware, serial-console, terminal, or graphical front ends can consume identical state.

Recommended stages:

1. firmware / platform discovery
2. memory and register initialization
3. ISA/runtime initialization
4. security and trust initialization
5. storage/filesystem discovery
6. Koronos kernel initialization
7. network/runtime services
8. desktop session preparation

## Desktop loading pass

`DesktopLoadingProgress` provides a bounded percentage and a circular sweep angle from 0–360 degrees. A desktop UI can use this state for a Windows-like circular loading indicator without coupling the desktop selector to a particular GUI toolkit.

The existing desktop architecture remains unchanged: `DesktopStartupMenu` selects an available profile and `DesktopSessionManager`/native adapters remain responsible for launching the native desktop. The progress state is presentation-only and does not replace GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, Aurora, Wayland, or X11 components.

## UX behavior

- Spitfire: horizontal staged progress with the current stage and percentage.
- Desktop pass: centered circular loading indicator with smooth animation; percentage may be shown as secondary text.
- Recovery/headless mode: progress can fall back to a textual/terminal representation.
- Failed stage: freeze the progress value and expose the failing stage rather than falsely reporting completion.

## Compatibility

The progress models use only Java standard-library types and remain independent of operating-system-specific UI libraries. This keeps the Java 25 runtime portable across Linux, Windows, macOS, and server/headless deployments.
