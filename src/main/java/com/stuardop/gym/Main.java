package com.stuardop.gym;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Scanner;

public final class Main {
    private Main() {}

    public static void main(String[] args) throws Exception {
        TrackerService tracker = new TrackerService(new CsvWorkoutStorage(Path.of("data", "workouts.csv")));
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                System.out.println("\nGYM TRACKER\n1. Add set\n2. View history\n3. View statistics\n0. Exit");
                switch (input.nextLine().trim()) {
                    case "1" -> addSet(input, tracker);
                    case "2" -> showHistory(input, tracker);
                    case "3" -> showStatistics(input, tracker);
                    case "0" -> running = false;
                    default -> System.out.println("Select a valid option.");
                }
            }
        }
    }

    private static void addSet(Scanner input, TrackerService tracker) {
        try {
            System.out.print("Exercise: "); String exercise = input.nextLine();
            System.out.print("Weight (kg): "); double weight = Double.parseDouble(input.nextLine());
            System.out.print("Repetitions: "); int reps = Integer.parseInt(input.nextLine());
            tracker.add(new WorkoutSet(exercise, weight, reps, LocalDate.now()));
            System.out.println("Set saved.");
        } catch (Exception exception) {
            System.out.println("Could not save: " + exception.getMessage());
        }
    }

    private static void showHistory(Scanner input, TrackerService tracker) {
        System.out.print("Exercise: ");
        var history = tracker.history(input.nextLine());
        if (history.isEmpty()) System.out.println("No records found.");
        history.forEach(set -> System.out.printf("%s — %.1f kg x %d (volume %.1f)%n",
                set.date(), set.weightKg(), set.repetitions(), set.volume()));
    }

    private static void showStatistics(Scanner input, TrackerService tracker) {
        System.out.printf("Total volume: %.1f kg%n", tracker.totalVolume());
        System.out.print("Exercise for personal record: ");
        WorkoutSet record = tracker.personalRecord(input.nextLine());
        if (record == null) System.out.println("No records found.");
        else System.out.printf("Estimated 1RM: %.1f kg (%s)%n", record.estimatedOneRepMax(), record.date());
    }
}

