package com.examen;

import java.util.List;

public interface ProductObserver {

    // Mettre à jour l'affichage des produits
    void update(List<Product> myProductsList);
}
