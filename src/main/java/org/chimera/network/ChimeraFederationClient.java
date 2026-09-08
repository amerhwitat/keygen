package org.chimera.network;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Base64;
import java.util.Objects;

/** Minimal signed-message client for trusted Chimera peers. */
public final class ChimeraFederationClient {
    private final HttpClient client;
    private final ChimeraTrustStore trustStore;

    public ChimeraFederationClient(ChimeraTrustStore trustStore) {
        this.client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
        this.trustStore = Objects.requireNonNull(trustStore);
    }

    public HttpResponse<String> send(URI endpoint, ChimeraNodeMessage message) throws Exception {
        Objects.requireNonNull(endpoint);
        Objects.requireNonNull(message);
        if (!trustStore.isTrusted(message.senderId())) throw new SecurityException("Sender is not trusted: " + message.senderId());
        boolean localLabHttp = "http".equalsIgnoreCase(endpoint.getScheme())
            && ("localhost".equalsIgnoreCase(endpoint.getHost()) || "127.0.0.1".equals(endpoint.getHost()) || "::1".equals(endpoint.getHost()));
        if (!"https".equalsIgnoreCase(endpoint.getScheme()) && !localLabHttp)
            throw new SecurityException("Federation transport requires HTTPS outside localhost lab endpoints");
        String json = "{\"senderId\":\"" + escape(message.senderId()) + "\",\"messageId\":\"" + escape(message.messageId())
            + "\",\"createdAt\":" + message.createdAtEpochSecond() + ",\"topic\":\"" + escape(message.topic())
            + "\",\"payload\":\"" + message.encodedPayload() + "\",\"signature\":\"" + message.encodedSignature() + "\"}";
        var request = HttpRequest.newBuilder(endpoint).timeout(Duration.ofSeconds(30))
            .header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
