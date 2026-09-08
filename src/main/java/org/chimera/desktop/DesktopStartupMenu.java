package org.chimera.desktop;

import java.util.*;
import java.util.function.Predicate;

public final class DesktopStartupMenu {
    public record Entry(String id, String displayName, DesktopDistribution distribution, DesktopSessionType sessionType,
                        boolean available, boolean defaultProfile, boolean recoveryProfile) {}

    private final DesktopProfileRegistry registry;
    private final Set<DesktopCapability> capabilities;
    private final Predicate<List<String>> launcherAvailable;

    public DesktopStartupMenu(DesktopProfileRegistry registry, Set<DesktopCapability> capabilities) {
        this(registry, capabilities, command -> true);
    }

    public DesktopStartupMenu(DesktopProfileRegistry registry, Set<DesktopCapability> capabilities,
                              Predicate<List<String>> launcherAvailable) {
        this.registry = Objects.requireNonNull(registry);
        this.capabilities = Set.copyOf(capabilities);
        this.launcherAvailable = Objects.requireNonNull(launcherAvailable);
    }

    public List<Entry> entries() {
        return registry.profiles().stream().map(p -> new Entry(p.id(), p.displayName(), p.distribution(), p.sessionType(),
                available(p), p.defaultProfile(), p.recoveryProfile())).toList();
    }

    public DesktopLaunchPlan select(String id) {
        var p = registry.find(id).orElseThrow(() -> new DesktopSelectionException("unknown desktop profile: " + id));
        if (!p.recoveryProfile() && !available(p)) {
            throw new DesktopSelectionException("desktop profile unavailable: " + id);
        }
        var candidate = p.commandCandidates().stream()
                .filter(c -> !c.isEmpty())
                .filter(launcherAvailable)
                .findFirst()
                .orElseThrow(() -> new DesktopSelectionException("profile has no available launcher: " + id));
        var env = new HashMap<>(p.environment());
        return new DesktopLaunchPlan(candidate.get(0), candidate.subList(1, candidate.size()), env);
    }

    private boolean available(DesktopProfile p) {
        if (p.sessionType() == DesktopSessionType.HEADLESS || p.recoveryProfile()) return true;
        if (p.sessionType() == DesktopSessionType.WAYLAND && !capabilities.contains(DesktopCapability.WAYLAND)) return false;
        if (p.sessionType() == DesktopSessionType.X11 && !capabilities.contains(DesktopCapability.X11)) return false;
        return p.commandCandidates().stream().anyMatch(c -> !c.isEmpty() && launcherAvailable.test(c));
    }
}
