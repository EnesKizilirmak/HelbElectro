package com.examen;

import java.util.ArrayList;
import java.util.List;

public abstract class Product {

    private List<Component> componentsList = new ArrayList<>();

    // Constructeur qui prend une liste en paramètre
    public Product(List<Component> componentsList) {
        this.componentsList = componentsList;
    }

    // Méthodes abstraites à redéfinir dans les classes filles
    public abstract String getName();

    public abstract int getPrice();

    public abstract char getEcoScore();

    public abstract int getFabricationTime();

    public List<Component> getComponentsList() {
        return this.componentsList;
    }

}
