# Crypto Interoperability and Upstream Provenance

The Java/Koronos track can model cryptographic workflows, but native cryptographic correctness remains governed by the Chimera II conformance layer and published standards.

## Standards

- BIP-32: https://github.com/bitcoin/bips/blob/master/bip-0032.mediawiki
- ERC-55 / EIP-55: https://eips.ethereum.org/EIPS/eip-55
- ERC-1191: https://eips.ethereum.org/EIPS/eip-1191

## Reference implementations reviewed

- https://github.com/bitcoinjs/bip32 — MIT, TypeScript BIP-32.
- https://github.com/bitcoinjs/bitcoinjs-lib — MIT, Bitcoin and BIP ecosystem.
- https://github.com/paulmillr/scure-bip32 — MIT, audited BIP-32 implementation.
- https://github.com/paulmillr/scure-bip39 — MIT, audited BIP-39 implementation.
- https://github.com/bitcoin-core/secp256k1 — MIT, high-assurance secp256k1 C implementation.

These are independent projects. This repository records compatibility observations rather than copying their source.

## Java implementation direction

Future Java crypto adapters should prefer standard, reviewed libraries where appropriate and should preserve dependency license notices. Test fixtures should use published deterministic vectors or synthetic keys, never real user wallet secrets.

## Security boundary

Supported workflows operate on key/seed material already legitimately possessed by the operator. The project must not provide public-address-to-private-key recovery, guessing, targeting, or brute-force wallet search.

## Chimera integration

The Java model may benchmark or emulate wide modular arithmetic, hashing, signature verification and key-derivation workflows for C8192/R8192 conformance. The native Chimera implementation remains authoritative for ISA semantics.
