# Crypto AI interoperability

Python research workloads can exchange deterministic datasets and test vectors with the Java/Koronos crypto components.

Recommended interfaces:

- JSON Lines for address/balance observations.
- JSON for model metadata and experiment configuration.
- CSV for tabular historical features.
- SHA-256 fingerprints for references to operator-owned keys without exposing secrets.
- C8192/R8192 conformance vectors for wide-integer and cryptographic benchmarks.

The AI layer can consume public blockchain observations and synthetic cryptographic workloads. It must not infer or recover private keys from public addresses.

CNN/RNN experiments should record feature schema, normalization, seed and model version. RL experiments should record environment version, reward definition, fees/slippage assumptions and evaluation period.
