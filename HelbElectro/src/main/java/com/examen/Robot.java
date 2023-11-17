package com.examen;

import java.util.List;

public class Robot extends Product {

    public final static String name = "Robot";

    public final static int price = 40;
    public final static char ecoScore = 'b';
    public final static int fabricationTime = 6;

    private int power; // Attribut du composant Moteur
    private int range; // Attribut du composant Capteur
    private String color; // Attribut du composant Capteur

    public Robot(List<Component> components) {
        super(components);

        for (Component component : components) {

            // si le component actuel est une instance de c2
            if (component instanceof C2) {
                C2 c2 = (C2) component; // Alors on caste le composant en C2

                this.range = c2.getRange(); // On donne le range du c2 comme attribut à robot
                this.color = c2.getColor();
            }

            if (component instanceof C3) {
                C3 c3 = (C3) component;
                this.power = c3.getPower();
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public char getEcoScore() {
        return ecoScore;
    }

    @Override
    public int getFabricationTime() {
        return fabricationTime;
    }

    // méthode spécifique à la sous classe "Robot"
    public int getPower() {
        return power;
    }

    // méthode spécifique à la sous classe "Robot"
    public int getRange() {
        return range;
    }

    // méthode spécifique à la sous classe "Robot"
    public String getColor() {
        return color;
    }
}
