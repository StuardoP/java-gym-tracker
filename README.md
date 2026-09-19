# Java Gym Tracker

A command-line strength training tracker written in Java 17. It records workout sets, persists them to CSV and calculates progress indicators such as personal records, total volume and exercise history.

## Features

- Record exercise, weight, repetitions and workout date
- Validate all user input
- Persist workout data between sessions
- Review exercise-specific history
- Calculate total training volume
- Identify estimated one-repetition maximums with the Epley formula
- Keep domain, service, storage and interface responsibilities separated
- Run dependency-free automated tests

## Architecture

```text
CLI (Main) → TrackerService → WorkoutStorage → CSV file
                    ↓
                WorkoutSet
```

## Run

```bash
javac -d target/classes $(find src/main/java -name '*.java')
java -cp target/classes com.stuardop.gym.Main
```

Or with Maven:

```bash
mvn package
java -jar target/java-gym-tracker-1.0.0.jar
```

## Tests

```bash
mkdir -p target/test-classes
javac -d target/test-classes $(find src/main/java src/test/java -name '*.java')
java -ea -cp target/test-classes com.stuardop.gym.TrackerServiceTest
```

## Skills demonstrated

Java 17, OOP, records, interfaces, streams, collections, validation, file persistence, layered architecture and automated testing.

## Author

**Herbert Stuardo Pacheco** — [GitHub](https://github.com/StuardoP) · [LinkedIn](https://linkedin.com/in/stuardopacheco)

