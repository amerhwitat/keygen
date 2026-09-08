package org.chimera.runtime;

import java.util.List;

/** Distro-aware package manager command builder. It never executes commands. */
public record LinuxPackageManager(String executable, List<String> installPrefix) {
    public LinuxPackageManager {
        if (executable == null || executable.isBlank()) throw new IllegalArgumentException("executable required");
        installPrefix = List.copyOf(installPrefix);
    }

    public static LinuxPackageManager forDistro(LinuxRuntimeCatalog.Distro distro) {
        return switch (distro) {
            case FEDORA, AURORA -> new LinuxPackageManager("dnf", List.of("dnf", "install", "-y"));
            case UBUNTU, DEBIAN, KALI -> new LinuxPackageManager("apt-get", List.of("apt-get", "install", "-y"));
        };
    }
}
