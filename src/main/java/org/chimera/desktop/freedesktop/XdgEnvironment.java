package org.chimera.desktop.freedesktop;

import java.util.Map;

public record XdgEnvironment(String dataHome, String configHome, String cacheHome, String runtimeDir, String currentDesktop, String sessionType) {
    public static XdgEnvironment from(Map<String,String> env) {
        return new XdgEnvironment(env.getOrDefault("XDG_DATA_HOME", home(env, ".local/share")),
                env.getOrDefault("XDG_CONFIG_HOME", home(env, ".config")),
                env.getOrDefault("XDG_CACHE_HOME", home(env, ".cache")), env.getOrDefault("XDG_RUNTIME_DIR", ""),
                env.getOrDefault("XDG_CURRENT_DESKTOP", ""), env.getOrDefault("XDG_SESSION_TYPE", ""));
    }
    private static String home(Map<String,String> env, String suffix) { var home = env.getOrDefault("HOME", ""); return home.isBlank() ? suffix : home + "/" + suffix; }
}
