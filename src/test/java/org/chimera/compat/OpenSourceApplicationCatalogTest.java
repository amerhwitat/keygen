package org.chimera.compat;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class OpenSourceApplicationCatalogTest {
    @Test
    void catalogCoversCoreDesktopProductivityAndMultimedia() {
        var ids = OpenSourceApplicationCatalog.applications().stream()
                .map(OpenSourceApplicationCatalog.Application::id)
                .toList();
        assertTrue(ids.contains("libreoffice"));
        assertTrue(ids.contains("gimp"));
        assertTrue(ids.contains("inkscape"));
        assertTrue(ids.contains("blender"));
        assertTrue(ids.contains("vlc"));
        assertTrue(ids.contains("obs-studio"));
        assertTrue(ids.contains("thunderbird"));
        assertTrue(ids.contains("keepassxc"));
    }

    @Test
    void catalogCoversInfrastructureDevelopmentAndInteroperability() {
        var ids = OpenSourceApplicationCatalog.applications().stream()
                .map(OpenSourceApplicationCatalog.Application::id)
                .toList();
        assertTrue(ids.contains("docker-engine"));
        assertTrue(ids.contains("podman"));
        assertTrue(ids.contains("qemu"));
        assertTrue(ids.contains("git"));
        assertTrue(ids.contains("openssh"));
        assertTrue(ids.contains("rclone"));
        assertTrue(ids.contains("syncthing"));
        assertTrue(ids.contains("wireshark"));
    }

    @Test
    void catalogCoversDesktopEcosystemAndRemoteAdministration() {
        var ids = OpenSourceApplicationCatalog.applications().stream()
                .map(OpenSourceApplicationCatalog.Application::id)
                .toList();
        assertTrue(ids.contains("kde-connect"));
        assertTrue(ids.contains("kde-discover"));
        assertTrue(ids.contains("kde-smb4k"));
        assertTrue(ids.contains("gnome-connections"));
        assertTrue(ids.contains("gnome-disks"));
        assertTrue(ids.contains("gnome-software"));
    }

    @Test
    void catalogHasUniqueIdsAndExplicitDelivery() {
        var apps = OpenSourceApplicationCatalog.applications();
        var ids = apps.stream().map(OpenSourceApplicationCatalog.Application::id).toList();
        assertEquals(ids.size(), new HashSet<>(ids).size());
        assertTrue(apps.stream().allMatch(a -> a.linux() || a.windows()));
        assertTrue(apps.stream().allMatch(a -> !a.delivery().isEmpty()));
        assertTrue(apps.stream().allMatch(a -> !a.licenseFamily().isBlank()));
    }

    @Test
    void platformDeliveryIsExplicit() {
        var apps = OpenSourceApplicationCatalog.applications();
        assertTrue(apps.stream().allMatch(a -> a.linux() || a.windows()));
        assertTrue(apps.stream().anyMatch(a -> a.id().equals("qemu") && a.linux()));
        assertTrue(apps.stream().anyMatch(a -> a.id().equals("7zip") && a.windows() && a.linux()));
    }
}
