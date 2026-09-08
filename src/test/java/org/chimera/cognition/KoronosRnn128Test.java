package org.chimera.cognition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KoronosRnn128Test {
    @Test
    void sameSeedProducesSameInitialState() {
        var a = new KoronosRnn128(0.01, 42L);
        var b = new KoronosRnn128(0.01, 42L);
        var input = new Vector128D();
        input.set(0, 1.0);
        assertArrayEquals(a.step(input).copy(), b.step(input).copy(), 0.0);
    }
}
