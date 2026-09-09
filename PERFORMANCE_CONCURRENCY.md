# Performance & Concurrency Policy

Use bounded concurrency for independent generation, encoding, file, and test workloads. Cryptographic primitives should remain delegated to vetted libraries and must not be made less safe by custom parallel implementations.

- Cap workers by hardware and workload size.
- Avoid oversubscription and per-item thread creation.
- Keep deterministic test paths where possible.
- Protect shared mutable state and prefer independent per-worker state.
- Benchmark before/after optimization.
