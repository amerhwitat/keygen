package org.chimera.koronos;

import org.chimera.cognition.KoronosRnn128;
import org.chimera.cognition.Vector128D;
import org.chimera.isa.ChimeraCpu;
import org.chimera.isa.Instruction;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java supervisory model of the Koronos kernel with persistent, bounded self-learning.
 * Hardware operations remain service abstractions and the existing ISA/ABI is unchanged.
 */
public final class KoronosKernel implements AutoCloseable {
    public static final Path DEFAULT_DATA_DIRECTORY = Path.of("/var/Cimera/Data");
    private static final double LEARNING_RATE = 1e-4;
    private static final long MODEL_SEED = 0x128D;

    private final ChimeraCpu cpu=new ChimeraCpu();
    private final KoronosRnn128 cognition=new KoronosRnn128(LEARNING_RATE,MODEL_SEED);
    private final KernelDataStore dataStore;
    private final ExecutorService virtualThreads=Executors.newVirtualThreadPerTaskExecutor();
    private volatile boolean running;

    public KoronosKernel() { this(DEFAULT_DATA_DIRECTORY); }

    public KoronosKernel(Path dataDirectory) {
        if (dataDirectory == null) throw new IllegalArgumentException("dataDirectory must not be null");
        dataStore = new H2KernelDataStore(dataDirectory);
        var model = dataStore.latestModel();
        if (model != null) cognition.restore(model);
    }

    public void boot(){running=true;}
    public boolean running(){return running;}

    /** Observe telemetry, persist the observation, and checkpoint the learned recurrent state. */
    public synchronized Vector128D observe(Vector128D telemetry){
        requireRunning();
        if (telemetry == null) throw new IllegalArgumentException("telemetry must not be null");
        var state=cognition.step(telemetry);
        dataStore.recordObservation(telemetry);
        dataStore.saveModel(cognition.snapshot());
        return state;
    }

    /** Perform one bounded self-supervised update and persist both the learning event and model. */
    public synchronized double learn(Vector128D input,Vector128D target){
        requireRunning();
        if (input == null || target == null) throw new IllegalArgumentException("learning vectors must not be null");
        double loss=cognition.adapt(input,target);
        dataStore.recordLearning(loss);
        dataStore.saveModel(cognition.snapshot());
        return loss;
    }

    public long observationCount(){return dataStore.observationCount();}
    public long snapshotCount(){return dataStore.snapshotCount();}
    public Double lastLearningLoss(){return dataStore.lastLearningLoss();}

    public void execute(Instruction instruction,boolean privileged){requireRunning();cpu.execute(instruction,privileged);}

    /** Stable Java 25 virtual-thread task hook; hardware scheduling remains abstracted. */
    public <T> T withParallelTask(Callable<T> task) throws Exception{return virtualThreads.submit(task).get();}

    private void requireRunning(){if(!running)throw new IllegalStateException("Koronos is not running");}

    @Override public void close(){
        running=false;
        virtualThreads.close();
        dataStore.close();
    }
}
