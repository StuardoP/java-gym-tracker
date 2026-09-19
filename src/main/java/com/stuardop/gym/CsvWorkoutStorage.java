package com.stuardop.gym;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class CsvWorkoutStorage implements WorkoutStorage {
    private final Path path;

    public CsvWorkoutStorage(Path path) {
        this.path = path;
    }

    @Override
    public List<WorkoutSet> load() throws IOException {
        if (!Files.exists(path)) return new ArrayList<>();
        List<WorkoutSet> result = new ArrayList<>();
        for (String line : Files.readAllLines(path)) {
            if (line.isBlank() || line.startsWith("exercise,")) continue;
            String[] values = line.split(",", -1);
            result.add(new WorkoutSet(values[0], Double.parseDouble(values[1]),
                    Integer.parseInt(values[2]), LocalDate.parse(values[3])));
        }
        return result;
    }

    @Override
    public void save(List<WorkoutSet> sets) throws IOException {
        if (path.getParent() != null) Files.createDirectories(path.getParent());
        List<String> lines = new ArrayList<>();
        lines.add("exercise,weightKg,repetitions,date");
        for (WorkoutSet set : sets) {
            lines.add(String.join(",", sanitize(set.exercise()), Double.toString(set.weightKg()),
                    Integer.toString(set.repetitions()), set.date().toString()));
        }
        Files.write(path, lines);
    }

    private String sanitize(String value) {
        return value.replace(",", " ").replace("\n", " ");
    }
}

