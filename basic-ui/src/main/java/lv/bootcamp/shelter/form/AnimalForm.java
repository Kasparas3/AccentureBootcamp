package lv.bootcamp.shelter.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lv.bootcamp.shelter.model.AnimalType;

/**
 * Form-backing object for server-rendered Thymeleaf pages.
 * Kept separate from API DTOs so form changes do not silently change the JSON contract.
 */
public record AnimalForm(

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