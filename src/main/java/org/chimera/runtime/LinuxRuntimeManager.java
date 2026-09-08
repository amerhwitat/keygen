package org.chimera.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Produces explicit package/service plans without executing them. */
public final class LinuxRuntimeManager {
    public record CommandPlan(List<String> command, String purpose) {
        public CommandPlan { command = List.copyOf(command); }
    }

    public enum ServiceAction { ENABLE, START, STOP, RESTART, STATUS }

    public CommandPlan installPlan(LinuxRuntimeCatalog.Distro distro, String runtimeId) {
        Objects.requireNonNull(distro);
        Objects.requireNonNull(runtimeId);
        var runtime = LinuxRuntimeCatalog.runtimes().stream()
            .filter(r -> r.id().equals(runtimeId)).findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown runtime: " + runtimeId));
        var packages = runtime.packages().get(distro);
        if (packages == null || packages.isEmpty()) throw new IllegalArgumentException("No package mapping for " + runtimeId + " on " + distro);
        var manager = LinuxPackageManager.forDistro(distro);
        var command = new ArrayList<>(manager.installPrefix());
        command.addAll(packages);
        return new CommandPlan(command, "Install " + runtime.displayName());
    }

    public CommandPlan servicePlan(LinuxRuntimeCatalog.Distro distro, String serviceId, ServiceAction action) {
        Objects.requireNonNull(distro);
        Objects.requireNonNull(serviceId);
        Objects.requireNonNull(action);
        if (serviceId.isBlank() || serviceId.indexOf('\0') >= 0 || serviceId.contains("/"))
            throw new IllegalArgumentException("Invalid service id");
        var command = List.of("systemctl", action.name().toLowerCase(), serviceId);
        return new CommandPlan(command, "systemd " + action.name().toLowerCase() + " for " + serviceId);
    }
}
