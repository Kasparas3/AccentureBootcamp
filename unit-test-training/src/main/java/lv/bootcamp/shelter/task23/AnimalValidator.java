package lv.bootcamp.shelter.task23;

import lv.bootcamp.shelter.model.Animal;

/**
 * Validates Animal objects before they are accepted into the shelter system.
 * Used in Tasks 2 and 3 for practising parameterized and exception tests.
 */
public class AnimalValidator {

    private static final int MAX_REASONABLE_AGE = 50;

    /**
     * Validates the given animal. Throws if any field is invalid.
     *
     * @param animal the animal to validate
     * @throws IllegalArgumentException if any field is invalid
     * @throws NullPointerException if animal is null
     */
    public void validate(Animal animal) {
        if (animal == null) {
            throw new NullPointerException("Animal must not be null");
        }
        validateName(animal.getName());
        validateSpecies(animal.getSpecies());
        validateAge(animal.getAge());
    }

    /**
     * Validates that a name is not null, not blank, and not longer than 100 characters.
     *
     * @throws IllegalArgumentException if name is invalid
     */
    public void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Name must not exceed 100 characters");
        }
    }

    /**
     * Validates that a species is not null and not blank.
     *
     * @throws IllegalArgumentException if species is invalid
     */
    public void validateSpecies(String species) {
        if (species == null || species.isBlank()) {
            throw new IllegalArgumentException("Species must not be blank");
        }
    }

    /**
     * Validates that age is between 0 and MAX_REASONABLE_AGE (inclusive).
     *
     * @throws IllegalArgumentException if age is out of range
     */
    public void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age must not be negative, got: " + age);
        }
        if (age > MAX_REASONABLE_AGE) {
            throw new IllegalArgumentException("Age seems unrealistic, got: " + age);
        }
    }
}
