package org.example.menu;

import org.example.model.Animal;
import org.example.model.AnimalId;
import org.example.model.Bird;
import org.example.model.Cat;
import org.example.model.Dog;
import org.example.shelter.Shelter;

import java.util.List;
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
        System.out.print("Species (Dog/Cat/Bird): ");
        String species = scanner.nextLine().trim();

        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine().trim());

        Animal animal;
        switch (species.toLowerCase()) {
            case "dog" -> animal = new Dog(new AnimalId(), name, age);
            case "cat" -> animal = new Cat(new AnimalId(), name, age);
            case "bird" -> animal = new Bird(new AnimalId(), name, age);
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
        shelter.markAsAdopted(id);
    }

    private void printMenu(){
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                0. Exit
                """);
    }
}
