# Chimera II Java Runtime Architecture

## 1. Overview

The Java track is a semantic and orchestration implementation of selected Chimera II concepts. It is designed to run on JDK 25, retain explicit native boundaries, and provide deterministic tests around CPU, cognition, services and desktop selection.

## 2. Layers

```text
┌───────────────────────────────────────────────┐
│ Applications / GUI / TUI / REST clients       │
├───────────────────────────────────────────────┤
│ Jakarta REST + service resources              │
├───────────────────────────────────────────────┤
│ Koronos kernel / service lifecycle             │
├───────────────────────┬───────────────────────┤
│ Chimera CPU / ISA     │ Koronos 128D cognition │
│ R8192 / instructions  │ vectors / RNN / bus    │
├───────────────────────┴───────────────────────┤
│ Linux desktop orchestration                   │
│ profile / capability / launcher / XDG         │
├───────────────────────────────────────────────┤
│ Native boundary adapters                      │
│ ProcessBuilder / optional FFM / JNI           │
└───────────────────────────────────────────────┘
                     │
                     ▼
              Native Linux platform
```

## 3. CPU semantic model

`Register8192` represents an 8192-bit register as 128 64-bit lanes. The instruction model preserves the canonical 16-byte field layout used by the migrated architecture.

The Java CPU is intentionally described as a **semantic implementation**. It is not assumed to provide physical 8192-bit hardware registers or native MMIO.

## 4. Kernel and services

`KoronosKernel` provides the Java-side lifecycle and service orchestration. Virtual threads are used through stable Java APIs. Hardware-specific responsibilities are expressed through service interfaces and simulators.

## 5. 128D cognition

The cognition layer consists of:

- `Vector128D` — fixed 128-dimensional state representation;
- `KoronosRnn128` — deterministic recurrent research model;
- `KnowledgeBus` — evidence/knowledge transport boundary.

The model supports bounded adaptation and observable outputs. It is not represented as AGI, consciousness or autonomous superintelligence.

## 6. Desktop runtime

The `org.chimera.desktop` package provides:

- `DesktopProfileRegistry` — canonical profile inventory;
- `DesktopCapabilityDetector` — host/session/launcher observations;
- `DesktopStartupMenu` — deterministic selectable menu;
- `DesktopSessionManager` — selection and fallback policy;
- `DesktopLaunchPlan` — immutable structured process description;
- `ProcessDesktopAdapter` — native process-launch boundary;
- `DesktopEntry` / `DesktopEntryCodec` — freedesktop metadata model;
- `XdgEnvironment` — XDG environment model.

## 7. Desktop state machine

```text
profile definition
       │
       ▼
host capability detection
       │
       ▼
launcher availability
       │
   ┌───┴────┐
   │        │
available unavailable
   │        │
   ▼        ▼
selectable  recovery
   │
   ▼
launch plan
   │
   ▼
native adapter
```

No shell command is constructed from untrusted profile data. The launcher is represented structurally and validated before selection.

## 8. API boundary

Jakarta REST is rooted at `/api`. Desktop resources expose inventory, current state and selection-plan generation. Selection is intentionally separated from execution so API clients cannot accidentally become an unrestricted process launcher.

## 9. Concurrency

The Java runtime favors stable JDK 25 concurrency primitives. Virtual threads are appropriate for service-style tasks with blocking I/O. Shared state in the cognition/runtime layers should remain deterministic where tests require reproducibility.

## 10. Error handling

Errors should remain explicit at subsystem boundaries:

- invalid instruction → instruction/CPU validation error;
- unavailable desktop → desktop selection error;
- unsupported native operation → adapter/service boundary error;
- failed native process → process adapter failure;
- malformed desktop entry → codec validation error.

The runtime should not convert native/platform failures into false success states.

## 11. Testing strategy

Tests are layered:

1. immutable data-model tests;
2. deterministic CPU/ISA semantic tests;
3. deterministic 128D cognition tests;
4. desktop capability and launcher-probe tests using injected probes;
5. desktop-entry round-trip tests;
6. REST resource tests;
7. Maven/JDK 25 CI verification.

Real GNOME/KDE/Xfce/Aurora compositors must not be launched in ordinary CI tests.

## 12. Native integration

Where Java cannot reproduce hardware behavior, use an explicit native adapter. Candidate technologies include Java Foreign Function & Memory APIs or JNI, subject to platform requirements and compatibility testing.

Native adapters must not silently change the documented ABI or bypass policy boundaries.

## 13. Compatibility contract

The Java implementation remains additive. Native Chimera remains authoritative for hardware and ABI details. Any future Java subsystem claiming conformance must provide reference vectors, compatibility tests and an updated migration record.
