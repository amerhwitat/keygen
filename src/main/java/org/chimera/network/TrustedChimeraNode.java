package org.chimera.network;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Objects;

/** Ed25519 identity used for authenticated Chimera-to-Chimera messages. */
public record TrustedChimeraNode(String nodeId, PublicKey publicKey) {
    public TrustedChimeraNode {
        Objects.requireNonNull(nodeId, "nodeId");
        Objects.requireNonNull(publicKey, "publicKey");
        if (nodeId.isBlank()) throw new IllegalArgumentException("nodeId must not be blank");
        if (!"EdDSA".equalsIgnoreCase(publicKey.getAlgorithm()) && !"Ed25519".equalsIgnoreCase(publicKey.getAlgorithm()))
            throw new IllegalArgumentException("Only Ed25519 identities are supported");
    }

    public static KeyPair generateIdentity() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("Ed25519");
            return generator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException("Ed25519 is unavailable", e);
        }
    }

    public static byte[] sign(KeyPair identity, String nodeId, byte[] payload) {
        try {
            Signature signature = Signature.getInstance("Ed25519");
            signature.initSign(identity.getPrivate());
            signature.update(canonical(nodeId, payload));
            return signature.sign();
        } catch (Exception e) {
            throw new IllegalStateException("Unable to sign node message", e);
        }
    }

    public boolean verify(byte[] payload, byte[] signature) {
        try {
            Signature verifier = Signature.getInstance("Ed25519");
            verifier.initVerify(publicKey);
            verifier.update(canonical(nodeId, payload));
            return verifier.verify(signature);
        } catch (Exception e) {
            return false;
        }
    }

    public String encodedPublicKey() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    private static byte[] canonical(String nodeId, byte[] payload) {
        byte[] id = nodeId.getBytes(StandardCharsets.UTF_8);
        byte[] result = new byte[id.length + 1 + payload.length];
        System.arraycopy(id, 0, result, 0, id.length);
        result[id.length] = 0;
        System.arraycopy(payload, 0, result, id.length + 1, payload.length);
        return result;
    }
}
