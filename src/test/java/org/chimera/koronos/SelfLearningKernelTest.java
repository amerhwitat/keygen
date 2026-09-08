package org.chimera.koronos;

import org.chimera.cognition.Vector128D;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SelfLearningKernelTest {
    @Test
    void persistsObservationsAndLearnedStateAcrossKernelInstances(@TempDir Path dataDir) throws Exception {
        try (KoronosKernel first = new KoronosKernel(dataDir)) {
            first.boot();
            var input = new Vector128D();
            input.copy()[0] = 0.75;
            var target = new Vector128D();
            target.copy()[0] = 0.25;
            first.observe(input);
            first.learn(input, target);
            assertEquals(1, first.observationCount());
            assertTrue(first.snapshotCount() >= 1);
        }

        try (KoronosKernel second = new KoronosKernel(dataDir)) {
            second.boot();
            assertEquals(1, second.observationCount());
            assertTrue(second.snapshotCount() >= 1);
            assertNotNull(second.lastLearningLoss());
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
