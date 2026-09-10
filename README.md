# Chimera II OS — Java 25 / Koronos 128D

This repository is the isolated Java implementation track for Chimera II OS. It provides a semantic JVM model of Chimera processor/kernel concepts, the Koronos 128D research runtime, persistent self-learning, Linux desktop orchestration, runtime/service integration, concurrency, trusted-node federation and cross-platform interoperability.

## Cross-language crypto integration

The repository participates in the unified Chimera crypto solution:

- `java/` — authoritative Java interoperability/application layer.
- `cpp/` — native C++ interoperability layer.
- `node/` — Node.js ESM integration layer.
- existing Python research artifacts remain interoperable through deterministic JSON/JSONL contracts.

All implementations use public/synthetic vectors and shared provenance metadata. Wallet signing remains external and owner-controlled. Address-to-private-key recovery, seed guessing and unauthorized credential attacks are excluded.

## Existing Java/Koronos scope

- Java 25 / Maven baseline and Jakarta EE 11.
- 8192-bit register model represented as 128 × 64-bit lanes.
- Canonical 16-byte Chimera instruction representation.
- Deterministic Koronos 128D research runtime.
- Persistent self-learning kernel using H2.
- High-concurrency runtime and trusted Ed25519 node federation.
- Linux/Windows/Unix/macOS/GNU compatibility catalogs and native-service integration boundaries.
- C8192/R8192 semantic and conformance metadata.

## Build

```bash
mvn test
mvn package
```

See `docs/CRYPTO_AI_INTEROPERABILITY.md` and the root documentation for the cross-language contract.