package lv.bootcamp.shelter.model;

import java.time.LocalDate;

/**
 * Records who adopted an animal and when.
 * Set once, when an animal's status transitions to {@link AnimalStatus#ADOPTED}.
 */
public record AdoptionDetails(String userId, LocalDate adoptionDate) {}
