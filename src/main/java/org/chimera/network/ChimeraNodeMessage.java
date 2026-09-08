package org.chimera.network;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

/** Signed, replay-bounded application message for trusted Chimera nodes. */
public record ChimeraNodeMessage(String senderId, String messageId, long createdAtEpochSecond,
                                 String topic, byte[] payload, byte[] signature) {
    public ChimeraNodeMessage {
        Objects.requireNonNull(senderId); Objects.requireNonNull(messageId);
        Objects.requireNonNull(topic); Objects.requireNonNull(payload); Objects.requireNonNull(signature);
        payload = payload.clone(); signature = signature.clone();
        if (senderId.isBlank() || messageId.isBlank() || topic.isBlank()) throw new IllegalArgumentException("message identity fields must not be blank");
    }

    public static ChimeraNodeMessage create(TrustedChimeraNode localIdentity, java.security.KeyPair keyPair,
                                            String messageId, String topic, byte[] payload) {
        long now = Instant.now().getEpochSecond();
        byte[] body = canonical(messageId, now, topic, payload);
        return new ChimeraNodeMessage(localIdentity.nodeId(), messageId, now, topic, payload,
            TrustedChimeraNode.sign(keyPair, localIdentity.nodeId(), body));
    }

    public boolean verify(ChimeraTrustStore trustStore, long maxAgeSeconds) {
        if (!trustStore.isTrusted(senderId) || maxAgeSeconds < 0) return false;
        long age = Math.abs(Instant.now().getEpochSecond() - createdAtEpochSecond);
        if (age > maxAgeSeconds) return false;
        TrustedChimeraNode node = trustStore.get(senderId);
        return node != null && node.verify(canonical(messageId, createdAtEpochSecond, topic, payload), signature);
    }

    public String encodedPayload() { return Base64.getEncoder().encodeToString(payload); }
    public String encodedSignature() { return Base64.getEncoder().encodeToString(signature); }

    private static byte[] canonical(String id, long time, String topic, byte[] payload) {
        byte[] idBytes = id.getBytes(StandardCharsets.UTF_8);
        byte[] topicBytes = topic.getBytes(StandardCharsets.UTF_8);
        byte[] digest;
        try { digest = MessageDigest.getInstance("SHA-256").digest(payload); }
        catch (Exception e) { throw new IllegalStateException(e); }
        ByteBuffer buffer = ByteBuffer.allocate(4 + idBytes.length + 8 + 4 + topicBytes.length + digest.length);
        buffer.putInt(idBytes.length).put(idBytes).putLong(time).putInt(topicBytes.length).put(topicBytes).put(digest);
        return buffer.array();
    }
}
