package org.example.model;

public final class Turtle extends Animal {
    public Turtle(AnimalId id, String name, int age){
        super(id,name,age);
    }

    @Override
    public String getSpecies(){
        return "Turtle";
    }
}
