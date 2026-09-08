# Chimera II → Java migration manifest

Source-of-record: `amerhwitat/ChimeraIIOS` at the migration snapshot used for this port. The native repository is read-only for this project; this repository is a separate Java implementation track.

## Native source families identified

### Assembly / C
- `boot/x86/mbr/boot.asm` → `org.chimera.boot.BootModel` (semantic boot state; JVM cannot execute x86 real-mode instructions).
- `src/arch/x86_64/chimera_fastpath.S` → `org.chimera.arch.FastPath` abstraction (Java dispatch; optional Panama/JNI adapter).
- `src/chimera.c` → core Java runtime services.
- `src/server.c` → Jakarta/Java server boundary.
- `integrations/windows/KMDF_ChmEcho/Driver/Driver.c` → Java service contract; actual KMDF driver remains native-only.
- `tests/test_chimera.c` → JUnit semantic tests.

### C++ ISA/kernel
- `src/isa/chimera_isa.cpp` → `org.chimera.isa.ChimeraCpu` and `Instruction`.
- `src/isa/ISA_EncoderDecoder.cpp` → Java encoder/decoder module (planned/generated from the ISA tables).
- `src/isa/isa_bitfields.cpp` → Java bitfield codec (planned/generated).
- `src/isa/unified_isa.cpp` → Java unified ISA registry (planned/generated).
- `src/kernel/bootinfo.cpp` → Java boot metadata model.
- `src/kernel/chimera_kernel_main.cpp` → `KoronosKernel.boot()`.
- `src/kernel/kernel.cpp` → Java kernel lifecycle.
- `src/kernel/kernel_arch.cpp` → architecture service interfaces.
- `src/main.cpp` → Java application entry point.
- `src/net/inet.cpp` → Java NIO/Jakarta networking service.
- `src/net/spotnik.cpp` → Java network service adapter.
- `src/runtime/nbit_runtime.cpp` → Java N-bit runtime package.
- `src/dma/dma.cpp` → Java DMA service abstraction.
- `src/services/kore.cpp` → Java service registry.
- `kernel/core/koronos.cpp` → `KoronosKernel`.
- `tests/unit/*.cpp` → JUnit tests.
- `integrations/ue5/AuroraDesktop/Source/AuroraDesktop/*.cpp` → Java/Aurora integration interfaces; rendering remains platform-specific.
- `integrations/windows/KMDF_ChmEcho/UserModeTest/ChmEchoTest.cpp` → JUnit/service-contract tests.

### Python cognition/tooling
- `tools/cognition/chimera_rnn.py` → `KoronosRnn128`, `Vector128D`, `KnowledgeBus`.
- `tests/cognition/test_chimera_rnn.py` → JUnit cognition tests.
- `tools/isa/chimera_asm.py` → Java assembler package.
- `tools/isa/generate_encoder_decoder_sample.py` → Java code generator.
- `tools/isa/generate_isa_bitfields.py` → Java bitfield generator.
- `tools/isa/test_isa_conformance.py` → JUnit ISA conformance suite.
- `tools/isa/test_isa_toolchain.py` → JUnit toolchain tests.
- `tools/isa/test_vector_generator.py` → Java vector generator tests.
- `tools/kernel_depth_crawler.py` → Java repository/introspection tooling.
- `tools/installer/installer_plan.py` → Java installer-plan model.
- `tools/shell/generate_command_registry.py` → Java command registry generator.
- `tools/source_import/import_w2k_asm.py` → Java import/parser service.

## 128D architecture

The 128D model is treated as a **research representation space**, not as a claim that 128 dimensions are physically fundamental. A state vector has 128 channels; recurrent dynamics operate on that state; telemetry/evidence are projected into it; learning is bounded and observable.

```text
                    ┌───────────────────────┐
                    │     Koronos Kernel    │
                    └──────────┬────────────┘
                               │
                 telemetry / evidence / policy
                               │
                    ┌──────────▼────────────┐
                    │       128D State      │
                    │  x[0] ... x[127]      │
                    └──────────┬────────────┘
                               │
                       recurrent cell
                               │
                    ┌──────────▼────────────┐
                    │   learned hidden h_t  │
                    └──────────┬────────────┘
                               │
                 bounded adaptation / output
                               │
                    ┌──────────▼────────────┐
                    │ services / inference │
                    └───────────────────────┘
```

The original Python RNN explicitly describes itself as an architectural seed rather than an AGI claim and recommends replacing the toy cell with a validated GRU/SSM implementation. The Java port keeps that boundary and makes the 128D research model explicit.

## Non-portable native responsibilities

The following are intentionally represented as Java interfaces/simulators rather than pretending that Java can replace hardware execution:

- x86 boot-sector execution
- privileged CPU instructions
- MMIO and interrupt controllers
- real DMA engines
- IOMMU hardware
- NIC/XDP zero-copy hardware
- KMDF kernel driver execution
- GPU/Wayland native compositor paths

For these, Java uses service boundaries, test doubles, and optional Foreign Function & Memory / JNI adapters. This preserves architecture without silently changing hardware semantics.
