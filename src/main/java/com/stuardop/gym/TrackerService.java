package com.stuardop.gym;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public final class TrackerService {
    private final WorkoutStorage storage;
    private final List<WorkoutSet> sets;

    public TrackerService(WorkoutStorage storage) throws IOException {
        this.storage = storage;
        this.sets = storage.load();
    }

    public void add(WorkoutSet set) throws IOException {
        sets.add(set);
        storage.save(List.copyOf(sets));
    }

    public List<WorkoutSet> history(String exercise) {
        return sets.stream()
                .filter(set -> set.exercise().equalsIgnoreCase(exercise.trim()))
                .sorted(Comparator.comparing(WorkoutSet::date).reversed())
                .toList();
    }

    public double totalVolume() {
        return sets.stream().mapToDouble(WorkoutSet::volume).sum();
    }

    public WorkoutSet personalRecord(String exercise) {
        return history(exercise).stream()
                .max(Comparator.comparingDouble(WorkoutSet::estimatedOneRepMax))
                .orElse(null);
    }

    public List<WorkoutSet> all() {
        return List.copyOf(sets);
    }
}

