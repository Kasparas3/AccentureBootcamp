package db.demo;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDemo {

    private static final String DB_URL = "jdbc:sqlite:student.db";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            System.out.println("Connected to SQLite database.");

            runSqlFile(connection, "create.sql");
            runSqlFile(connection, "insert.sql");
            runSqlFile(connection, "query.sql");

            System.out.println("Program finished.");

        } catch (SQLException e) {
            System.out.println("Database connection error:");
            System.out.println(e.getMessage());
        }
    }

    private static void runSqlFile(Connection connection, String fileName) {
        System.out.println("\n==============================");
        System.out.println("Running file: " + fileName);
        System.out.println("==============================");

        try {
            String sql = readResourceFile(fileName);

            String[] statements = sql.split(";");

            for (String statement : statements) {
                String cleaned = removeSqlComments(statement).trim();

                if (cleaned.isEmpty()) {
                    continue;
                }

                try (Statement stmt = connection.createStatement()) {
                    boolean hasResultSet = stmt.execute(cleaned);

                    if (hasResultSet) {
                        System.out.println();
                        printResultSet(stmt.getResultSet());
                    } else {
                        System.out.println("Executed successfully: " + getFirstSqlLine(cleaned));
                    }

                } catch (SQLException e) {
                    System.out.println("SQL error in file: " + fileName);
                    System.out.println("Statement:");
                    System.out.println(cleaned);
                    System.out.println("Error message:");
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }

        } catch (Exception e) {
            System.out.println("Could not run SQL file: " + fileName);
            System.out.println(e.getMessage());
        }
    }

    private static String readResourceFile(String fileName) throws Exception {
        try (InputStream inputStream = DatabaseDemo.class.getClassLoader().getResourceAsStream(fileName)) {

            if (inputStream == null) {
                throw new RuntimeException("File not found in src/main/resources: " + fileName);
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    private static String getFirstSqlLine(String sql) {
        String[] lines = sql.split("\\R");

        for (String line : lines) {
            String trimmedLine = line.trim();

            if (!trimmedLine.isEmpty() && !trimmedLine.startsWith("--")) {
                return trimmedLine;
            }
        }

        return sql.length() > 60 ? sql.substring(0, 60) + "..." : sql;
    }

    private static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i) + ": " + resultSet.getString(i));

                if (i < columnCount) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }
    }

    private static String removeSqlComments(String sql) {
        StringBuilder result = new StringBuilder();

        String[] lines = sql.split("\\R");

        for (String line : lines) {
            String trimmedLine = line.trim();

            if (trimmedLine.startsWith("--")) {
                continue;
            }

            result.append(line).append(System.lineSeparator());
        }

        return result.toString();
    }
}