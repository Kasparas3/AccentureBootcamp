package lv.bootcamp.shelter.service;

import lombok.extern.slf4j.Slf4j;
import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CsvImportService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final int EXPECTED_COLUMNS = 5;

    public ImportResult importAnimals(Path inputPath) {
        log.info("Starting import from {}", inputPath);

        List<Animal> allAnimals = new ArrayList<>();
        int skippedRows = 0;

        List<String> lines;
        try {
            lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("Failed to read input file {}", inputPath, e);
            return new ImportResult(allAnimals, skippedRows);
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            int rowNumber = i + 1;

            if (line.isBlank()) {
                continue;
            }

            Animal animal = parseRow(line, rowNumber);
            if (animal == null) {
                skippedRows++;
            } else {
                allAnimals.add(animal);
            }
        }

        log.info("Import finished: {} imported, {} skipped", allAnimals.size(), skippedRows);
        return new ImportResult(allAnimals, skippedRows);
    }

    private Animal parseRow(String line, int rowNumber) {
        String[] fields = line.split(",", -1);
        if (fields.length != EXPECTED_COLUMNS) {
            log.warn("Row {}: expected {} columns but found {} - skipping: {}",
                    rowNumber, EXPECTED_COLUMNS, fields.length, line);
            return null;
        }

        String name = fields[0].trim();
        String species = fields[1].trim();
        String ageText = fields[2].trim();
        String vaccinatedText = fields[3].trim();
        String dateText = fields[4].trim();

        if (name.isEmpty() || species.isEmpty()) {
            log.warn("Row {}: name and species are required - skipping: {}", rowNumber, line);
            return null;
        }

        Integer age = null;
        if (!ageText.isEmpty()) {
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                log.warn("Row {}: non-numeric age '{}' - skipping: {}", rowNumber, ageText, line);
                return null;
            }
        }

        boolean vaccinated = Boolean.parseBoolean(vaccinatedText);

        LocalDate intakeDate;
        try {
            intakeDate = LocalDate.parse(dateText, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            log.warn("Row {}: invalid date '{}' - skipping: {}", rowNumber, dateText, line);
            return null;
        }

        return new Animal(name, species, age, vaccinated, intakeDate);
    }
}
