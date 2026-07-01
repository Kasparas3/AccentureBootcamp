package lv.bootcamp.shelter;

import lv.bootcamp.shelter.service.CsvImportService;
import lv.bootcamp.shelter.service.data.ImportResult;
import lv.bootcamp.shelter.service.ReportExportService;
import lv.bootcamp.shelter.service.ShelterAnalyticsService;
import lv.bootcamp.shelter.service.data.ShelterReportData;

import java.nio.file.Path;

public class ShelterCsvApplication {

    public static void main(String[] args) {
        Path inputPath = Path.of("src", "main", "resources", "data", "intake.csv");
        Path outputPath = Path.of("output", "upload-report.txt");

        CsvImportService importService = new CsvImportService();
        ShelterAnalyticsService analyticsService = new ShelterAnalyticsService();
        ReportExportService reportExportService = new ReportExportService();

        ImportResult importResult = importService.importAnimals(inputPath);
        ShelterReportData reportData = analyticsService.buildReportData(importResult);
        reportExportService.writeReport(outputPath, reportData);
    }
}
