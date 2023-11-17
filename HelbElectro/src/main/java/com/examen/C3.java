package com.examen;

public class C3 extends Component {

    private final String name = "Moteur";

    private int power; // Attribut spécifique a C2 a récupéré depuis le fichier

    public C3(int power) {

        this.power = power; // Attribut spécifique a C2 a récupéré depuis le fichier
    }

    // Surcharge de constructeur nécessaire lorsque je vais supprimer un C3
    public C3() {
    }

    @Override
    public String getName() {
        return name;
    }

    // Méthode spécifique à "C3"
    public int getPower() {
        return power;
    }

}
