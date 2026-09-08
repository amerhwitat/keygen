# Koronos Distributed Runtime

**Last synchronized:** 2026-09-09

## Concurrency model

Koronos now has three execution domains:

1. **Virtual threads** for very high fan-out I/O and service tasks.
2. **Bounded platform-thread workers** sized from available processors for CPU-oriented work.
3. **Native processes** for isolated external runtimes and services, launched with `ProcessBuilder(List<String>)` and never through shell concatenation.

This gives the Java kernel a highly multithreaded and multiprocess orchestration layer while preserving the native Chimera hardware/ABI boundary.

## Trusted Chimera nodes

Chimera nodes can exchange application messages only when the receiving node explicitly trusts the sender's Ed25519 public key.

Each message contains:

- sender identity;
- message ID;
- creation timestamp;
- topic;
- payload;
- Ed25519 signature bound to a SHA-256 digest of the payload and message metadata.

A receiver checks both the trust registry and a configurable message-age window. Payload modification invalidates the signature.

## Trust lifecycle

```text
Node identity generated
        |
        v
Public key exchanged out-of-band
        |
        v
Receiving node explicitly trusts key
        |
        v
Signed message accepted
        |
        +--> expired/untrusted/tampered -> reject
```

Trust is intentionally explicit. Network reachability alone does not establish trust.

## Persistence direction

The existing H2 kernel database at `/var/Cimera/Data` is the natural persistence boundary for durable node identities, trust records, replay state and federated knowledge. The current Java trust store is an in-memory policy layer; durable federation records should be added through `KernelDataStore` transactions before production deployment.

## Security boundary

Trusted-node communication is an authentication primitive, not an authorization bypass. Every sensitive operation still requires its normal kernel privilege and policy checks. Security tools are cataloged for authorized defensive testing and lab environments.
