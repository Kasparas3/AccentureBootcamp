package lv.bootcamp.shelter.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * JSON request body for creating a new animal via the REST API.
 * Status is not included; all new animals start as AVAILABLE.
 */
public record AnimalCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Type is required")
        AnimalType type,

        String breed,

        @Min(value = 0, message = "Age cannot be negative")
        Integer age,

        String description,

        String imageUrl
) {}