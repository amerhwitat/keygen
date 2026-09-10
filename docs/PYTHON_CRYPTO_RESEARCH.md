# Python Cryptography Research Layer

The Java/Koronos track can interoperate with the Python crypto research tools through deterministic public test vectors and serialized metadata.

## Covered areas

- Bitcoin and Ethereum key/address concepts.
- secp256k1, Ed25519 and public-key signatures.
- SHA-256, SHA-512, SHA-3 and Keccak-family hashing concepts.
- BIP-32 hierarchical deterministic key derivation as an interoperability reference.
- EIP-55 address checksums.
- Cryptographic test vectors and conformance workloads for Chimera C8192/R8192 research.

## Python GUI policy

Python GUIs should expose public metadata, test-vector verification, hashing, signature verification, and protocol analysis. They must not implement private-key recovery or address-targeted brute-force search.

## Interoperability

The Java implementation's Ed25519 trusted-node layer can exchange deterministic test fixtures with the Python research layer. Secret material must remain local and must never be committed to Git repositories, logs, CI artifacts, or documentation.
