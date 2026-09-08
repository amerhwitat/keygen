package org.chimera.desktop;

public final class DesktopSessionManager {
    private final DesktopStartupMenu menu;

    public DesktopSessionManager(DesktopStartupMenu menu) { this.menu = menu; }

    public DesktopLaunchPlan prepare(String profileId) { return menu.select(profileId); }

    public DesktopLaunchPlan defaultPlan() {
        return menu.entries().stream()
                .filter(DesktopStartupMenu.Entry::defaultProfile)
                .filter(DesktopStartupMenu.Entry::available)
                .findFirst()
                .map(DesktopStartupMenu.Entry::id)
                .map(menu::select)
                .orElseGet(() -> selectRecovery("safe-minimal", "headless"));
    }

    private DesktopLaunchPlan selectRecovery(String primary, String fallback) {
        try {
            return menu.select(primary);
        } catch (DesktopSelectionException ignored) {
            return menu.select(fallback);
        }
    }
}
