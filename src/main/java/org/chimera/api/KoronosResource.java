package org.chimera.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.chimera.cognition.Vector128D;
import org.chimera.koronos.KoronosKernel;

@Path("/koronos")
@Produces(MediaType.APPLICATION_JSON)
public final class KoronosResource {
    private static final KoronosKernel KERNEL=new KoronosKernel();
    private static final AtomicReference<Vector128D> STATE=new AtomicReference<>(new Vector128D());
    static { KERNEL.boot(); }
    @GET @Path("/health") public Map<String,Object> health(){return Map.of("kernel","Koronos","running",KERNEL.running(),"dimensions",128,"registerBits",8192);}
    @GET @Path("/state") public Map<String,Object> state(){return Map.of("dimensions",128,"norm",STATE.get().norm());}
}
