package org.chimera.network;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class PersistentChimeraTrustStoreTest {
    @Test void trustSurvivesReload() throws Exception {
        var dir = Files.createTempDirectory("chimera-trust-");
        var file = dir.resolve("trusted.json");
        var pair = TrustedChimeraNode.generateIdentity();
        var first = new PersistentChimeraTrustStore(file);
        first.trust(new TrustedChimeraNode("node-a", pair.getPublic()));
        assertTrue(first.isTrusted("node-a"));

        var second = new PersistentChimeraTrustStore(file);
        assertTrue(second.isTrusted("node-a"));
        assertEquals(pair.getPublic(), second.get("node-a").publicKey());
        second.revoke("node-a");
        assertFalse(new PersistentChimeraTrustStore(file).isTrusted("node-a"));
    }

    @Test void privateKeyIsNotPersisted() throws Exception {
        var dir = Files.createTempDirectory("chimera-trust-");
        var file = dir.resolve("trusted.json");
        var pair = TrustedChimeraNode.generateIdentity();
        new PersistentChimeraTrustStore(file).trust(new TrustedChimeraNode("node-a", pair.getPublic()));
        var text = Files.readString(file);
        assertFalse(text.contains(java.util.Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded())));
    }
}
