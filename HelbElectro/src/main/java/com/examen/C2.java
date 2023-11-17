package com.examen;

public class C2 extends Component {

    private final String name = "Capteur";

    private int range; // Attribut spécifique a C2 a récupéré depuis le fichier
    private String color; // Attribut spécifique a C2 a récupéré depuis le fichier

    public C2(int range, String color) {

        this.range = range; // Attribut spécifique a C2 a récupéré depuis le fichier
        this.color = color; // Attribut spécifique a C2 a récupéré depuis le fichier
    }

    // Surcharge de constructeur nécessaire lorsque je vais supprimer un C2
    public C2() {
    }

    @Override
    public String getName() {
        return name;
    }

    // Méthode spécifique à "C2"
    public int getRange() {
        return range;
    }

    // Méthode spécifique à "C2"
    public String getColor() {
        return color;
    }
}
