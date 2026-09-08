package org.chimera.desktop;

import java.util.*;

public final class DesktopCapabilityDetector {
    @FunctionalInterface public interface CommandProbe { boolean available(Map<String,String> environment, String executable); }
    private final CommandProbe probe;
    public DesktopCapabilityDetector() { this((env, executable) -> {
        var path = env.getOrDefault("PATH", System.getenv().getOrDefault("PATH", ""));
        return Arrays.stream(path.split(java.io.File.pathSeparator)).filter(s -> !s.isBlank())
                .map(java.nio.file.Path::of).map(p -> p.resolve(executable)).anyMatch(p -> java.nio.file.Files.isExecutable(p));
    }); }
    public DesktopCapabilityDetector(CommandProbe probe) { this.probe = Objects.requireNonNull(probe); }
    public Set<DesktopCapability> detect(Map<String,String> env) {
        var result = EnumSet.noneOf(DesktopCapability.class);
        if (env.containsKey("WAYLAND_DISPLAY") || "wayland".equalsIgnoreCase(env.get("XDG_SESSION_TYPE"))) result.add(DesktopCapability.WAYLAND);
        if (env.containsKey("DISPLAY") || "x11".equalsIgnoreCase(env.get("XDG_SESSION_TYPE"))) result.add(DesktopCapability.X11);
        if (env.containsKey("DBUS_SESSION_BUS_ADDRESS")) result.add(DesktopCapability.DBUS);
        var portals = env.getOrDefault("XDG_DESKTOP_PORTAL", "");
        if (!portals.isBlank() || probe.available(env, "xdg-desktop-portal")) result.add(DesktopCapability.PORTALS);
        if (probe.available(env, "pactl") || probe.available(env, "pipewire")) result.add(DesktopCapability.AUDIO);
        if (probe.available(env, "glxinfo") || probe.available(env, "vulkaninfo")) result.add(DesktopCapability.GPU);
        return Set.copyOf(result);
    }
}
