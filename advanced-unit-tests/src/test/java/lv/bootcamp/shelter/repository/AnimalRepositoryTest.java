package lv.bootcamp.shelter.repository;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Task: Repository tests with @DataJpaTest.
 *
 * Use entityManager.persist() + entityManager.flush() to set up test data.
 * Each test rolls back automatically — no cleanup needed.
 */
@DataJpaTest
class AnimalRepositoryTest {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_shouldPersistAnimalAndGenerateId() {
        Animal animal = new Animal(null, "Rex", AnimalType.DOG, "Labrador", 4, "friendly", AnimalStatus.AVAILABLE);

        Animal saved = animalRepository.save(animal);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Rex");
    }

    @Test
    void findByStatus_shouldReturnOnlyMatchingAnimals() {
        entityManager.persist(new Animal(null, "Rex", AnimalType.DOG, "Labrador", 4, "friendly", AnimalStatus.AVAILABLE));
        entityManager.persist(new Animal(null, "Luna", AnimalType.CAT, "Siamese", 2, "shy", AnimalStatus.AVAILABLE));
        entityManager.persist(new Animal(null, "Bella", AnimalType.DOG, "Poodle", 3, "calm", AnimalStatus.ADOPTED));
        entityManager.flush();

        List<Animal> available = animalRepository.findByStatus(AnimalStatus.AVAILABLE);

        assertThat(available).extracting(Animal::getName).containsExactlyInAnyOrder("Rex", "Luna");
    }

    @Test
    void findByType_shouldReturnAnimalsOfGivenType() {
        entityManager.persist(new Animal(null, "Rex", AnimalType.DOG, "Labrador", 4, "friendly", AnimalStatus.AVAILABLE));
        entityManager.persist(new Animal(null, "Luna", AnimalType.CAT, "Siamese", 2, "shy", AnimalStatus.AVAILABLE));
        entityManager.flush();

        List<Animal> dogs = animalRepository.findByType(AnimalType.DOG);

        assertThat(dogs).extracting(Animal::getName).containsExactly("Rex");
    }

    @Test
    void findByNameContainingIgnoreCase_shouldMatchPartialName() {
        entityManager.persist(new Animal(null, "Rex", AnimalType.DOG, "Labrador", 4, "friendly", AnimalStatus.AVAILABLE));
        entityManager.persist(new Animal(null, "Rexy Jr", AnimalType.DOG, "Labrador", 1, "playful", AnimalStatus.AVAILABLE));
        entityManager.persist(new Animal(null, "Mia", AnimalType.CAT, "Siamese", 2, "shy", AnimalStatus.AVAILABLE));
        entityManager.flush();

        List<Animal> matches = animalRepository.findByNameContainingIgnoreCase("rex");

        assertThat(matches).hasSize(2)
                .extracting(Animal::getName)
                .containsExactlyInAnyOrder("Rex", "Rexy Jr");
    }
}
