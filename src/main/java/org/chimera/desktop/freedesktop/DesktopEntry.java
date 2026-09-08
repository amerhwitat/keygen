package org.chimera.desktop.freedesktop;

import java.util.List;

public record DesktopEntry(String type, String name, String genericName, String exec, String icon,
                           List<String> categories, List<String> onlyShowIn, List<String> notShowIn,
                           boolean dbusActivatable) {
    public DesktopEntry {
        if (type == null || name == null || exec == null) throw new IllegalArgumentException("Type, Name and Exec are required");
        categories = List.copyOf(categories == null ? List.of() : categories);
        onlyShowIn = List.copyOf(onlyShowIn == null ? List.of() : onlyShowIn);
        notShowIn = List.copyOf(notShowIn == null ? List.of() : notShowIn);
    }
}
