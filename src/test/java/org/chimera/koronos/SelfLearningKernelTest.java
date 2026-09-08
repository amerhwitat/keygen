package org.chimera.koronos;

import org.chimera.cognition.Vector128D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SelfLearningKernelTest {
    @Test
    void observationAutomaticallyLearnsAndPersistsAcrossKernelInstances(@TempDir Path dataDir) throws Exception {
        Vector128D learnedState;
        try (KoronosKernel first = new KoronosKernel(dataDir)) {
            first.boot();
            var input = new Vector128D();
            input.set(0, 0.75);
            first.observe(input);
            learnedState = first.learnedState();
            assertEquals(1, first.observationCount());
            assertEquals(1, first.learningCount());
            assertTrue(first.snapshotCount() >= 1);
            assertNotNull(first.lastLearningLoss());
        }

        try (KoronosKernel second = new KoronosKernel(dataDir)) {
            second.boot();
            assertEquals(1, second.observationCount());
            assertEquals(1, second.learningCount());
            assertTrue(second.snapshotCount() >= 1);
            assertNotNull(second.lastLearningLoss());
            assertEquals(learnedState, second.learnedState());
        }
    }

    @Test
    void explicitLearningCanUseAnExternalTarget(@TempDir Path dataDir) throws Exception {
        try (KoronosKernel kernel = new KoronosKernel(dataDir)) {
            kernel.boot();
            var input = new Vector128D();
            input.set(0, 0.75);
            var target = new Vector128D();
            target.set(0, 0.25);
            assertTrue(Double.isFinite(kernel.learn(input, target)));
            assertEquals(1, kernel.learningCount());
        }
    }

    @Test
    void defaultDataDirectoryIsCimeraData() {
        assertEquals(Path.of("/var/Cimera/Data"), KoronosKernel.DEFAULT_DATA_DIRECTORY);
    }

    @Test
    void learningDoesNotRunBeforeBoot(@TempDir Path dataDir) throws Exception {
        try (KoronosKernel kernel = new KoronosKernel(dataDir)) {
            assertThrows(IllegalStateException.class, () -> kernel.observe(new Vector128D()));
            assertThrows(IllegalStateException.class, () -> kernel.learn(new Vector128D(), new Vector128D()));
        }
    }
}
