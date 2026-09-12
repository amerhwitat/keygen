# Chimera II OS — Java 25 / Koronos 128D

This repository is the isolated Java implementation track for Chimera II OS. It provides a semantic JVM model of Chimera processor/kernel concepts, the Koronos 128D research runtime, persistent self-learning, Linux desktop orchestration, runtime/service integration, concurrency, trusted-node federation and cross-platform interoperability.

## Centralized Apple Objective-C + Flutter

The Apple companion is maintained in [`general/Apple-Implementations/keygen`](https://github.com/amerhwitat/general/tree/master/Apple-Implementations/keygen). It provides Objective-C/Xcode native integration and Flutter iOS/macOS UI while keeping Java/Koronos execution behind an explicit cross-language service boundary.

## Cross-language crypto integration

The repository participates in the unified Chimera crypto solution across Java, C++, Node.js, Python and Apple. All implementations use public/synthetic vectors and shared provenance metadata. Wallet signing remains external and owner-controlled. Address-to-private-key recovery, seed guessing and unauthorized credential attacks are excluded.

## Apple applications

`apple/project.yml` remains the native SwiftUI/Xcode boundary. IPA export requires macOS/Xcode and operator-controlled signing. Use XcodeGen to regenerate projects from source specifications.

## Existing Java/Koronos scope

- Java 25 / Maven baseline and Jakarta EE 11.
- 8192-bit register model represented as 128 × 64-bit lanes.
- Canonical 16-byte Chimera instruction representation.
- Deterministic Koronos 128D research runtime.
- Persistent self-learning kernel using H2.
- High-concurrency runtime and trusted Ed25519 node federation.
- C8192/R8192 semantic and conformance metadata.

## Build

```bash
mvn test
mvn package
```

For Apple builds on macOS: install Xcode/XcodeGen, generate `apple/project.yml`, then archive/export with Xcode.
