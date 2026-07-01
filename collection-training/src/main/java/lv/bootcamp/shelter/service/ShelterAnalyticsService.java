package lv.bootcamp.shelter.service;

import lv.bootcamp.shelter.model.Animal;
import lv.bootcamp.shelter.service.data.ImportResult;
import lv.bootcamp.shelter.service.data.ShelterReportData;

import java.util.*;
import java.util.stream.Collectors;

public class ShelterAnalyticsService {

    public ShelterReportData buildReportData(ImportResult importResult) {
        List<Animal> allAnimals = importResult.allAnimals();

        Set<String> uniqueSpecies = new TreeSet<>();
        Map<String, List<Animal>> animalsBySpecies = new HashMap<>();
        List<String> animalsNeedingVetInput = new ArrayList<>();

        for (Animal animal : allAnimals) {
            uniqueSpecies.add(animal.getSpecies());
            animalsBySpecies
                    .computeIfAbsent(animal.getSpecies(), species -> new ArrayList<>())
                    .add(animal);
            if (!animal.isVaccinated() || animal.getAgeOptional().isEmpty()) {
                animalsNeedingVetInput.add(animal.getName() + "(" + animal.getSpecies() + ")");
            }
        }

        Map<String, Long> vaccinatedCountBySpecies = allAnimals.stream()
                .filter(Animal::isVaccinated)
                .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()));

        Map<String, Animal> oldestBySpecies = allAnimals.stream()
                .filter(animal -> animal.getAgeOptional().isPresent())
                .collect(Collectors.groupingBy(
                        Animal::getSpecies,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Animal::getAge)),
                                oldest -> oldest.orElse(null))));

        return new ShelterReportData(importResult, uniqueSpecies, animalsBySpecies,
                animalsNeedingVetInput, vaccinatedCountBySpecies, oldestBySpecies);
    }
}
