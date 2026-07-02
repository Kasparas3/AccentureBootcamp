package lv.bootcamp.shelter.task5;

import lv.bootcamp.shelter.model.Animal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Parses a CSV file into Animal objects.
 * Used in Task 5 for practising nested test classes and file I/O testing.
 */
public class AnimalCsvParser {

    /**
     * Parses a single CSV row into an Animal.
     * Expected format: name,species,age,vaccinated,intakeDate
     *
     * @param row a single comma-separated line
     * @return Optional containing the parsed Animal, or empty if the row is malformed
     */
    public Optional<Animal> parseRow(String row) {
        if (row == null || row.isBlank()) {
            return Optional.empty();
        }

        String[] parts = row.split(",", -1);
        if (parts.length < 5) {
            return Optional.empty();
        }

        String name = parts[0].trim();
        String species = parts[1].trim();
        String ageStr = parts[2].trim();
        String vaccinatedStr = parts[3].trim();
        String dateStr = parts[4].trim();

        if (name.isEmpty() || species.isEmpty()) {
            return Optional.empty();
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (age < 0) {
            return Optional.empty();
        }

        boolean vaccinated = Boolean.parseBoolean(vaccinatedStr);

        LocalDate intakeDate;
        try {
            intakeDate = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }

        return Optional.of(new Animal(name, species, age, vaccinated, intakeDate));
    }

    /**
     * Parses an entire CSV file, skipping the header row and any malformed rows.
     *
     * @param filePath path to the CSV file
     * @return a ParseResult containing the successfully parsed animals and the count of skipped rows
     * @throws IOException if the file cannot be read
     */
    public ParseResult parseFile(Path filePath) throws IOException {
        List<Animal> animals = new ArrayList<>();
        int skipped = 0;

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String header = reader.readLine(); // skip header
            if (header == null) {
                return new ParseResult(animals, skipped);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                Optional<Animal> parsed = parseRow(line);
                if (parsed.isPresent()) {
                    animals.add(parsed.get());
                } else {
                    skipped++;
                }
            }
        }

        return new ParseResult(animals, skipped);
    }

    /**
     * Holds the result of a CSV parse operation.
     */
    public record ParseResult(List<Animal> animals, int skippedRows) {
    }
}
