package org.chimera.desktop;

import org.chimera.desktop.freedesktop.DesktopEntry;
import org.chimera.desktop.freedesktop.DesktopEntryCodec;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DesktopRuntimeTest {
    @Test
    void baselineRegistryContainsRequestedFamilies() {
        var registry = DesktopProfileRegistry.defaults();
        assertTrue(registry.find("aurora").isPresent());
        assertTrue(registry.find("fedora-gnome").isPresent());
        assertTrue(registry.find("ubuntu-gnome").isPresent());
        assertTrue(registry.find("debian-gnome").isPresent());
        assertTrue(registry.find("kde-plasma").isPresent());
        assertTrue(registry.find("xfce").isPresent());
        assertTrue(registry.find("cinnamon").isPresent());
        assertTrue(registry.find("mate").isPresent());
        assertTrue(registry.find("lxqt").isPresent());
        assertTrue(registry.find("gnome-flashback").isPresent());
        assertTrue(registry.find("safe-minimal").isPresent());
        assertTrue(registry.find("headless").isPresent());
    }

    @Test
    void unavailableProfileCannotBeSelected() {
        var detector = new DesktopCapabilityDetector((environment, executable) -> false);
        var menu = new DesktopStartupMenu(DesktopProfileRegistry.defaults(), detector.detect(Map.of()),
                candidate -> detector.launcherAvailable(candidate, Map.of()));
        assertThrows(DesktopSelectionException.class, () -> menu.select("fedora-gnome"));
    }

    @Test
    void launcherAvailabilityControlsProfileSelection() {
        var capabilities = Set.of(DesktopCapability.WAYLAND, DesktopCapability.X11);
        var menu = new DesktopStartupMenu(DesktopProfileRegistry.defaults(), capabilities,
                candidate -> !candidate.isEmpty() && "definitely-installed".equals(candidate.get(0)));
        assertThrows(DesktopSelectionException.class, () -> menu.select("fedora-gnome"));
        assertThrows(DesktopSelectionException.class, () -> menu.select("safe-minimal"));
    }

    @Test
    void defaultSelectionFallsBackWhenAuroraLauncherIsUnavailable() {
        var capabilities = Set.of(DesktopCapability.WAYLAND, DesktopCapability.X11);
        var menu = new DesktopStartupMenu(DesktopProfileRegistry.defaults(), capabilities,
                candidate -> !candidate.isEmpty() && Set.of("xterm", "java").contains(candidate.get(0)));
        var manager = new DesktopSessionManager(menu);
        assertEquals("xterm", manager.defaultPlan().executable());
    }

    @Test
    void structuredLaunchPlanDoesNotContainShellConcatenation() {
        var plan = new DesktopLaunchPlan("/usr/bin/example", List.of("--session", "wayland"), Map.of());
        assertEquals("/usr/bin/example", plan.executable());
        assertEquals(List.of("--session", "wayland"), plan.arguments());
        assertFalse(plan.arguments().stream().anyMatch(a -> a.contains(";")));
    }

    @Test
    void desktopEntryRoundTripsSupportedFields() {
        var entry = new DesktopEntry("Application", "Koronos", "Kernel Runtime", "koronos", "koronos",
                List.of("System", "Utility"), List.of("GNOME", "KDE"), List.of(), false);
        var encoded = DesktopEntryCodec.encode(entry);
        assertEquals(entry, DesktopEntryCodec.parse(encoded));
    }
}
