package lv.bootcamp.shelter.stretch;

import lv.bootcamp.shelter.model.Animal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Writes a summary report to a text file.
 * Used as a stretch goal for testing file output.
 */
public class AnimalReportWriter {

    /**
     * Writes a summary report for the given list of animals.
     *
     * @param animals list of animals to summarize
     * @param outputPath path to write the report file
     * @throws IOException if writing fails
     */
    public void writeReport(List<Animal> animals, Path outputPath) throws IOException {
        Map<String, List<Animal>> bySpecies = animals.stream()
                .collect(Collectors.groupingBy(Animal::getSpecies));

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write("=== Shelter Intake Report ===");
            writer.newLine();
            writer.write("Date generated: " + LocalDate.now());
            writer.newLine();
            writer.newLine();
            writer.write("Total animals: " + animals.size());
            writer.newLine();
            writer.newLine();

            writer.write("--- Per-species breakdown ---");
            writer.newLine();

            bySpecies.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        try {
                            String species = entry.getKey();
                            List<Animal> group = entry.getValue();
                            long vaccinatedCount = group.stream().filter(Animal::isVaccinated).count();
                            writer.write(species + ": " + group.size() + " total, " + vaccinatedCount + " vaccinated");
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to write report line", e);
                        }
                    });

            writer.newLine();
            writer.write("--- Oldest per species ---");
            writer.newLine();

            bySpecies.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        try {
                            String species = entry.getKey();
                            Animal oldest = entry.getValue().stream()
                                    .max(Comparator.comparingInt(Animal::getAge))
                                    .orElseThrow();
                            writer.write(species + ": " + oldest.getName() + " (age " + oldest.getAge() + ")");
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to write report line", e);
                        }
                    });
        }
    }
}
