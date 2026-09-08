package org.chimera.desktop;

import java.io.IOException;
import java.util.ArrayList;

public final class ProcessDesktopAdapter implements NativeDesktopAdapter {
    @Override public Process launch(DesktopLaunchPlan plan) throws IOException {
        var command = new ArrayList<>(plan.command());
        var builder = new ProcessBuilder(command);
        if (!plan.environment().isEmpty()) builder.environment().putAll(plan.environment());
        return builder.start();
    }
}
