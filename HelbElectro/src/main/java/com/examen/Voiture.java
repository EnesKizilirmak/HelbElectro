package com.examen;

import java.util.List;

public class Voiture extends Product {

    public final static String name = "Voiture";

    public final static int price = 30;
    public final static char ecoScore = 'b';
    public final static int fabricationTime = 5;

    private int power; // Attribut du composant Moteur
    private int batteryPercentage; // Attribut du composant Batterie

    public Voiture(List<Component> components) {
        super(components);

        for (Component component : components) {

            // si le component actuel est une instance de c1
            if (component instanceof C1) {
                C1 c1 = (C1) component; // Alors on caste le composant en C2
                this.batteryPercentage = c1.getBatteryPercentage(); // Attribut Spécifique à C1
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

    // méthode spécifique à la sous classe "Voiture"
    public int getPower() {
        return power;
    }

    // méthode spécifique à la sous classe "Voiture"
    public int getBatteryPercentage() {
        return batteryPercentage;
    }
}
