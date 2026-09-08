# Linux Desktop + Aurora Integration Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a Java 25 desktop-runtime layer to `keygen` covering common Fedora, Ubuntu, Debian and Aurora/Wayland desktop/session profiles with a deterministic startup menu, safe launch plans, freedesktop integration, and Jakarta REST exposure.

**Architecture:** Additive `org.chimera.desktop` APIs model profiles, capabilities, startup entries, session selection, launch plans, and freedesktop metadata. Native desktop execution remains an explicit adapter boundary; CI never launches a compositor.

**Tech Stack:** Java 25, Maven, JUnit 5, Jakarta EE 11/JAX-RS, standard Java `ProcessBuilder`/NIO, JSON-compatible configuration models.

**Spec:** `docs/superpowers/specs/2026-09-09-linux-desktop-aurora-design.md`

## Global Constraints

- Change only `amerhwitat/keygen`; do not modify `amerhwitat/test` or `amerhwitat/ChimeraIIOS`.
- Preserve existing Java 25, Maven, Jakarta EE 11, Chimera ISA, Koronos 128D and cognition APIs.
- Do not embed or reimplement GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, Wayland or X.Org in Java.
- Never construct shell commands by string interpolation; use structured executable/argument lists.
- Never execute an unavailable desktop profile.
- CI tests must not launch a real compositor or display manager.
- Treat 128D/Koronos as research architecture, not an AGI/superintelligence claim.

---

### Task 1: Desktop profile domain model

**Files:**
- Create: `src/main/java/org/chimera/desktop/DesktopProfile.java`
- Create: `src/main/java/org/chimera/desktop/DesktopSessionType.java`
- Create: `src/main/java/org/chimera/desktop/DesktopDistribution.java`
- Create: `src/main/java/org/chimera/desktop/DesktopCapability.java`
- Create: `src/test/java/org/chimera/desktop/DesktopProfileTest.java`

**Interfaces:**
- `DesktopProfile` exposes `id()`, `displayName()`, `distribution()`, `desktopEnvironment()`, `sessionType()`, `commandCandidates()`, `environment()`, `capabilities()`, `defaultProfile()`, and `recoveryProfile()`.
- `DesktopSessionType` contains `WAYLAND`, `X11`, `HEADLESS`.
- `DesktopDistribution` contains `FEDORA`, `UBUNTU`, `DEBIAN`, `AURORA`, `GENERIC`.
- `DesktopCapability` contains `WAYLAND`, `X11`, `PORTALS`, `DBUS`, `GPU`, `AUDIO`.

- [ ] Write tests proving profiles reject blank IDs and retain immutable metadata.
- [ ] Run the targeted JUnit test and observe failure because the model does not exist.
- [ ] Implement the records/enums minimally.
- [ ] Run the targeted test and verify green.
- [ ] Commit `feat: add desktop profile domain model`.

### Task 2: Baseline Fedora/Ubuntu/Debian/Aurora registry

**Files:**
- Create: `src/main/java/org/chimera/desktop/DesktopProfileRegistry.java`
- Create: `src/test/java/org/chimera/desktop/DesktopProfileRegistryTest.java`

**Interfaces:**
- `DesktopProfileRegistry.defaults()` returns the immutable baseline registry.
- `find(String id)` returns `Optional<DesktopProfile>`.
- `profiles()` returns deterministic ordering.

- [ ] Write failing tests requiring Aurora, Fedora GNOME, Ubuntu GNOME, Debian GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, GNOME Flashback, Safe/Minimal and Headless profiles.
- [ ] Verify the tests fail.
- [ ] Implement the registry with structured command candidates such as `gnome-session`, `startplasma-wayland`, `startxfce4`, `cinnamon-session`, `mate-session`, `startlxqt`, and safe/headless JVM modes; these are candidates, not guaranteed installed commands.
- [ ] Verify deterministic ordering and required metadata.
- [ ] Commit `feat: add Linux desktop profile registry`.

### Task 3: Host capability and command detection

**Files:**
- Create: `src/main/java/org/chimera/desktop/DesktopHostEnvironment.java`
- Create: `src/main/java/org/chimera/desktop/DesktopCapabilityDetector.java`
- Create: `src/test/java/org/chimera/desktop/DesktopCapabilityDetectorTest.java`

**Interfaces:**
- `DesktopHostEnvironment` wraps controlled environment variables and a command-availability function.
- `DesktopCapabilityDetector.detect(DesktopHostEnvironment)` returns an immutable capability snapshot.
- Detect `XDG_CURRENT_DESKTOP`, `XDG_SESSION_TYPE`, `WAYLAND_DISPLAY`, `DISPLAY`, `DBUS_SESSION_BUS_ADDRESS`, portal presence and executable availability without launching desktop sessions.

- [ ] Write tests using controlled environment maps and fake command probes.
- [ ] Verify failures before implementation.
- [ ] Implement deterministic detection with no shell invocation.
- [ ] Verify green.
- [ ] Commit `feat: detect Linux desktop capabilities`.

### Task 4: Startup menu and safe launch plans

**Files:**
- Create: `src/main/java/org/chimera/desktop/DesktopStartupEntry.java`
- Create: `src/main/java/org/chimera/desktop/DesktopStartupMenu.java`
- Create: `src/main/java/org/chimera/desktop/DesktopLaunchPlan.java`
- Create: `src/main/java/org/chimera/desktop/DesktopSessionManager.java`
- Create: `src/test/java/org/chimera/desktop/DesktopStartupMenuTest.java`

**Interfaces:**
- `DesktopStartupMenu.entries()` returns deterministic menu entries.
- `DesktopStartupMenu.select(String id)` validates availability and returns a `DesktopLaunchPlan` or structured failure.
- `DesktopLaunchPlan.executable()` and `arguments()` are structured lists suitable for `ProcessBuilder`.
- `DesktopSessionManager.prepare(String id)` never executes a process.

- [ ] Write tests for default selection, unavailable profile rejection, argument-array safety and recovery/headless fallback.
- [ ] Verify failure.
- [ ] Implement minimal selection and launch-plan validation.
- [ ] Verify green.
- [ ] Commit `feat: add safe desktop startup menu`.

### Task 5: Native process adapter boundary

**Files:**
- Create: `src/main/java/org/chimera/desktop/NativeDesktopAdapter.java`
- Create: `src/main/java/org/chimera/desktop/ProcessDesktopAdapter.java`
- Create: `src/test/java/org/chimera/desktop/ProcessDesktopAdapterTest.java`

**Interfaces:**
- `NativeDesktopAdapter.launch(DesktopLaunchPlan)` is the only process execution boundary.
- `ProcessDesktopAdapter` uses `ProcessBuilder(List<String>)`, inherits a sanitized environment, and refuses unknown/unavailable commands.

- [ ] Write tests proving invalid executable/availability is rejected without process creation.
- [ ] Verify failure.
- [ ] Implement the adapter.
- [ ] Verify green.
- [ ] Commit `feat: add native desktop launch adapter`.

### Task 6: Freedesktop/XDG integration

**Files:**
- Create: `src/main/java/org/chimera/desktop/freedesktop/DesktopEntry.java`
- Create: `src/main/java/org/chimera/desktop/freedesktop/DesktopEntryCodec.java`
- Create: `src/main/java/org/chimera/desktop/freedesktop/XdgEnvironment.java`
- Create: `src/test/java/org/chimera/desktop/freedesktop/DesktopEntryCodecTest.java`

**Interfaces:**
- `DesktopEntry` models supported `Type`, `Name`, `GenericName`, `Exec`, `Icon`, `Categories`, `OnlyShowIn`, `NotShowIn`, `DBusActivatable` fields.
- `DesktopEntryCodec.parse(String)` and `encode(DesktopEntry)` provide a deterministic supported-field round trip.
- `XdgEnvironment.from(Map<String,String>)` resolves XDG data/config/runtime directories and desktop/session names.

- [ ] Write round-trip and escaping tests based on freedesktop desktop-entry rules.
- [ ] Verify failure.
- [ ] Implement the codec without executing `Exec` content.
- [ ] Verify green.
- [ ] Commit `feat: add freedesktop desktop entry integration`.

### Task 7: Jakarta EE desktop REST resource

**Files:**
- Create: `src/main/java/org/chimera/api/DesktopResource.java`
- Modify: `src/main/java/org/chimera/api/KoronosApplication.java`
- Create: `src/test/java/org/chimera/desktop/DesktopResourceModelTest.java`

**Interfaces:**
- `/api/desktop/profiles` exposes profile metadata and availability.
- `/api/desktop/current` exposes detected session/capabilities.
- `/api/desktop/select/{id}` returns a launch plan without executing it.

- [ ] Write model-level tests for response data and unavailable-profile errors.
- [ ] Verify failure.
- [ ] Implement REST resource and register it through the existing Jakarta application.
- [ ] Verify green.
- [ ] Commit `feat: expose desktop runtime through Jakarta REST`.

### Task 8: Documentation and integration verification

**Files:**
- Modify: `README.md`
- Modify: `docs/SOURCE_MIGRATION_MANIFEST.md`
- Create: `docs/LINUX_DESKTOP_AURORA.md`

- [ ] Document Fedora, Ubuntu, Debian and Aurora profile behavior and the distinction between profile definition and host availability.
- [ ] Document freedesktop/XDG integration and structured process execution.
- [ ] Document that actual compositors/display managers remain native.
- [ ] Run the full Maven test suite.
- [ ] Verify no changes exist in `amerhwitat/test` or `amerhwitat/ChimeraIIOS`.
- [ ] Commit `docs: document Linux desktop and Aurora runtime`.

### Task 9: GitHub Actions verification

**Files:**
- Existing `.github/workflows/java.yml`

- [ ] Push implementation branch commits.
- [ ] Inspect the workflow associated with the implementation commits.
- [ ] If a build fails, add a failing regression test before fixing the cause.
- [ ] Re-run/verify until the Java 25 Maven build is green.
- [ ] Open a PR from the implementation branch to `main` with the isolation and native-boundary constraints documented.
