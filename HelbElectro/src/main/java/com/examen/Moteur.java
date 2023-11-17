package com.examen;

import java.util.List;

public class Moteur extends Product {

    public final static String name = "Moteur";

    public final static int price = 15;
    public final static char ecoScore = 'a';
    public final static int fabricationTime = 3;

    private int power; // Attribut spécifique à la sous classe Moteur

    public Moteur(List<Component> components) {
        super(components);

        for (Component component : components) {

            // si le component actuel est une instance de c3
            if (component instanceof C3) {
                C3 c3 = (C3) component; // Alors on caste le composant en C3

                this.power = c3.getPower(); // Attribut specifique a moteur
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

    // Méthode spécifique à Moteur
    public int getPower() {
        return power;
    }
}
