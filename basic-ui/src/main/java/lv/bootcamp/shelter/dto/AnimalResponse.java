package lv.bootcamp.shelter.dto;

import lv.bootcamp.shelter.model.AnimalStatus;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * Response body for a single animal returned by the API.
 * {@code adoptionNote} is only populated for ADMIN callers (see AnimalService#toResponse) —
 * everyone else just sees the plain {@code status}.
 */
public record AnimalResponse(
        Long id,
        String name,
        AnimalType type,
        String breed,
        Integer age,
        String description,
        AnimalStatus status,
        String imageUrl,
        String adoptionNote
) {}
