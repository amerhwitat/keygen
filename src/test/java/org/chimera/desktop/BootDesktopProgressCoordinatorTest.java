package org.chimera.desktop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BootDesktopProgressCoordinatorTest {
    @Test
    void bootStageCompletionAdvancesSharedProgress() {
        var coordinator = new BootDesktopProgressCoordinator();

        assertEquals(0, coordinator.bootPercent());
        coordinator.completeBootStage("firmware / platform discovery");
        assertEquals(12, coordinator.bootPercent());
        coordinator.completeBootStage("memory and register initialization");
        assertEquals(25, coordinator.bootPercent());
    }

    @Test
    void desktopPreparationStartsOnlyAfterBootCompletes() {
        var coordinator = new BootDesktopProgressCoordinator();

        assertFalse(coordinator.desktopLoadingStarted());
        coordinator.completeAllBootStages();

        assertTrue(coordinator.desktopLoadingStarted());
        assertEquals(0, coordinator.desktopPercent());
        coordinator.setDesktopPercent(50);
        assertEquals(180, coordinator.desktopSweepDegrees());
    }

    @Test
    void desktopCompletionCompletesWholeStartupPass() {
        var coordinator = new BootDesktopProgressCoordinator();
        coordinator.completeAllBootStages();
        coordinator.setDesktopPercent(100);

        assertTrue(coordinator.bootComplete());
        assertTrue(coordinator.desktopComplete());
        assertTrue(coordinator.startupComplete());
        assertEquals(360, coordinator.desktopSweepDegrees());
    }

    @Test
    void desktopCannotAdvanceBeforeBootCompletes() {
        var coordinator = new BootDesktopProgressCoordinator();

        assertThrows(IllegalStateException.class, () -> coordinator.setDesktopPercent(10));
    }
}
