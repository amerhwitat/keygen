package org.chimera.network;

import java.security.PublicKey;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** In-memory trust policy. Persistence can be backed by the kernel database later. */
public final class ChimeraTrustStore {
    private final Map<String, TrustedChimeraNode> trusted = new ConcurrentHashMap<>();

    public void trust(TrustedChimeraNode node) { trusted.put(node.nodeId(), node); }
    public void revoke(String nodeId) { trusted.remove(nodeId); }
    public boolean isTrusted(String nodeId) { return trusted.containsKey(nodeId); }
    public TrustedChimeraNode get(String nodeId) { return trusted.get(nodeId); }
    public int size() { return trusted.size(); }

    public void trust(String nodeId, PublicKey key) { trust(new TrustedChimeraNode(nodeId, key)); }
}
