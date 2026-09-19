package com.stuardop.gym;

import java.time.LocalDate;

public record WorkoutSet(String exercise, double weightKg, int repetitions, LocalDate date) {
    public WorkoutSet {
        if (exercise == null || exercise.isBlank()) throw new IllegalArgumentException("Exercise is required");
        exercise = exercise.trim();
        if (weightKg <= 0 || weightKg > 1000) throw new IllegalArgumentException("Weight must be between 0 and 1000 kg");
        if (repetitions <= 0 || repetitions > 1000) throw new IllegalArgumentException("Repetitions must be between 1 and 1000");
        if (date == null) throw new IllegalArgumentException("Date is required");
    }

    public double volume() {
        return weightKg * repetitions;
    }

    public double estimatedOneRepMax() {
        return weightKg * (1.0 + repetitions / 30.0);
    }
}

