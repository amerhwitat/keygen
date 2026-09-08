package org.chimera.desktop;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DesktopProfileTest {
    @Test
    void profileRetainsImmutableMetadata() {
        DesktopProfile profile = new DesktopProfile("aurora", "Chimera Aurora", DesktopDistribution.AURORA,
                "Aurora", DesktopSessionType.WAYLAND, List.of(List.of("aurora-session")),
                Map.of("XDG_CURRENT_DESKTOP", "Aurora"), Set.of(DesktopCapability.WAYLAND), true, false);
        assertEquals("aurora", profile.id());
        assertEquals(DesktopSessionType.WAYLAND, profile.sessionType());
        assertThrows(UnsupportedOperationException.class, () -> profile.commandCandidates().add(List.of("bad")));
    }

    @Test
    void blankIdIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new DesktopProfile(" ", "Name", DesktopDistribution.GENERIC,
                "Desktop", DesktopSessionType.X11, List.of(), Map.of(), Set.of(), false, false));
    }
}
