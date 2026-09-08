package org.chimera.desktop.freedesktop;

import java.util.*;

public final class DesktopEntryCodec {
    private DesktopEntryCodec() {}
    public static String encode(DesktopEntry e) {
        var b = new StringBuilder("[Desktop Entry]\n");
        line(b, "Type", e.type()); line(b, "Version", "1.5"); line(b, "Name", e.name());
        if (e.genericName() != null && !e.genericName().isBlank()) line(b, "GenericName", e.genericName());
        line(b, "Exec", e.exec());
        if (e.icon() != null && !e.icon().isBlank()) line(b, "Icon", e.icon());
        if (!e.categories().isEmpty()) line(b, "Categories", String.join(";", e.categories()) + ";");
        if (!e.onlyShowIn().isEmpty()) line(b, "OnlyShowIn", String.join(";", e.onlyShowIn()) + ";");
        if (!e.notShowIn().isEmpty()) line(b, "NotShowIn", String.join(";", e.notShowIn()) + ";");
        line(b, "DBusActivatable", Boolean.toString(e.dbusActivatable()));
        return b.toString();
    }
    public static DesktopEntry parse(String text) {
        var m = new LinkedHashMap<String,String>();
        for (String raw : text.split("\\R")) {
            var s = raw.trim(); if (s.isEmpty() || s.startsWith("#") || s.equals("[Desktop Entry]")) continue;
            int i = s.indexOf('='); if (i <= 0) continue;
            m.put(s.substring(0,i), s.substring(i+1));
        }
        return new DesktopEntry(m.get("Type"), m.get("Name"), m.get("GenericName"), m.get("Exec"), m.get("Icon"),
                split(m.get("Categories")), split(m.get("OnlyShowIn")), split(m.get("NotShowIn")),
                Boolean.parseBoolean(m.getOrDefault("DBusActivatable", "false")));
    }
    private static List<String> split(String value) { if (value == null || value.isBlank()) return List.of(); return Arrays.stream(value.split(";", -1)).filter(s -> !s.isBlank()).toList(); }
    private static void line(StringBuilder b, String k, String v) { b.append(k).append('=').append(v).append('\n'); }
}
