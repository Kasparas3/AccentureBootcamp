package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;
import org.example.util.AnimalStats;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner =  new Scanner(System.in);
    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start(){
        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> addAnimal();
                case "2" -> listAnimals(shelter.getAllAnimals());
                case "3" -> findBySpecies();
                case "4" -> listAnimals(shelter.findAvailableAnimals());
                case "5" -> markAsAdopted();
                case "6" -> sortAnimals();
                case "7" -> showStatistics();
                case "8" -> showAdoptionHistory();
                case "0" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Unknown option, please try again.");
            }
            System.out.println();
        }
    }

    private void addAnimal(){
        System.out.print("Species (Dog/Cat/Bird/Turtle): ");
        String species = scanner.nextLine().trim();

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Age: ");
        String ageText = scanner.nextLine().trim();
        int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            System.out.println("Age must be a whole number.");
            return;
        }
        if (age < 0) {
            System.out.println("Age cannot be negative.");
            return;
        }

        Animal animal;
        switch (species.toLowerCase()) {
            case "dog" -> animal = new Dog(new AnimalId(), name, age);
            case "cat" -> animal = new Cat(new AnimalId(), name, age);
            case "bird" -> animal = new Bird(new AnimalId(), name, age);
            case "turtle" -> animal = new Turtle(new AnimalId(), name, age);
            default -> {
                System.out.println("Unknown species: " + species);
                return;
            }
        }
        shelter.addAnimal(animal);
        System.out.println("Added: " + animal);
    }

    private void listAnimals(List<Animal> animals){
        if (animals.isEmpty()) {
            System.out.println("No animals to show.");
            return;
        }
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }

    private void findBySpecies(){
        System.out.print("Species to search for: ");
        String species = scanner.nextLine().trim();
        listAnimals(shelter.findBySpecies(species));
    }

    private void markAsAdopted(){
        System.out.print("Animal id to mark as adopted: ");
        String id = scanner.nextLine().trim();

        System.out.print("Adopter name: ");
        String adopterName = scanner.nextLine().trim();
        if (adopterName.isEmpty()) {
            System.out.println("Adopter name cannot be empty.");
            return;
        }

        shelter.markAsAdopted(id, adopterName);
    }

    private void showAdoptionHistory(){
        List<AdoptionRecord> history = shelter.getAdoptionHistory();
        if (history.isEmpty()) {
            System.out.println("No animals have been adopted yet.");
            return;
        }
        for (AdoptionRecord record : history) {
            System.out.println(record);
        }
    }

    private void showStatistics(){
        List<Animal> animals = shelter.getAllAnimals();
        if (animals.isEmpty()) {
            System.out.println("No animals in the shelter yet.");
            return;
        }

        System.out.printf("Average age: %.1f years%n", AnimalStats.averageAge(animals));
        System.out.println("Oldest animal: " + AnimalStats.oldestAnimal(animals));

        System.out.println("Number of animals per species:");
        Map<String, Integer> counts = AnimalStats.countBySpecies(animals);
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    private void sortAnimals(){
        System.out.print("Sort by (name/age): ");
        String choice = scanner.nextLine().trim();
        switch (choice.toLowerCase()) {
            case "name" -> listAnimals(shelter.getAnimalsSortedByName());
            case "age" -> listAnimals(shelter.getAnimalsSortedByAge());
            default -> System.out.println("Please type 'name' or 'age'.");
        }
    }

    private void printMenu(){
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                6. Sort animals by name or age
                7. Show statistics
                8. View adoption history
                0. Exit
                """);
    }
}
