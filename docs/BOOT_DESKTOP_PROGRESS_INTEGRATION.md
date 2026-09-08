# Chimera II Boot/Desktop Progress Integration

The Spitfire bootloader and desktop loading pass now share a small orchestration layer: `BootDesktopProgressCoordinator`.

## Startup flow

```text
SpitfireBootProgress
       |
       | complete all boot stages
       v
BootDesktopProgressCoordinator
       |
       +--> DesktopLoadingProgress
                |
                +--> 0..100% / 0..360° circular sweep
```

### Boot phase

`completeBootStage(name)` records one of the eight standard Spitfire stages and updates the bounded boot percentage. Repeated stage completion is idempotent because the underlying model tracks completed stage names.

### Desktop phase

Desktop progress cannot advance until all Spitfire stages are complete. Once boot completes, the coordinator starts the desktop loading pass at 0%. `setDesktopPercent()` updates the bounded percentage and exposes a 0–360 degree sweep for a Windows-like circular indicator.

### Completion

The startup pass is complete only when both conditions hold:

- Spitfire boot progress is 100%.
- Desktop loading progress is 100%.

This prevents a UI from reporting a completed desktop startup while the kernel/runtime boot is still incomplete.

## Integration boundary

The coordinator is deliberately UI-toolkit-neutral. `DesktopStartupMenu`, `DesktopSessionManager`, and native desktop adapters continue to select and launch the actual native desktop. A future GTK/Qt/JavaFX/terminal frontend can render the same progress state without changing the boot or desktop selection APIs.

## Failure behavior

A failed boot stage should not be marked complete. The caller can retain the current percentage and surface the failing stage through its chosen UI or recovery console. The coordinator does not execute privileged boot operations itself.
