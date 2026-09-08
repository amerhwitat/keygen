# Chimera II → Java migration manifest

## 1. Authority and scope

**Native source-of-record:** `amerhwitat/ChimeraIIOS` at the migration snapshot used for the Java port.

This repository, `amerhwitat/keygen`, is the isolated Java implementation track. The migration is semantic rather than a claim of byte-for-byte hardware equivalence. Existing Java API/ABI contracts are preserved wherever practical; native-only behavior remains explicitly identified.

`amerhwitat/test` and `amerhwitat/ChimeraIIOS` are outside the scope of this repository's Java changes.

## 2. Migration principles

1. Preserve externally visible semantics before adding convenience features.
2. Do not translate native instructions into Java in a way that falsely implies hardware equivalence.
3. Keep hardware, boot, driver, GPU and compositor responsibilities behind explicit boundaries.
4. Prefer deterministic, testable Java models for ISA, kernel state, cognition and desktop selection.
5. Record source provenance for every major migrated subsystem.
6. Use native adapters only when the Java runtime cannot faithfully represent a platform boundary.

## 3. Assembly / C mappings

| Native source | Java representation | Boundary |
|---|---|---|
| `boot/x86/mbr/boot.asm` | boot semantic model | JVM cannot execute x86 real-mode boot code |
| `src/arch/x86_64/chimera_fastpath.S` | architecture/fast-path abstraction | optional native FFM/JNI adapter |
| `src/chimera.c` | core Java runtime services | semantic mapping |
| `src/server.c` | Jakarta/Java server boundary | Java networking/service model |
| `integrations/windows/KMDF_ChmEcho/Driver/Driver.c` | service contract | actual KMDF driver remains native |
| `tests/test_chimera.c` | JUnit semantic tests | behavior-focused |

## 4. C++ ISA/kernel mappings

| Native source | Java target | Status |
|---|---|---|
| `src/isa/chimera_isa.cpp` | `org.chimera.core.ChimeraCpu` / instruction model | semantic implementation |
| `src/isa/ISA_EncoderDecoder.cpp` | Java encoder/decoder model | Java-side representation |
| `src/isa/isa_bitfields.cpp` | Java bitfield codec | Java-side representation |
| `src/isa/unified_isa.cpp` | Java ISA registry | Java-side representation |
| `src/kernel/bootinfo.cpp` | boot metadata model | semantic |
| `src/kernel/chimera_kernel_main.cpp` | `KoronosKernel.boot()` | semantic |
| `src/kernel/kernel.cpp` | Java kernel lifecycle | semantic |
| `src/kernel/kernel_arch.cpp` | architecture service interfaces | boundary |
| `src/main.cpp` | Java application/runtime entry | semantic |
| `src/net/inet.cpp` | Java NIO/Jakarta networking | semantic/service boundary |
| `src/net/spotnik.cpp` | Java network adapter | service boundary |
| `src/runtime/nbit_runtime.cpp` | Java N-bit runtime | semantic |
| `src/dma/dma.cpp` | Java DMA abstraction | simulator/service boundary |
| `src/services/kore.cpp` | Java service registry | semantic |
| `kernel/core/koronos.cpp` | `KoronosKernel` | semantic |
| `tests/unit/*.cpp` | JUnit tests | behavior-focused |
| `integrations/ue5/AuroraDesktop/Source/AuroraDesktop/*.cpp` | Aurora integration interfaces | rendering stays native/platform-specific |
| `integrations/windows/KMDF_ChmEcho/UserModeTest/ChmEchoTest.cpp` | JUnit/service-contract tests | semantic |

## 5. Python cognition/tooling mappings

| Native/tool source | Java target |
|---|---|
| `tools/cognition/chimera_rnn.py` | `KoronosRnn128`, `Vector128D`, `KnowledgeBus` |
| `tests/cognition/test_chimera_rnn.py` | JUnit cognition tests |
| `tools/isa/chimera_asm.py` | Java assembler model |
| `tools/isa/generate_encoder_decoder_sample.py` | Java generator workflow |
| `tools/isa/generate_isa_bitfields.py` | Java bitfield generation workflow |
| `tools/isa/test_isa_conformance.py` | JUnit ISA conformance tests |
| `tools/isa/test_isa_toolchain.py` | JUnit toolchain tests |
| `tools/isa/test_vector_generator.py` | Java vector tests |
| `tools/kernel_depth_crawler.py` | Java repository/introspection tooling |
| `tools/installer/installer_plan.py` | Java installer-plan model |
| `tools/shell/generate_command_registry.py` | Java command registry direction |
| `tools/source_import/import_w2k_asm.py` | Java import/parser service |

## 6. 8192-bit register model

A Chimera 8192-bit register is represented as 128 × 64-bit lanes. This representation makes the width explicit while remaining practical on the JVM.

```text
R8192
 ├── lane[0]   : 64 bits
 ├── lane[1]   : 64 bits
 ├── ...
 └── lane[127] : 64 bits
```

Operations that require arbitrary precision or exact carry semantics may use the Java semantic layer rather than assuming that a host CPU exposes a native 8192-bit register.

## 7. Canonical instruction model

The documented canonical packet is 16 bytes:

```text
bytes  0..1   opcode
bytes  2..3   destination register
bytes  4..5   source A register
bytes  6..7   source B register
bytes  8..15  immediate / extension payload
```

The Java instruction model preserves these field boundaries. The complete native opcode inventory remains authoritative; the Java semantic CPU currently implements the supported subset and must not be described as a complete hardware emulator unless full conformance is demonstrated.

## 8. Koronos 128D

Koronos treats 128 dimensions as a research representation space:

```text
input / evidence / telemetry
             │
             ▼
        128D vector
             │
             ▼
       recurrent state
             │
             ▼
 bounded adaptation/output
             │
             ▼
       observable services
```

The recurrent implementation is deterministic under its seed and keeps adaptation bounded and observable. The model is an architectural research prototype, not a claim of AGI, consciousness or autonomous superintelligence.

## 9. Linux desktop/Aurora mapping

The Java desktop package adds an orchestration layer for:

- Aurora / Wayland;
- Fedora GNOME;
- Ubuntu GNOME;
- Debian GNOME;
- KDE Plasma;
- Xfce;
- Cinnamon;
- MATE;
- LXQt;
- GNOME Flashback;
- Safe / Minimal;
- Headless / Server.

The Java layer owns profile metadata, capability detection, launcher availability, startup selection, XDG/freedesktop metadata and structured launch plans. Actual compositors, display managers, GPU drivers and desktop processes remain native.

## 10. Non-portable native responsibilities

The following are intentionally not presented as JVM-native replacements:

- x86 firmware and boot-sector execution;
- privileged CPU instructions;
- MMIO and real interrupt controllers;
- real DMA engines and IOMMUs;
- NIC/XDP zero-copy hardware;
- KMDF kernel-driver execution;
- GPU driver execution;
- Wayland/X11 compositor internals;
- distribution package management;
- display-manager and systemd session orchestration.

Where integration is required, use explicit service interfaces, test doubles, or optional FFM/JNI/native adapters.

## 11. Conformance expectations

A future complete Java ISA implementation should be generated from the authoritative opcode/bitfield tables and tested against native reference vectors. Until that work is complete, this repository must distinguish clearly between:

- **implemented semantic behavior**;
- **modeled interfaces**;
- **planned/generated components**;
- **native-only behavior**.

This distinction is part of the project's compatibility contract.
