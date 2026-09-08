package org.chimera.koronos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Java 25 concurrency boundary for Koronos. Virtual threads handle high fan-out I/O;
 * a bounded platform-thread pool handles CPU-oriented work; OS processes provide
 * isolation for external runtimes and services.
 */
public final class KoronosConcurrency implements AutoCloseable {
    private final ExecutorService virtualThreads = Executors.newVirtualThreadPerTaskExecutor();
    private final ExecutorService cpuWorkers;

    public KoronosConcurrency() { this(Math.max(1, Runtime.getRuntime().availableProcessors())); }

    public KoronosConcurrency(int cpuParallelism) {
        if (cpuParallelism < 1) throw new IllegalArgumentException("cpuParallelism must be >= 1");
        cpuWorkers = Executors.newFixedThreadPool(cpuParallelism);
    }

    public <T> Future<T> submitIo(Callable<T> task) { return virtualThreads.submit(Objects.requireNonNull(task)); }
    public <T> Future<T> submitCpu(Callable<T> task) { return cpuWorkers.submit(Objects.requireNonNull(task)); }

    /** Starts a native process without a shell. Callers own lifecycle and resource limits. */
    public Process startProcess(List<String> command) throws IOException {
        if (command == null || command.isEmpty()) throw new IllegalArgumentException("command must not be empty");
        List<String> safe = new ArrayList<>(command.size());
        for (String part : command) {
            if (part == null || part.isBlank()) throw new IllegalArgumentException("command arguments must not be blank");
            safe.add(part);
        }
        return new ProcessBuilder(safe).start();
    }

    public int cpuParallelism() { return ((java.util.concurrent.ThreadPoolExecutor) cpuWorkers).getMaximumPoolSize(); }

    @Override public void close() {
        virtualThreads.close();
        cpuWorkers.close();
    }
}
