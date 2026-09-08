package org.chimera.desktop;

import java.util.*;

/**
 * Deterministic bootloader progress model for the Chimera "Spitfire" boot path.
 * It intentionally contains no rendering code so firmware/CLI/GUI front ends
 * can consume the same state.
 */
public final class SpitfireBootProgress {
    private final List<String> stages;
    private final Set<String> completed = new LinkedHashSet<>();

    public SpitfireBootProgress(List<String> stages) {
        Objects.requireNonNull(stages, "stages");
        if (stages.isEmpty() || stages.stream().anyMatch(s -> s == null || s.isBlank())) {
            throw new IllegalArgumentException("boot stages must be non-empty");
        }
        this.stages = List.copyOf(stages);
    }

    public synchronized int completeStage(String stage) {
        if (!stages.contains(stage)) throw new IllegalArgumentException("unknown boot stage: " + stage);
        completed.add(stage);
        return percent();
    }

    public synchronized int percent() {
        return (int) Math.round(completed.size() * 100.0 / stages.size());
    }

    public synchronized boolean complete() {
        return completed.size() == stages.size();
    }

    public List<String> stages() {
        return stages;
    }

    public synchronized List<String> completedStages() {
        return List.copyOf(completed);
    }
}
