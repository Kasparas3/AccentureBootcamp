package lv.bootcamp.shelter.task6;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.task23.AnimalValidator;

import java.util.List;

/**
 * Service that handles animal intake operations.
 * Depends on AnimalRepository (mocked in tests).
 * Used in Task 7 for practising basic Mockito mocking.
 */
public class IntakeService {

    private final AnimalRepository repository;
    private final AnimalValidator validator;

    public IntakeService(AnimalRepository repository) {
        this.repository = repository;
        this.validator = new AnimalValidator();
    }

    /**
     * Validates and saves an animal to the repository.
     *
     * @param animal the animal to intake
     * @return the saved animal
     * @throws IllegalArgumentException if validation fails
     * @throws NullPointerException if animal is null
     */
    public Animal intake(Animal animal) {
        validator.validate(animal);
        return repository.save(animal);
    }

    /**
     * Finds an animal by name. Returns null if not found.
     */
    public Animal findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        return repository.findByName(name).orElse(null);
    }

    /**
     * Returns all animals of a given species.
     * Returns an empty list if species is null or blank.
     */
    public List<Animal> findBySpecies(String species) {
        if (species == null || species.isBlank()) {
            return List.of();
        }
        return repository.findBySpecies(species);
    }

    /**
     * Returns the total number of animals in the repository.
     */
    public int count() {
        return repository.findAll().size();
    }
}
