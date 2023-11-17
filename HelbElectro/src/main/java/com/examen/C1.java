package com.examen;

public class C1 extends Component {

    private final String name = "Batterie";

    private int batteryPercentage; // Attribut spécifique a C1 a récupéré depuis le fichier

    public C1(int batteryPercentage) {

        this.batteryPercentage = batteryPercentage; // Attribut spécifique a C1 a récupéré depuis le fichier
    }

    // Surcharge de constructeur nécessaire lorsque je vais supprimer un C1
    public C1() {
    }

    @Override
    public final String getName() {
        return name;
    }

    // Méthode spécifique à C1
    public int getBatteryPercentage() {
        return batteryPercentage;
    }
}
