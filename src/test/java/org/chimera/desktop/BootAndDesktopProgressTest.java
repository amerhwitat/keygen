package org.chimera.desktop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BootAndDesktopProgressTest {
    @Test
    void spitfireBootProgressIsBoundedAndReportsCompletion() {
        var progress = new SpitfireBootProgress(List.of("firmware", "memory", "kernel", "desktop"));
        assertEquals(0, progress.percent());
        assertFalse(progress.complete());

        progress.completeStage("firmware");
        assertEquals(25, progress.percent());
        progress.completeStage("memory");
        progress.completeStage("kernel");
        progress.completeStage("desktop");

        assertEquals(100, progress.percent());
        assertTrue(progress.complete());
        assertEquals(100, progress.percent());
    }

    @Test
    void desktopLoadingProgressProvidesWindowsLikeCircularGeometry() {
        var progress = new DesktopLoadingProgress();
        progress.setPercent(37);

        assertEquals(37, progress.percent());
        assertEquals(133, progress.sweepDegrees());
        assertEquals(100, progress.setPercent(150));
        assertEquals(0, progress.setPercent(-10));
    }

    @Test
    void progressCanBeUpdatedByStage() {
        var progress = new SpitfireBootProgress(List.of("firmware", "memory"));
        assertEquals(50, progress.completeStage("firmware"));
        assertEquals(50, progress.completeStage("firmware"));
        assertEquals(100, progress.completeStage("memory"));
        assertThrows(IllegalArgumentException.class, () -> progress.completeStage("unknown"));
    }
}
