# Chimera II Java Runtime Architecture

## 1. Overview

The Java track is a semantic and orchestration implementation of selected Chimera II concepts. It is designed to run on JDK 25, retain explicit native boundaries, and provide deterministic tests around CPU, cognition, services, desktop selection and persistent kernel learning.

## 2. Layers

```text
┌───────────────────────────────────────────────┐
│ Applications / GUI / TUI / REST clients       │
├───────────────────────────────────────────────┤
│ Jakarta REST + service resources              │
├───────────────────────────────────────────────┤
│ Koronos self-learning kernel                  │
│ lifecycle / observation / adaptation / store  │
├───────────────────────┬───────────────────────┤
│ Chimera CPU / ISA     │ Koronos 128D cognition │
│ R8192 / instructions  │ vectors / RNN / bus    │
├───────────────────────┴───────────────────────┤
│ Persistent kernel database                    │
│ H2 embedded DB → /var/Cimera/Data/kernel      │
├───────────────────────────────────────────────┤
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

## 4. Self-learning kernel

`KoronosKernel` now combines the lifecycle, cognition and persistent learning boundary.

On every `observe(telemetry)` call it:

1. validates that the kernel is booted;
2. feeds the telemetry through the 128D recurrent model;
3. performs a bounded self-supervised adaptation using the telemetry's tanh representation as the target;
4. records the observation and learning loss;
5. checkpoints the complete recurrent model state.

`learn(input, target)` remains available for explicit externally supplied training targets.

Learning is deliberately bounded. The model cannot rewrite Java kernel code, the ISA/ABI, executable paths or privilege policy. The feature is a research adaptive-kernel mechanism, not a claim of AGI, consciousness or autonomous superintelligence.

## 5. Persistent kernel database

`KernelDataStore` isolates persistence from kernel logic. `H2KernelDataStore` provides the embedded implementation using a local H2 database.

Default runtime location:

```text
/var/Cimera/Data/
└── kernel.mv.db
```

The directory is created automatically when the running service has sufficient filesystem permissions. Tests use an injected temporary directory so CI never depends on `/var`.

The database currently stores:

- telemetry observations;
- learning events and loss values;
- complete recurrent model snapshots containing weights, bias and hidden state.

A new kernel instance loads the latest valid model snapshot, allowing learned state to survive process restart.

## 6. 128D cognition

The cognition layer consists of:

- `Vector128D` — fixed 128-dimensional state representation;
- `KoronosRnn128` — deterministic recurrent research model with bounded adaptation and snapshot/restore;
- `KnowledgeBus` — evidence/knowledge transport boundary;
- `ModelSnapshotCodec` — validated deterministic model-state encoding.

## 7. Desktop runtime

The `org.chimera.desktop` package provides:

- `DesktopProfileRegistry` — canonical profile inventory;
- `DesktopCapabilityDetector` — host/session/launcher observations;
- `DesktopStartupMenu` — deterministic selectable menu;
- `DesktopSessionManager` — selection and fallback policy;
- `DesktopLaunchPlan` — immutable structured process description;
- `ProcessDesktopAdapter` — native process-launch boundary;
- `DesktopEntry` / `DesktopEntryCodec` — freedesktop metadata model;
- `XdgEnvironment` — XDG environment model.

## 8. Desktop state machine

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

## 9. API boundary

Jakarta REST is rooted at `/api`. Desktop resources expose inventory, current state and selection-plan generation. Selection is intentionally separated from execution so API clients cannot accidentally become an unrestricted process launcher.

Future kernel telemetry endpoints should expose aggregate metrics rather than raw database access.

## 10. Concurrency

The Java runtime favors stable JDK 25 concurrency primitives. Virtual threads are appropriate for service-style tasks with blocking I/O. Kernel learning calls are synchronized at the model boundary so a persisted snapshot corresponds to a coherent recurrent state.

## 11. Error handling and recovery

Errors remain explicit at subsystem boundaries:

- invalid instruction → instruction/CPU validation error;
- unavailable desktop → desktop selection error;
- unsupported native operation → adapter/service boundary error;
- failed native process → process adapter failure;
- malformed desktop entry → codec validation error;
- database initialization/write failure → kernel persistence error.

The runtime must not convert native/platform/database failures into false success states.

## 12. Testing strategy

Tests are layered:

1. immutable data-model tests;
2. deterministic CPU/ISA semantic tests;
3. deterministic 128D cognition tests;
4. persistence and restart tests using temporary databases;
5. desktop capability and launcher-probe tests using injected probes;
6. desktop-entry round-trip tests;
7. REST resource tests;
8. Maven/JDK 25 CI verification.

Real GNOME/KDE/Xfce/Aurora compositors must not be launched in ordinary CI tests.

## 13. Native integration

Where Java cannot reproduce hardware behavior, use an explicit native adapter. Candidate technologies include Java Foreign Function & Memory APIs or JNI, subject to platform requirements and compatibility testing.

Native adapters must not silently change the documented ABI or bypass policy boundaries.

## 14. Compatibility contract

The Java implementation remains additive. Native Chimera remains authoritative for hardware and ABI details. Any future Java subsystem claiming conformance must provide reference vectors, compatibility tests and an updated migration record.
