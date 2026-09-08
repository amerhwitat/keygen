package org.chimera.koronos;

import org.chimera.cognition.KoronosRnn128;
import org.chimera.cognition.Vector128D;
import org.chimera.isa.ChimeraCpu;
import org.chimera.isa.Instruction;

import java.util.concurrent.StructuredTaskScope;

/** Java supervisory model of the Koronos kernel; hardware operations remain service abstractions. */
public final class KoronosKernel implements AutoCloseable {
    private final ChimeraCpu cpu=new ChimeraCpu();
    private final KoronosRnn128 cognition=new KoronosRnn128(1e-4,0x128D);
    private volatile boolean running;

    public void boot(){running=true;}
    public boolean running(){return running;}
    public Vector128D observe(Vector128D telemetry){if(!running)throw new IllegalStateException("Koronos is not running");return cognition.step(telemetry);}
    public double learn(Vector128D input,Vector128D target){if(!running)throw new IllegalStateException("Koronos is not running");return cognition.adapt(input,target);}
    public void execute(Instruction instruction,boolean privileged){if(!running)throw new IllegalStateException("Koronos is not running");cpu.execute(instruction,privileged);}

    /** Parallel observation hook using Java 25 structured concurrency APIs when enabled by the deployment. */
    public <T> T withParallelScope(java.util.concurrent.Callable<T> task) throws Exception {
        try(var scope=new StructuredTaskScope.ShutdownOnFailure()){var sub=scope.fork(task);scope.join();scope.throwIfFailed();return sub.get();}
    }
    @Override public void close(){running=false;}
}
