package org.chimera.network;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** File-backed trust registry containing public keys only. */
public final class PersistentChimeraTrustStore {
    private static final Pattern RECORD = Pattern.compile("\\{\\s*\\\"id\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"\\s*,\\s*\\\"key\\\"\\s*:\\s*\\\"([^\\\"]+)\\\"\\s*\\}");
    private final Path file;
    private final Map<String, TrustedChimeraNode> trusted = new ConcurrentHashMap<>();

    public PersistentChimeraTrustStore(Path file) {
        this.file = file.toAbsolutePath().normalize();
        load();
    }

    public synchronized void trust(TrustedChimeraNode node) {
        trusted.put(node.nodeId(), node);
        persist();
    }

    public synchronized void trust(String nodeId, PublicKey key) {
        trust(new TrustedChimeraNode(nodeId, key));
    }

    public synchronized void revoke(String nodeId) {
        trusted.remove(nodeId);
        persist();
    }

    public boolean isTrusted(String nodeId) { return trusted.containsKey(nodeId); }
    public TrustedChimeraNode get(String nodeId) { return trusted.get(nodeId); }
    public int size() { return trusted.size(); }
    public Path file() { return file; }

    private void load() {
        if (!Files.exists(file)) return;
        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            Matcher matcher = RECORD.matcher(json);
            while (matcher.find()) trusted.put(matcher.group(1), new TrustedChimeraNode(matcher.group(1), decode(matcher.group(2))));
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load Chimera trust store", e);
        }
    }

    private PublicKey decode(String encoded) throws Exception {
        return KeyFactory.getInstance("Ed25519").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(encoded)));
    }

    private void persist() {
        try {
            Path parent = file.getParent();
            if (parent != null) Files.createDirectories(parent);
            Path temp = file.resolveSibling(file.getFileName() + ".tmp");
            var entries = new LinkedHashMap<>(trusted);
            StringBuilder json = new StringBuilder("{\n  \"nodes\": [\n");
            int i = 0;
            for (TrustedChimeraNode node : entries.values()) {
                if (i++ > 0) json.append(",\n");
                json.append("    {\"id\":\"").append(escape(node.nodeId())).append("\",\"key\":\"")
                    .append(node.encodedPublicKey()).append("\"}");
            }
            json.append("\n  ]\n}\n");
            Files.writeString(temp, json, StandardCharsets.UTF_8);
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to persist Chimera trust store", e);
        }
    }

    private static String escape(String value) { return value.replace("\\", "\\\\").replace("\"", "\\\""); }
}
