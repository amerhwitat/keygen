package org.chimera.koronos;

import org.chimera.cognition.Vector128D;

/** Persistent storage contract for kernel observations and learned model snapshots. */
public interface KernelDataStore extends AutoCloseable {
    void recordObservation(Vector128D observation);
    void recordLearning(double loss);
    void saveModel(byte[] modelState);
    long observationCount();
    long learningCount();
    long snapshotCount();
    Double lastLearningLoss();
    byte[] latestModel();
    @Override void close();
}
