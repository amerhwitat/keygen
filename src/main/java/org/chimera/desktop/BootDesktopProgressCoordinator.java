package org.chimera.desktop;

import java.util.List;
import java.util.Objects;

/** Coordinates the shared Spitfire boot progress with the graphical desktop loading pass. */
public final class BootDesktopProgressCoordinator {
    private static final List<String> STANDARD_BOOT_STAGES = List.of(
            "firmware / platform discovery",
            "memory and register initialization",
            "ISA/runtime initialization",
            "security and trust initialization",
            "storage/filesystem discovery",
            "Koronos kernel initialization",
            "network/runtime services",
            "desktop session preparation");

    private final SpitfireBootProgress bootProgress;
    private final DesktopLoadingProgress desktopProgress;
    private boolean desktopLoadingStarted;

    public BootDesktopProgressCoordinator() {
        this(new SpitfireBootProgress(STANDARD_BOOT_STAGES), new DesktopLoadingProgress());
    }

    public BootDesktopProgressCoordinator(SpitfireBootProgress bootProgress,
                                          DesktopLoadingProgress desktopProgress) {
        this.bootProgress = Objects.requireNonNull(bootProgress);
        this.desktopProgress = Objects.requireNonNull(desktopProgress);
        this.desktopLoadingStarted = bootProgress.complete();
    }

    public void completeBootStage(String stage) {
        bootProgress.completeStage(stage);
        desktopLoadingStarted = bootProgress.complete();
    }

    public void completeAllBootStages() {
        bootProgress.stages().forEach(bootProgress::completeStage);
        desktopLoadingStarted = true;
    }

    public int bootPercent() {
        return bootProgress.percent();
    }

    public boolean bootComplete() {
        return bootProgress.complete();
    }

    public boolean desktopLoadingStarted() {
        return desktopLoadingStarted;
    }

    public void setDesktopPercent(int percent) {
        if (!desktopLoadingStarted) {
            throw new IllegalStateException("desktop loading cannot start before Spitfire boot completes");
        }
        desktopProgress.setPercent(percent);
    }

    public int desktopPercent() {
        return desktopProgress.percent();
    }

    public int desktopSweepDegrees() {
        return desktopProgress.sweepDegrees();
    }

    public boolean desktopComplete() {
        return desktopProgress.complete();
    }

    public boolean startupComplete() {
        return bootProgress.complete() && desktopProgress.complete();
    }
}
