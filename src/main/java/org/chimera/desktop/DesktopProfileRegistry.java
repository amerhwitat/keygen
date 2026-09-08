package org.chimera.desktop;

import java.util.*;

public final class DesktopProfileRegistry {
    private final Map<String, DesktopProfile> profiles;

    public DesktopProfileRegistry(Collection<DesktopProfile> profiles) {
        var map = new LinkedHashMap<String, DesktopProfile>();
        for (var profile : profiles) {
            if (map.put(profile.id(), profile) != null) throw new IllegalArgumentException("duplicate profile: " + profile.id());
        }
        this.profiles = Collections.unmodifiableMap(map);
    }

    public Optional<DesktopProfile> find(String id) { return Optional.ofNullable(profiles.get(id)); }
    public List<DesktopProfile> profiles() { return List.copyOf(profiles.values()); }

    public static DesktopProfileRegistry defaults() {
        var w = Set.of(DesktopCapability.WAYLAND, DesktopCapability.DBUS, DesktopCapability.PORTALS, DesktopCapability.GPU, DesktopCapability.AUDIO);
        var x = Set.of(DesktopCapability.X11, DesktopCapability.DBUS, DesktopCapability.GPU, DesktopCapability.AUDIO);
        var list = new ArrayList<DesktopProfile>();
        list.add(new DesktopProfile("aurora", "Chimera Aurora", DesktopDistribution.AURORA, "Aurora", DesktopSessionType.WAYLAND,
                List.of(List.of("aurora-session"), List.of("chimera-aurora")), Map.of("XDG_CURRENT_DESKTOP", "Aurora"), w, true, false));
        list.add(new DesktopProfile("fedora-gnome", "Fedora GNOME", DesktopDistribution.FEDORA, "GNOME", DesktopSessionType.WAYLAND,
                List.of(List.of("gnome-session", "--session=gnome")), Map.of(), w, false, false));
        list.add(new DesktopProfile("ubuntu-gnome", "Ubuntu GNOME", DesktopDistribution.UBUNTU, "GNOME", DesktopSessionType.WAYLAND,
                List.of(List.of("gnome-session", "--session=ubuntu"), List.of("gnome-session", "--session=gnome")), Map.of(), w, false, false));
        list.add(new DesktopProfile("debian-gnome", "Debian GNOME", DesktopDistribution.DEBIAN, "GNOME", DesktopSessionType.WAYLAND,
                List.of(List.of("gnome-session", "--session=gnome")), Map.of(), w, false, false));
        list.add(new DesktopProfile("kde-plasma", "KDE Plasma", DesktopDistribution.GENERIC, "KDE Plasma", DesktopSessionType.WAYLAND,
                List.of(List.of("startplasma-wayland"), List.of("startplasma-x11")), Map.of(), Set.of(DesktopCapability.WAYLAND, DesktopCapability.X11, DesktopCapability.DBUS, DesktopCapability.GPU, DesktopCapability.AUDIO), false, false));
        list.add(new DesktopProfile("xfce", "Xfce", DesktopDistribution.GENERIC, "Xfce", DesktopSessionType.X11,
                List.of(List.of("startxfce4")), Map.of(), x, false, false));
        list.add(new DesktopProfile("cinnamon", "Cinnamon", DesktopDistribution.GENERIC, "Cinnamon", DesktopSessionType.X11,
                List.of(List.of("cinnamon-session")), Map.of(), x, false, false));
        list.add(new DesktopProfile("mate", "MATE", DesktopDistribution.GENERIC, "MATE", DesktopSessionType.X11,
                List.of(List.of("mate-session")), Map.of(), x, false, false));
        list.add(new DesktopProfile("lxqt", "LXQt", DesktopDistribution.GENERIC, "LXQt", DesktopSessionType.X11,
                List.of(List.of("startlxqt")), Map.of(), x, false, false));
        list.add(new DesktopProfile("gnome-flashback", "GNOME Flashback", DesktopDistribution.DEBIAN, "GNOME Flashback", DesktopSessionType.X11,
                List.of(List.of("gnome-session", "--session=gnome-flashback-metacity")), Map.of(), x, false, false));
        list.add(new DesktopProfile("safe-minimal", "Safe / Minimal", DesktopDistribution.GENERIC, "Minimal", DesktopSessionType.X11,
                List.of(List.of("xterm")), Map.of(), Set.of(DesktopCapability.X11), false, true));
        list.add(new DesktopProfile("headless", "Headless / Server", DesktopDistribution.GENERIC, "None", DesktopSessionType.HEADLESS,
                List.of(List.of("java", "-version")), Map.of(), Set.of(), false, true));
        return new DesktopProfileRegistry(list);
    }
}
