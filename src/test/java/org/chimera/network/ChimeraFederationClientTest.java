package org.chimera.network;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ChimeraFederationClientTest {
    @Test void untrustedPeerIsRejectedBeforeNetwork() {
        var client = new ChimeraFederationClient(new ChimeraTrustStore());
        var identity = TrustedChimeraNode.generateIdentity();
        var message = ChimeraNodeMessage.create(new TrustedChimeraNode("node-a", identity.getPublic()), identity, "m1", "test", new byte[]{1});
        assertThrows(SecurityException.class, () -> client.send(URI.create("http://127.0.0.1:9/message"), "node-b", message));
    }

    @Test void remotePlainHttpIsRejected() {
        var trust = new ChimeraTrustStore();
        var identity = TrustedChimeraNode.generateIdentity();
        trust.trust(new TrustedChimeraNode("node-b", identity.getPublic()));
        var client = new ChimeraFederationClient(trust);
        var message = ChimeraNodeMessage.create(new TrustedChimeraNode("node-a", identity.getPublic()), identity, "m2", "test", new byte[]{2});
        assertThrows(SecurityException.class, () -> client.send(URI.create("http://remote-host.invalid/message"), "node-b", message));
    }
}
