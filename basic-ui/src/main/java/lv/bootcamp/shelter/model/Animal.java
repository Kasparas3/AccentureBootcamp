package lv.bootcamp.shelter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Internal representation of a shelter animal stored in memory.
 * Not exposed directly to controllers — use API DTOs and page form models instead.
 * Lombok generates: getters for all fields, setters for all non-final fields,
 * an all-args constructor, and a toString method.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Animal {

    /** Immutable identity — assigned once by AnimalRepository. No setter generated. */
    private final Long id;
    private String name;
    private AnimalType type;
    private String breed;
    private Integer age;
    private String description;
    private AnimalStatus status;
    private String imageUrl;
    /** Populated only once the animal is adopted (see AnimalService#adopt). */
    private AdoptionDetails adoptionDetails;
}
