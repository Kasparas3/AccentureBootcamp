package org.example;

import org.example.menu.ConsoleMenu;
import org.example.model.*;
import org.example.shelter.Shelter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Animal Shelter!");

        // create shelter object
        Shelter<Animal> shelter = new Shelter<>();

        // pre-load some data
        shelter.addAnimal(new Dog(new AnimalId(),"Buddy",3));
        shelter.addAnimal(new Cat(new AnimalId(),"Luna",5));
        shelter.addAnimal(new Bird(new AnimalId(),"Kiwi",1));

        // create a ConsoleMenu object
        ConsoleMenu consoleMenu = new ConsoleMenu(shelter);
        // start the menu loop
        consoleMenu.start();
    }
}