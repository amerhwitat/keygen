package org.chimera.desktop;

import java.util.Map;

public final class DesktopSessionManager {
    private final DesktopStartupMenu menu;
    public DesktopSessionManager(DesktopStartupMenu menu) { this.menu = menu; }
    public DesktopLaunchPlan prepare(String profileId) { return menu.select(profileId); }
    public DesktopLaunchPlan defaultPlan() {
        return menu.entries().stream().filter(DesktopStartupMenu.Entry::defaultProfile).findFirst()
                .map(DesktopStartupMenu.Entry::id).map(menu::select)
                .orElseGet(() -> menu.select("headless"));
    }
}
