package org.chimera.desktop;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record DesktopProfile(
        String id,
        String displayName,
        DesktopDistribution distribution,
        String desktopEnvironment,
        DesktopSessionType sessionType,
        List<List<String>> commandCandidates,
        Map<String, String> environment,
        Set<DesktopCapability> capabilities,
        boolean defaultProfile,
        boolean recoveryProfile) {
    public DesktopProfile {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("profile id must not be blank");
        if (displayName == null || displayName.isBlank()) throw new IllegalArgumentException("display name must not be blank");
        if (distribution == null || sessionType == null) throw new IllegalArgumentException("distribution/session type required");
        commandCandidates = commandCandidates == null ? List.of() : commandCandidates.stream().map(List::copyOf).toList();
        environment = environment == null ? Map.of() : Map.copyOf(environment);
        capabilities = capabilities == null ? Set.of() : Set.copyOf(capabilities);
    }
}
