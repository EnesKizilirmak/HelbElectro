package com.examen;

import java.util.List;

public class Drone extends Product {

    public final static String name = "Drone";

    public final static int price = 60;
    public final static char ecoScore = 'e';
    public final static int fabricationTime = 12;

    private int batteryPercentage; // Attribut du composant batterie
    private int range; // Attribut du composant capteur
    private String color; // Attribut du composant capteur
    private int power; // Attribut du composant Moteur

    public Drone(List<Component> components) {
        super(components);

        for (Component component : components) {

            // si le component actuel est une instance de c1
            if (component instanceof C1) {
                C1 c1 = (C1) component; // Alors on caste le composant en C1
                this.batteryPercentage = c1.getBatteryPercentage(); // Attribut Spécifique à C1
            }

            if (component instanceof C2) {
                C2 c2 = (C2) component;
                this.range = c2.getRange();
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

    // méthode spécifique à la sous classe "drone"
    public int getPower() {
        return power;
    }

    // méthode spécifique à la sous classe "drone"
    public int getBatteryPercentage() {
        return batteryPercentage;
    }

    // méthode spécifique à la sous classe "drone"
    public int getRange() {
        return range;
    }

    // méthode spécifique à la sous classe "drone"
    public String getColor() {
        return color;
    }
}
