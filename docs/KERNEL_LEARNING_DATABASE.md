# Koronos Self-Learning Kernel and Internal Database

## Purpose

The Java Koronos kernel now includes a bounded self-learning loop with durable local state. The default persistence root is:

```text
/var/Cimera/Data
```

The embedded database is stored as:

```text
/var/Cimera/Data/kernel.mv.db
```

The same implementation accepts an injected `Path` for tests and controlled deployments.

## Runtime learning loop

```text
Telemetry
   │
   ▼
KoronosKernel.observe()
   │
   ├── record observation
   │
   ├── 128D recurrent inference
   │
   ├── bounded self-supervised adaptation
   │       target = tanh(telemetry)
   │
   ├── record learning loss
   │
   └── persist model snapshot
              │
              ▼
       H2 Kernel Database
              │
              ├── observations
              ├── learning_events
              └── model_snapshots
```

## Persistent model

A model snapshot contains the complete current Koronos 128D recurrent state:

- recurrent weights (`128 × 128`);
- input weights (`128 × 128`);
- bias (`128`);
- hidden state (`128`).

Snapshots use a deterministic binary representation with explicit length validation. On startup the kernel loads the latest snapshot, so learned state survives process restart.

## Database abstraction

`KernelDataStore` is the persistence boundary. `H2KernelDataStore` is the default embedded implementation. Keeping the interface separate allows a future native or distributed backend without changing the kernel learning API.

## Safety boundaries

Self-learning is intentionally bounded. Learned data cannot directly rewrite kernel source, Java classes, the ISA/ABI, privilege policy, executable paths, or native binaries. Database corruption or incompatible snapshots fail explicitly instead of silently being treated as valid learned state.

The current mechanism is a research adaptive-kernel subsystem; it is not an implementation claim of AGI, consciousness, or autonomous superintelligence.

## Linux deployment

A production installation should create the directory with service ownership and restrictive permissions before starting Koronos, for example conceptually:

```text
/var/Cimera/Data
```

The Java runtime creates missing directories when the process has permission to do so. The application should run under a dedicated service account rather than as an unrestricted root process.

## Testing

Persistence tests use JUnit temporary directories. They verify:

1. observations are stored;
2. observations automatically create learning events;
3. model snapshots are created;
4. the last learning loss is available;
5. a new kernel instance restores the learned 128D state;
6. explicit target-based learning remains available;
7. the default production path is `/var/Cimera/Data`.

## ABI compatibility

The existing instruction format and CPU semantic API remain unchanged. The self-learning/database functionality is additive at the Koronos kernel layer. Native Chimera remains the source of record for hardware and ABI behavior.
