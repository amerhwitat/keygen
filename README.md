# Chimera II OS — Java 25 / Koronos 128D

This repository is the isolated Java implementation track for Chimera II OS. It provides a semantic JVM model of Chimera processor/kernel concepts, the Koronos 128D research runtime, persistent self-learning, Linux desktop orchestration, Linux runtime/service integration, high concurrency, trusted-node federation and cross-platform interoperability.

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
- Kali security-tool/metapackage catalog including Nmap, Burp Suite and Metasploit for authorized security testing.
- IPv4/IPv6 network scanner inventory including Nmap, Masscan, ZMap, RustScan, Naabu and common discovery utilities.
- Windows 16/32/64-bit compatibility profiles, PE32/PE32+ boundaries and native/Wine execution planning.
- Windows/Linux/Unix/BSD/macOS filesystem interoperability inventory.
- SMB2/SMB3, NFS/NFSv4, Samba, Active Directory/LDAP/Kerberos, mDNS/Bonjour and SMB network-browser integration boundaries.
- macOS Tahoe 26 / Finder integration descriptors.
- Aurora/Fedora/Ubuntu/Debian and common Linux desktop startup orchestration.
- CI verification with JDK 25 and Maven tests.

## Cross-platform compatibility

`org.chimera.compat` contains the compatibility layer:

- `NetworkScannerCatalog` — IPv4/IPv6 scanner inventory.
- `FileSystemCatalog` — Windows, Linux, Unix/BSD and Apple filesystem families.
- `WindowsCompatibilityCatalog` — Win16, Win32, Win64/PE32+, Windows Server and ARM64 profiles.
- `NetworkInteroperabilityCatalog` — Samba daemons, SMB browser/client, NFS, LDAP, Kerberos and mDNS roles.
- `MacNetworkCatalog` — macOS Tahoe 26, Finder, SMB, NFS, Bonjour and directory-service integration.
- `W2kAsmCompatibilityManifest` — provenance and architectural compatibility metadata for the Library `W2K-ASM.txt` corpus.

The supplied `W2K-ASM.txt` is 921,435 lines and contains historical x86, Alpha, PowerPC, Win16/Win32 thunking and emulator/8087 material. It carries Microsoft Confidential/proprietary notices, so the complete corpus is **not redistributed into this public repository**. The Library copy remains the reference source while its compatibility targets are represented by the manifest and Windows compatibility layer.

## Linux and network interoperability

The Java layer catalogs native runtimes and services rather than vendoring third-party operating systems or binaries. Native package managers remain responsible for signatures and updates. Samba remains the native SMB/AD implementation; NFS remains a native filesystem protocol; Finder remains Apple's native desktop shell.

Nmap supports both IPv4 and IPv6, including IPv6 operation with `-6`. All scanning functionality is intended for authorized networks and hosts.

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
│ Compatibility / Runtime / Network / Desktop Integration   │
│ Windows • Linux • Unix • macOS • SMB • NFS • AD • Finder  │
├────────────────────────────────────────────────────────────┤
│ Trusted Chimera Federation                                 │
│ Ed25519 identities → explicit trust → signed messages      │
└────────────────────────────────────────────────────────────┘
                              │
                              ▼
                Native operating-system services
```

## Persistent self-learning

`KoronosKernel.observe()` performs a bounded self-supervised update and persists observations, learning events and complete 128D model snapshots. A fresh kernel restores the latest valid snapshot. Learning cannot rewrite kernel code, ISA/ABI definitions, privilege policy, executable paths or native binaries.

## Highly multithreaded and multiprocess Koronos

`KoronosConcurrency` provides virtual threads for high fan-out I/O, bounded platform workers for CPU-oriented tasks and structured `ProcessBuilder(List<String>)` process isolation for external runtimes/services. The process boundary does not concatenate shell command strings.

## Trusted Chimera nodes

Nodes do not trust one another merely because they can reach the network. Each node has an Ed25519 identity; the receiving node explicitly registers the sender public key. Inter-node application messages are signed and bound to message metadata plus a SHA-256 payload digest. Stale, untrusted or tampered messages are rejected.

## Linux runtime/service stack

The Java layer uses a runtime catalog, not a vendor dump of third-party binaries. Current catalog targets include Python 3.14.7, OpenJDK 26.0.2.1, Zsh 5.9.2, PowerShell 7.6.2, .NET 10.0.400, NGINX 1.30.4 stable, BIND 9.20.27 and Postfix 3.11.7; Firefox and Chrome track current stable packages rather than pinning stale browser builds.

For Kali, the catalog exposes native metapackages and a top-tool profile rather than embedding an entire Kali filesystem.

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

See `docs/CROSS_PLATFORM_COMPATIBILITY.md` for the complete interoperability model and `docs/DOCUMENTATION_INDEX.md` for the documentation map.
