# Chimera II OS — Java 25 / Koronos 128D

This repository is the **isolated Java implementation track** for Chimera II OS. It provides a semantic JVM model of Chimera processor/kernel concepts, the Koronos 128D research runtime, persistent self-learning, Linux desktop orchestration, Linux runtime/service integration, high concurrency and trusted-node federation.

> **Scope boundary:** this repository does not modify `amerhwitat/ChimeraIIOS` or `amerhwitat/test`. Native Chimera remains the source-of-record for hardware, boot, ABI, driver, compositor and platform-specific behavior.

## Project status

- Java 25 / Maven baseline and Jakarta EE 11.
- 8192-bit register model represented as 128 × 64-bit lanes.
- Canonical 16-byte Chimera instruction representation.
- Deterministic Koronos 128D research runtime.
- Persistent self-learning kernel using H2 at `/var/Cimera/Data/kernel.mv.db` by default.
- High-concurrency runtime: virtual threads, bounded CPU worker pool and isolated native processes.
- Explicit Ed25519 trust identities and signed inter-node messages.
- Linux runtime catalog for Python, OpenJDK, Firefox, Chrome, Bash, Zsh, PowerShell, .NET, NGINX, BIND 9 and Postfix.
- Kali security-tool/metapackage catalog including Burp Suite and Metasploit for authorized security testing.
- Aurora/Fedora/Ubuntu/Debian and common Linux desktop startup orchestration.
- CI verification with JDK 25 and Maven tests.

## Architecture

```text
                 Chimera native source-of-record
                              │
                    semantic/conformance boundary
                              ▼
┌────────────────────────────────────────────────────────────┐
│                       Koronos Kernel                       │
├──────────────────┬──────────────────┬─────────────────────┤
│ 8192-bit CPU/ISA │ self-learning    │ high concurrency    │
│ semantic model   │ 128D + H2 DB     │ threads/processes   │
├──────────────────┴──────────────────┴─────────────────────┤
│ Linux Runtime / Services / Desktop / Security Tool Catalog │
│ Python • Java • Firefox • Chrome • Bash • Zsh • pwsh       │
│ .NET • NGINX • BIND • Postfix • Kali profiles              │
├────────────────────────────────────────────────────────────┤
│ Trusted Chimera Federation                                 │
│ Ed25519 identities → explicit trust → signed messages      │
└────────────────────────────────────────────────────────────┘
                              │
                              ▼
                Native Linux services / compositor
```

## Persistent self-learning

`KoronosKernel.observe()` performs a bounded self-supervised update and persists observations, learning events and complete 128D model snapshots. A fresh kernel restores the latest valid snapshot. Learning cannot rewrite kernel code, ISA/ABI definitions, privilege policy, executable paths or native binaries.

## Highly multithreaded and multiprocess Koronos

`KoronosConcurrency` provides:

- virtual threads for high fan-out I/O;
- bounded platform workers for CPU-oriented tasks;
- structured `ProcessBuilder(List<String>)` process isolation for external runtimes/services;
- no shell-command concatenation in the process boundary.

The kernel exposes these through `submitIo`, `submitCpu` and `startProcess` while retaining the existing CPU and learning APIs.

## Trusted Chimera nodes

Nodes do not trust one another merely because they can reach the network. Each node has an Ed25519 identity; the receiving node explicitly registers the sender public key. Inter-node application messages are signed and bound to message metadata plus a SHA-256 payload digest. Stale, untrusted or tampered messages are rejected.

The current trust policy is in-memory; durable trust records are designed to fit the existing kernel database boundary before production federation deployment.

## Linux runtime/service stack

The Java layer uses a **runtime catalog**, not a vendor dump of third-party binaries. This is important for licensing, signatures, security updates and native dependencies. Current catalog targets include Python 3.14.7, OpenJDK 26.0.2.1, Zsh 5.9.2, PowerShell 7.6.2, .NET 10.0.400, NGINX 1.30.4 stable, BIND 9.20.27 and Postfix 3.11.7; Firefox and Chrome track current stable packages rather than pinning stale browser builds.

For Kali, the catalog exposes native metapackages and a top-tool profile rather than embedding an entire Kali filesystem. It includes Nmap, Burp Suite, Metasploit Framework, Wireshark, Aircrack-ng, Hydra, John, NetExec, Responder and sqlmap. Use these only on systems and targets for which authorization exists.

See `docs/LINUX_RUNTIME_STACK.md` for the complete model.

## Linux desktop and Aurora

The desktop registry covers Aurora/Wayland, Fedora GNOME, Ubuntu GNOME, Debian GNOME, KDE Plasma, Xfce, Cinnamon, MATE, LXQt, GNOME Flashback, Safe/Minimal and Headless/Server. Availability is detected on the host; the Java layer does not replace native compositors or package managers.

## CPU / ISA and 128D

The Java CPU model preserves the documented 8192-bit/128×64-bit semantic representation and canonical 16-byte instruction packet. The native ISA remains authoritative; complete native ISA parity is not claimed until generated conformance vectors cover the authoritative opcode/bitfield set.

Koronos 128D is a bounded research architecture, not a claim of AGI, consciousness or autonomous superintelligence.

## Build and test

Requirements: JDK 25 and Maven 3.9+.

```bash
mvn test
mvn package
```

JDK 25 remains the build baseline for compatibility while OpenJDK 26 is cataloged as the current Java feature release.

## Documentation map

- `docs/DOCUMENTATION_INDEX.md` — documentation map.
- `docs/JAVA_RUNTIME_ARCHITECTURE.md` — complete Java architecture.
- `docs/KERNEL_LEARNING_DATABASE.md` — self-learning and persistence.
- `docs/LINUX_RUNTIME_STACK.md` — Linux runtimes, services and Kali integration.
- `docs/KORONOS_DISTRIBUTED_RUNTIME.md` — concurrency, processes and trusted nodes.
- `docs/LINUX_DESKTOP_AURORA.md` — desktop/session integration.
- `docs/DESKTOP_API.md` — Jakarta REST contract.
- `docs/SOURCE_MIGRATION_MANIFEST.md` — native-to-Java provenance.

## Non-goals

This repository does not replace the Linux kernel, systemd, display managers, native desktop compositors, third-party browsers/services, or native Chimera hardware. It provides a coherent orchestration and semantic runtime layer around those components while preserving the existing Java ABI/API intent.
