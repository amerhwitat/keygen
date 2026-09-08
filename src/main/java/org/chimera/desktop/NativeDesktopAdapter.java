package org.chimera.desktop;

public interface NativeDesktopAdapter {
    Process launch(DesktopLaunchPlan plan) throws java.io.IOException;
}
