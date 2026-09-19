package com.stuardop.gym;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class TrackerServiceTest {
    private static final class MemoryStorage implements WorkoutStorage {
        private List<WorkoutSet> data = new ArrayList<>();
        public List<WorkoutSet> load() { return new ArrayList<>(data); }
        public void save(List<WorkoutSet> sets) { data = new ArrayList<>(sets); }
    }

    public static void main(String[] args) throws Exception {
        MemoryStorage storage = new MemoryStorage();
        TrackerService service = new TrackerService(storage);
        service.add(new WorkoutSet("Bench Press", 80, 8, LocalDate.of(2026, 9, 1)));
        service.add(new WorkoutSet("Bench Press", 90, 5, LocalDate.of(2026, 9, 8)));
        service.add(new WorkoutSet("Squat", 120, 5, LocalDate.of(2026, 9, 9)));

        assert service.all().size() == 3 : "All sets should be stored";
        assert service.history("bench press").size() == 2 : "History must ignore case";
        assert service.history("Bench Press").get(0).date().equals(LocalDate.of(2026, 9, 8));
        assert Math.abs(service.totalVolume() - 1690.0) < 0.001 : "Volume should be calculated";
        assert service.personalRecord("Bench Press").weightKg() == 90 : "Personal record should use estimated 1RM";

        boolean rejected = false;
        try { new WorkoutSet("", -1, 0, LocalDate.now()); }
        catch (IllegalArgumentException expected) { rejected = true; }
        assert rejected : "Invalid sets must be rejected";
        System.out.println("All Gym Tracker tests passed.");
    }
}

