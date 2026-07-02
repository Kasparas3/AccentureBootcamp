package lv.bootcamp.shelter.task6;

import lv.bootcamp.shelter.model.Animal;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Animal persistence.
 * This interface is NOT implemented in the project — students will mock it in tests.
 */
public interface AnimalRepository {

    Animal save(Animal animal);

    Optional<Animal> findByName(String name);

    List<Animal> findAll();

    List<Animal> findBySpecies(String species);

    void delete(Animal animal);
}
