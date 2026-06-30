package org.example.shelter;

import org.example.model.Animal;
import org.example.model.AdoptionRecord;
import org.example.model.AdoptionStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shelter <T extends Animal>{
    private final List<T> animals = new ArrayList<>();
    private final List<AdoptionRecord> adoptionHistory = new ArrayList<>();

    public void addAnimal(T animal){
        animals.add(animal);
    }

    public List<T> getAllAnimals(){
        return new ArrayList<>(animals);
    }

    public List<T> findBySpecies(String species){
        List<T> result = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getSpecies().equalsIgnoreCase(species)) {
                result.add(animal);
            }
        }
        return result;
    }

    public List<T> findAvailableAnimals(){
        List<T> result = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getAdoptionStatus() == AdoptionStatus.AVAILABLE) {
                result.add(animal);
            }
        }
        return result;
    }

    public List<T> getAnimalsSortedByName(){
        List<T> sorted = new ArrayList<>(animals);
        sorted.sort(Comparator.comparing(Animal::getName, String.CASE_INSENSITIVE_ORDER));
        return sorted;
    }

    public List<T> getAnimalsSortedByAge(){
        List<T> sorted = new ArrayList<>(animals);
        sorted.sort(Comparator.comparingInt(Animal::getAge));
        return sorted;
    }

    public void markAsAdopted(String id, String adopterName){
        for (T animal : animals) {
            if (animal.getId().toString().equals(id)) {
                animal.markAsAdopted();
                adoptionHistory.add(new AdoptionRecord(animal, LocalDate.now(), adopterName));
                return;
            }
        }
        System.out.println("No animal found with id: " + id);
    }

    public List<AdoptionRecord> getAdoptionHistory(){
        return new ArrayList<>(adoptionHistory);
    }
}
