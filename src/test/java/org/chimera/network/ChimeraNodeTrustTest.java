package org.chimera.network;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

class ChimeraNodeTrustTest {
    @Test void trustedNodeCanSignAndVerifyMessage() {
        KeyPair keys = TrustedChimeraNode.generateIdentity();
        TrustedChimeraNode node = new TrustedChimeraNode("node-a", keys.getPublic());
        ChimeraTrustStore trust = new ChimeraTrustStore();
        trust.trust(node);
        ChimeraNodeMessage message = ChimeraNodeMessage.create(node, keys, "m1", "knowledge", "hello".getBytes());
        assertTrue(message.verify(trust, 60));
    }

    @Test void untrustedNodeCannotVerify() {
        KeyPair keys = TrustedChimeraNode.generateIdentity();
        TrustedChimeraNode node = new TrustedChimeraNode("node-a", keys.getPublic());
        ChimeraNodeMessage message = ChimeraNodeMessage.create(node, keys, "m1", "knowledge", new byte[] {1,2,3});
        assertFalse(message.verify(new ChimeraTrustStore(), 60));
    }

    @Test void payloadTamperingInvalidatesSignature() {
        KeyPair keys = TrustedChimeraNode.generateIdentity();
        TrustedChimeraNode node = new TrustedChimeraNode("node-a", keys.getPublic());
        ChimeraTrustStore trust = new ChimeraTrustStore();
        trust.trust(node);
        ChimeraNodeMessage original = ChimeraNodeMessage.create(node, keys, "m1", "knowledge", new byte[] {1});
        ChimeraNodeMessage tampered = new ChimeraNodeMessage("node-a", "m1", original.createdAtEpochSecond(), "knowledge", new byte[] {2}, original.signature());
        assertFalse(tampered.verify(trust, 60));
    }
}
