package lv.bootcamp.shelter.service;

import lombok.extern.slf4j.Slf4j;
import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;
import lv.bootcamp.shelter.service.data.ShelterReportData;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Slf4j
public class ReportExportService {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public void writeReport(Path outputPath, ShelterReportData reportData) {
        try {
            if (outputPath.getParent() != null) {
                Files.createDirectories(outputPath.getParent());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
                writeReportBody(writer, reportData);
            }

            log.info("Report written to {}", outputPath);
        } catch (IOException e) {
            log.error("Failed to write report to {}", outputPath, e);
        }
    }

    private void writeReportBody(BufferedWriter writer, ShelterReportData reportData) throws IOException {
        ImportResult importResult = reportData.importResult();

        writer.write("Shelter Upload Report");
        writer.newLine();
        writer.write("Generated: " + LocalDate.now().format(DATE_FORMAT));
        writer.newLine();
        writer.newLine();

        writer.write("Totals");
        writer.newLine();
        writer.write("- Imported: " + importResult.allAnimals().size());
        writer.newLine();
        writer.write("- Skipped: " + importResult.skippedRows());
        writer.newLine();
        String invalidRows = importResult.invalidRowNumbers().isEmpty()
                ? "none"
                : importResult.invalidRowNumbers().stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
        writer.write("- Invalid rows: " + invalidRows);
        writer.newLine();
        writer.newLine();

        writer.write("Unique species: " + String.join(", ", reportData.uniqueSpecies()));
        writer.newLine();
        writer.newLine();

        writer.write("Per-species breakdown");
        writer.newLine();
        for (String species : reportData.uniqueSpecies()) {
            int total = reportData.animalsBySpecies().get(species).size();
            long vaccinated = reportData.vaccinatedCountBySpecies().getOrDefault(species, 0L);
            long unvaccinated = total - vaccinated;
            writer.write("- " + species + ": total=" + total
                    + ", vaccinated=" + vaccinated
                    + ", unvaccinated=" + unvaccinated);
            writer.newLine();
        }
        writer.newLine();

        writer.write("Oldest per species");
        writer.newLine();
        for (String species : reportData.uniqueSpecies()) {
            Animal oldest = reportData.oldestBySpecies().get(species);
            if (oldest == null) {
                writer.write("- " + species + ": unknown");
            } else {
                writer.write("- " + species + ": " + oldest.getName() + " (age " + oldest.getAge() + ")");
            }
            writer.newLine();
        }
        writer.newLine();

        writer.write("Needs vet input");
        writer.newLine();
        writer.write(String.join(", ", reportData.animalsNeedingVetInput()));
        writer.newLine();
    }
}
