package org.example.util;

import org.example.model.Animal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalStats {

    public static <T extends Animal> double averageAge(List<T> animals){
        if (animals.isEmpty()) {
            return 0;
        }
        int total = 0;
        for (T animal : animals) {
            total += animal.getAge();
        }
        return (double) total / animals.size();
    }

    public static <T extends Animal> T oldestAnimal(List<T> animals){
        if (animals.isEmpty()) {
            return null;
        }
        T oldest = animals.get(0);
        for (T animal : animals) {
            if (animal.getAge() > oldest.getAge()) {
                oldest = animal;
            }
        }
        return oldest;
    }

    public static <T extends Animal> Map<String, Integer> countBySpecies(List<T> animals){
        Map<String, Integer> counts = new HashMap<>();
        for (T animal : animals) {
            String species = animal.getSpecies();
            counts.put(species, counts.getOrDefault(species, 0) + 1);
        }
        return counts;
    }
}
