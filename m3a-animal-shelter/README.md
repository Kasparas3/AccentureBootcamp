# Practical Task: Animal Shelter Console App

Starter project for M3A practical task: Create a simple Java console application for managing animals in an animal shelter.

## Task
Complete all of the 'TODO' portions of the code. Finished application should support:
- Adding a new animal
- Listing all animals
- Searching animals by species
- Marking an animal as adopted
- Displaying only available animals

## OOP Requirements
- Class anatomy: fields, constructors, methods
- Object isntantiation and usage
- Constructor overloading
- Immutable class usage (AnimalId)
- Lombok usage for reducing boilerplate
- A basic sealed class hierarchy
- Generic (Shelter<T>) class that stores animals

## Project Structure
``` text
src
├── Main.java
├── menu/
│   └── ConsoleMenu.java
│   └── MenuOption.java
├── model/
│   ├── Animal.java
│   ├── Dog.java
│   ├── Cat.java
│   ├── Bird.java
│   ├── AnimalId.java
│   └── AdoptionStatus.java
└── shelter/
    └── Shelter.java
```
## Stretch goals
- Add a new animal type without modifying existing functionality
- Allow sorting animals by age or name
- Validate user input (no empty names, age cannot be negative etc.)
- Create a generic utility class for searching and filtering collections (Average animal age, Oldest animal, Number of animals of each species etc.)
- Add adoption history that tracks:
    - Animal
    - Adoption Date
    - Adopter Name
