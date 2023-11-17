package com.examen;

import java.util.List;

public interface ComponentObserver {

    // Mettre à jour l'affichage des composants
    void update(List<Component> myComponentsList);
}
