package com.examen;

import java.util.List;

public class Capteur extends Product {

    public final static String name = "Capteur";

    public final static int price = 10;
    public final static char ecoScore = 'b';
    public final static int fabricationTime = 3;

    private int range; // Attribut spécifique à la sous classe Capteur
    private String color; // Attribut spécifique à la sous classe Capteur

    public Capteur(List<Component> components) {
        super(components);

        for (Component component : components) {

            // si le component actuel est une instance de c2
            if (component instanceof C2) {
                C2 c2 = (C2) component; // Alors on caste le composant en C2

                this.range = c2.getRange(); // Attribut specifique a capteur
                this.color = c2.getColor(); // Attribut specifique a capteru
                break;
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

    // Méthodes spécifiques à Capteur
    public int getRange() {
        return range;
    }

    // Méthodes spécifiques à Capteur
    public String getColor() {
        return color;
    }
}
