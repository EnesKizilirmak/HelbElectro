package com.examen;

import java.util.List;

public class Batterie extends Product {

    public final static String name = "Batterie";

    public final static int price = 5;
    public final static char ecoScore = 'c';
    public final static int fabricationTime = 3;

    private int batteryPercentage; // Attribut Spécifique à ma sous classe Batterie

    public Batterie(List<Component> components) {
        super(components);

        for (Component component : components) {

            // si le component actuel est une instance de c1
            if (component instanceof C1) {
                C1 c1 = (C1) component; // Alors on caste le composant en C1

                this.batteryPercentage = c1.getBatteryPercentage(); // Attribut Spécifique à ma sous classe Batterie
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

    // Méthode spécifique à Batterie
    public int getBatteryPercentage() {
        return batteryPercentage;
    }
}
