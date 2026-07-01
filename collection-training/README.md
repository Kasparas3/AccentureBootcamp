# Shelter CSV Import and Report Starter

Starter project for M4 practical task: Collections, Files, and Streams.

## Task
Build a small Java application that:
1. Parses and validates shelter intake CSV rows.
2. Stores valid data in suitable collections.
3. Uses stream pipelines to compute report metrics.
4. Exports output/upload-report.txt in the required format.

## What is included
- Compile-safe project skeleton with TODO markers for Steps 1-4.
- Sample intake file with 30+ rows, including intentionally invalid rows.
- Output folder scaffold and optional processed-files registry placeholder.
- SLF4J logging dependencies.

## Project structure
- src/main/java/lv/bootcamp/shelter/model/Animal.java
- src/main/java/lv/bootcamp/shelter/service/CsvImportService.java
- src/main/java/lv/bootcamp/shelter/service/ShelterAnalyticsService.java
- src/main/java/lv/bootcamp/shelter/service/ReportExportService.java
- src/main/resources/data/intake.csv
- output/processed-files.txt

## Run commands
Use Maven wrapper (recommended):
- Windows: ./mvnw.cmd test
- Windows: ./mvnw.cmd exec:java
- macOS/Linux: ./mvnw test
- macOS/Linux: ./mvnw exec:java

## Required behavior checklist
- Skip malformed rows and log warnings with SLF4J.
- Accept blank age as unknown.
- Reject non-numeric age values.
- Parse date format dd.MM.yyyy.
- Build collections:
  - List<Animal> allAnimals
  - Set<String> uniqueSpecies
  - Map<String, List<Animal>> animalsBySpecies
  - List<String> animalsNeedingVetInput
- Report includes:
  - total imported and total skipped
  - unique species list
  - per-species totals and vaccinated counts
  - oldest animal per species (excluding unknown ages)
  - needs-vet-input line in format name(species)

## Stretch goals
- Sort report sections alphabetically by species.
- Use Optional for missing ages.
- Add JSON summary export.
- Process multiple CSV files with Files.walk().
- Write unique timestamped report filenames.
- Persist processed input filenames and skip duplicates.
- Include invalid row numbers in report.
