package lv.bootcamp.shelter.task4;

import lv.bootcamp.shelter.model.Animal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Provides sorting operations for animal lists.
 * Used in Task 4 for practising collection and comparator tests.
 */
public class AnimalSorter {

    /**
     * Returns a new list sorted by age ascending.
     */
    public List<Animal> sortByAge(List<Animal> animals) {
        if (animals == null) {
            return List.of();
        }
        List<Animal> sorted = new ArrayList<>(animals);
        sorted.sort(Comparator.comparingInt(Animal::getAge));
        return sorted;
    }

    /**
     * Returns a new list sorted by name alphabetically (case-insensitive).
     */
    public List<Animal> sortByName(List<Animal> animals) {
        if (animals == null) {
            return List.of();
        }
        List<Animal> sorted = new ArrayList<>(animals);
        sorted.sort(Comparator.comparing(Animal::getName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    /**
     * Returns a new list sorted by intake date (oldest first).
     */
    public List<Animal> sortByIntakeDate(List<Animal> animals) {
        if (animals == null) {
            return List.of();
        }
        List<Animal> sorted = new ArrayList<>(animals);
        sorted.sort(Comparator.comparing(Animal::getIntakeDate));
        return sorted;
    }

    /**
     * Returns a new list sorted by species alphabetically, then by age descending within each species.
     */
    public List<Animal> sortBySpeciesThenAgeDescending(List<Animal> animals) {
        if (animals == null) {
            return List.of();
        }
        List<Animal> sorted = new ArrayList<>(animals);
        sorted.sort(Comparator.comparing(Animal::getSpecies)
                .thenComparing(Comparator.comparingInt(Animal::getAge).reversed()));
        return sorted;
    }
}
