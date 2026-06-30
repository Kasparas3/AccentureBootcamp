package org.example.shelter;

import org.example.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class Shelter <T extends Animal>{
    private final List<T> animals = new ArrayList<>();

    public void addAnimal(T animal){
        animals.add(animal);
    }

    public List<T> getAllAnimals(){
        // TODO
        return null;
    }

    public List<T> findBySpecies(String species){
        // TODO
        return null;
    }

    public List<T> findAvailableAnimals(){
        // TODO
        return null;
    }

    public void markAsAdopted(String id){
        // TODO
    }
}
