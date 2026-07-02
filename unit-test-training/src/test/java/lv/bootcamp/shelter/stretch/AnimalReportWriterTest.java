package lv.bootcamp.shelter.stretch;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Stretch goal: Testing file output
 *
 * Practice:
 * - Writing to temp files and reading them back
 * - String content assertions
 * - Cleanup with Files.deleteIfExists
 *
 * Instructions:
 * These tests verify that AnimalReportWriter produces correct output.
 * This task is optional — attempt it after completing tasks 1–6.
 */
@DisplayName("AnimalReportWriter (stretch)")
class AnimalReportWriterTest {

    private final AnimalReportWriter writer = new AnimalReportWriter();

    @Test
    @DisplayName("writes report file that contains total count")
    void shouldWriteTotalCount() throws IOException {
        // TODO: Create a list of 3 animals
        // TODO: Create a temp file: Path output = Files.createTempFile("report-test", ".txt");
        // TODO: Call writer.writeReport(animals, output)
        // TODO: Read the file content: String content = Files.readString(output, StandardCharsets.UTF_8);
        // TODO: Assert content contains "Total animals: 3"
        // TODO: Clean up: Files.deleteIfExists(output)
    }

    @Test
    @DisplayName("writes per-species breakdown in alphabetical order")
    void shouldWriteSpeciesBreakdown() throws IOException {
        // TODO: Create animals of different species (Dog, Cat)
        // TODO: Write report to temp file
        // TODO: Read content and verify "Cat:" appears before "Dog:" (alphabetical)
        // TODO: Verify vaccinated counts are correct
    }

    @Test
    @DisplayName("writes oldest animal per species")
    void shouldWriteOldestPerSpecies() throws IOException {
        // TODO: Create animals where Max (age 5) is the oldest Dog
        // TODO: Write report to temp file
        // TODO: Read content and verify it contains "Dog: Max (age 5)"
    }
}
