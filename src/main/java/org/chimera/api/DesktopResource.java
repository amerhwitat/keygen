package org.chimera.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.chimera.desktop.*;
import java.util.*;

@Path("/desktop")
@Produces(MediaType.APPLICATION_JSON)
public final class DesktopResource {
    private final DesktopProfileRegistry registry = DesktopProfileRegistry.defaults();
    private final DesktopStartupMenu menu;
    public DesktopResource() {
        var env = System.getenv();
        var capabilities = new DesktopCapabilityDetector().detect(env);
        this.menu = new DesktopStartupMenu(registry, capabilities);
    }
    @GET @Path("/profiles") public List<DesktopStartupMenu.Entry> profiles() { return menu.entries(); }
    @GET @Path("/current") public Map<String,Object> current() {
        var env = System.getenv();
        return Map.of("currentDesktop", env.getOrDefault("XDG_CURRENT_DESKTOP", ""),
                "sessionType", env.getOrDefault("XDG_SESSION_TYPE", ""),
                "wayland", env.containsKey("WAYLAND_DISPLAY"), "x11", env.containsKey("DISPLAY"));
    }
    @GET @Path("/select/{id}") public DesktopLaunchPlan select(@PathParam("id") String id) {
        return menu.select(id);
    }
}
