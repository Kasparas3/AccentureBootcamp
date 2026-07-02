package lv.bootcamp.shelter.model;

import java.time.LocalDate;
import java.util.Objects;

public class Animal {

    private final String name;
    private final String species;
    private final int age;
    private final boolean vaccinated;
    private final LocalDate intakeDate;

    public Animal(String name, String species, int age, boolean vaccinated, LocalDate intakeDate) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.vaccinated = vaccinated;
        this.intakeDate = intakeDate;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public int getAge() {
        return age;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public LocalDate getIntakeDate() {
        return intakeDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return age == animal.age
                && vaccinated == animal.vaccinated
                && Objects.equals(name, animal.name)
                && Objects.equals(species, animal.species)
                && Objects.equals(intakeDate, animal.intakeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, species, age, vaccinated, intakeDate);
    }

    @Override
    public String toString() {
        return name + " (" + species + ", age " + age + ", "
                + (vaccinated ? "vaccinated" : "not vaccinated") + ", intake " + intakeDate + ")";
    }
}
