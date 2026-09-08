package org.chimera.koronos;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class KoronosConcurrencyTest {
    @Test void cpuPoolIsBounded() throws Exception {
        try (var concurrency = new KoronosConcurrency(2)) {
            assertEquals(2, concurrency.cpuParallelism());
            assertEquals(42, concurrency.submitCpu(() -> 42).get(2, TimeUnit.SECONDS));
        }
    }

    @Test void ioUsesVirtualThreadExecutor() throws Exception {
        try (var concurrency = new KoronosConcurrency()) {
            assertEquals("io-result", concurrency.submitIo(() -> "io-result").get(2, TimeUnit.SECONDS));
        }
    }

    @Test void emptyProcessCommandIsRejected() {
        try (var concurrency = new KoronosConcurrency(1)) {
            assertThrows(IllegalArgumentException.class, () -> concurrency.startProcess(List.of()));
        }
    }
}
