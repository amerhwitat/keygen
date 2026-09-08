package org.chimera.cognition;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/** Small deterministic codec for validated floating-point model snapshots. */
final class ModelSnapshotCodec {
    private ModelSnapshotCodec() {}

    static byte[] pack(double[] values) {
        var buffer = ByteBuffer.allocate(4 + values.length * Double.BYTES).order(ByteOrder.BIG_ENDIAN);
        buffer.putInt(values.length);
        for (double value : values) buffer.putDouble(value);
        return buffer.array();
    }

    static double[] unpack(byte[] bytes) {
        if (bytes == null || bytes.length < Integer.BYTES) throw new IllegalArgumentException("Missing model snapshot");
        var buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
        int length = buffer.getInt();
        if (length < 0 || length > 1_000_000 || buffer.remaining() != length * Double.BYTES) {
            throw new IllegalArgumentException("Invalid model snapshot");
        }
        var values = new double[length];
        for (int i = 0; i < length; i++) values[i] = buffer.getDouble();
        return values;
    }
}
