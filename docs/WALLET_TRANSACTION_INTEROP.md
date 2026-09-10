# Wallet transaction interoperability

The keygen layer supplies deterministic crypto primitives and interoperability metadata. Wallet transaction execution is separated from key material.

## Transaction flow

1. Derive or import an operator-owned account using approved wallet recovery material.
2. Validate chain ID, network, recipient, amount, nonce/UTXO selection and fee.
3. Construct the unsigned transaction.
4. Sign through the wallet or an external signer.
5. Pass only the signed transaction to the broadcaster.
6. Record transaction hash, network and confirmation state.

Ethereum uses `eth_sendRawTransaction` for the final broadcast. Bitcoin can use authenticated Bitcoin Core wallet RPCs.

## Receive

Receiving addresses are public identifiers. The system may generate/request fresh wallet-owned receiving addresses and associate them with labels, derivation paths, and watch-only monitoring metadata.

## Burn-address policy

Addresses classified as `BURN` or provably unspendable are observation-only. Their balances may be indexed for accounting and analytics but are never added to spendable-balance calculations.

## Secret handling

No private key or seed phrase is persisted in interoperability JSON. Use a vault reference or non-secret fingerprint when an operator-owned identity must be correlated across components.
