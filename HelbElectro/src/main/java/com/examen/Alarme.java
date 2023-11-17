package com.examen;

import java.util.List;

public class Alarme extends Product {

    public final static String name = "Alarme";

    public final static int price = 20;
    public final static char ecoScore = 'c';
    public final static int fabricationTime = 4;

    private int batteryPercentage; // Attribut du composant batterie
    private int range; // Attribut du composant Capteur
    private String color; // Attribut du composant capteur

    public Alarme(List<Component> components) {
        super(components);

        for (Component component : components) {

            // si le component actuel est une instance de c1
            if (component instanceof C1) {
                C1 c1 = (C1) component; // Alors on caste le composant en C2
                this.batteryPercentage = c1.getBatteryPercentage(); // Attribut Spécifique à C1
            }

            if (component instanceof C2) {
                C2 c2 = (C2) component;
                this.range = c2.getRange();
                this.color = c2.getColor();

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

    // méthode spécifique à la sous classe "Alarme"
    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    // méthode spécifique à la sous classe "Alarme"
    public int getRange() {
        return range;
    }

    // méthode spécifique à la sous classe "Alarme"
    public String getColor() {
        return color;
    }
}
