package lv.bootcamp.shelter.task4;

import lv.bootcamp.shelter.model.Animal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

/**
 * Task 4: Collection and sorting tests
 *
 * Practice:
 * - AssertJ list assertions (extracting, containsExactly)
 * - Testing sort order
 * - Testing null/empty input handling
 *
 * Instructions:
 * Write tests for AnimalSorter. Use AssertJ's extracting() and containsExactly()
 * to verify the order of results.
 */
@DisplayName("AnimalSorter")
class AnimalSorterTest {

    private AnimalSorter sorter;

    private Animal buddy;
    private Animal luna;
    private Animal max;
    private Animal bella;

    @BeforeEach
    void setUp() {
        sorter = new AnimalSorter();
        buddy = new Animal("Buddy", "Dog", 3, true, LocalDate.of(2026, 1, 15));
        luna = new Animal("Luna", "Cat", 2, true, LocalDate.of(2026, 1, 10));
        max = new Animal("Max", "Dog", 5, false, LocalDate.of(2026, 1, 20));
        bella = new Animal("Bella", "Cat", 1, true, LocalDate.of(2026, 1, 5));
    }

    // --- sortByAge ---

    @Test
    @DisplayName("sortByAge: returns animals ordered youngest to oldest")
    void shouldSortByAgeAscending() {
        // TODO: Call sorter.sortByAge with [buddy, luna, max, bella]
        // TODO: Use assertThat(result).extracting(Animal::getName)
        //       .containsExactly("Bella", "Luna", "Buddy", "Max")
    }

    @Test
    @DisplayName("sortByAge: returns empty list for null input")
    void shouldReturnEmptyForNullInput() {
        // TODO: Call sorter.sortByAge(null)
        // TODO: Assert result is empty
    }

    @Test
    @DisplayName("sortByAge: returns empty list for empty input")
    void shouldReturnEmptyForEmptyInput() {
        // TODO: Call sorter.sortByAge(List.of())
        // TODO: Assert result is empty
    }

    @Test
    @DisplayName("sortByAge: does not modify the original list")
    void shouldNotModifyOriginalList() {
        // TODO: Create a list, sort it, then verify the original list order is unchanged
    }

    // --- sortByName ---

    @Test
    @DisplayName("sortByName: returns animals in alphabetical order")
    void shouldSortByNameAlphabetically() {
        // TODO: Call sorter.sortByName with [buddy, luna, max, bella]
        // TODO: Verify order is Bella, Buddy, Luna, Max
    }

    @Test
    @DisplayName("sortByName: is case-insensitive")
    void shouldSortNamesCaseInsensitively() {
        // TODO: Create animals with mixed case names (e.g., "zebra", "Alpha")
        // TODO: Verify alphabetical order ignores case
    }

    // --- sortByIntakeDate ---

    @Test
    @DisplayName("sortByIntakeDate: returns animals from earliest to latest")
    void shouldSortByIntakeDateAscending() {
        // TODO: Call sorter.sortByIntakeDate with [buddy, luna, max, bella]
        // TODO: Verify order by date: bella (Jan 5), luna (Jan 10), buddy (Jan 15), max (Jan 20)
    }

    // --- sortBySpeciesThenAgeDescending ---

    @Test
    @DisplayName("sortBySpeciesThenAgeDescending: groups by species then orders by age desc")
    void shouldSortBySpeciesThenAgeDesc() {
        // TODO: Call sorter.sortBySpeciesThenAgeDescending with [buddy, luna, max, bella]
        // TODO: Expected order: Cat group (Luna age 2, Bella age 1), Dog group (Max age 5, Buddy age 3)
        // TODO: Verify with extracting(Animal::getName).containsExactly(...)
    }
}
