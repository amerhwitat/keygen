package org.chimera.runtime;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinuxRuntimeManagerTest {
    private final LinuxRuntimeManager manager = new LinuxRuntimeManager();

    @Test void fedoraUsesDnf() {
        var plan = manager.installPlan(LinuxRuntimeCatalog.Distro.FEDORA, "python");
        assertEquals(List.of("dnf", "install", "-y", "python3", "python3-pip"), plan.command());
    }

    @Test void debianUsesApt() {
        var plan = manager.installPlan(LinuxRuntimeCatalog.Distro.DEBIAN, "bind9");
        assertEquals(List.of("apt-get", "install", "-y", "bind9", "bind9-utils"), plan.command());
    }

    @Test void unknownRuntimeIsRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> manager.installPlan(LinuxRuntimeCatalog.Distro.UBUNTU, "does-not-exist"));
    }

    @Test void serviceCommandsAreStructured() {
        var plan = manager.servicePlan(LinuxRuntimeCatalog.Distro.FEDORA, "nginx", LinuxRuntimeManager.ServiceAction.START);
        assertEquals(List.of("systemctl", "start", "nginx"), plan.command());
    }

    @Test void serviceInjectionCharactersAreRejected() {
        assertThrows(IllegalArgumentException.class,
            () -> manager.servicePlan(LinuxRuntimeCatalog.Distro.FEDORA, "nginx;rm", LinuxRuntimeManager.ServiceAction.START));
    }
}
