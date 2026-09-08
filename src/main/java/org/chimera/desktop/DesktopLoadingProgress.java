package org.chimera.desktop;

/**
 * Shared state for the desktop loading screen. The sweep is suitable for a
 * Windows-like indeterminate/animated circular progress indicator while the
 * percentage remains available for deterministic startup reporting.
 */
public final class DesktopLoadingProgress {
    private int percent;

    public synchronized int setPercent(int value) {
        percent = clamp(value);
        return percent;
    }

    public synchronized int percent() {
        return percent;
    }

    /** Returns the clockwise arc size in degrees for a circular indicator. */
    public synchronized int sweepDegrees() {
        return (percent * 360) / 100;
    }

    public synchronized boolean complete() {
        return percent >= 100;
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }
}
