package com.examen;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;

public class ComponentController {

    // ------>>> Controlleur responsable de la logique des composants <<<------ //

    private static ComponentController instance; // instance statique pour le Singleton

    private static int maxAvailableComponentEmplacement = 8;

    public static ComponentView componentView; // Vue responsable de mes composants
    public static VBox componentsButtonVBox; // Les boutons sont dans un VBOX

    public List<Component> myComponentsList = new ArrayList<>();
    private List<ComponentObserver> observers = new ArrayList<>(); // La liste des observeurs

    // Principe de forte cohésion lecture des composants par ctrlr des composants

    // ------>>> Partie Lecture de fichier <<<------ //
    private DataReader dataReader;
    public static final String fileName = "/home/helb/Desktop/HelbElectro_KIZILIRMAK/HelbElectro/src/main/java/com/examen/HelbElectro.data.txt";

    // Constructeur privé pour eviter l'instanciation depuis d'autres classes
    private ComponentController() {

        componentView = new ComponentView(this);
        componentsButtonVBox = componentView.createComponentsArea(myComponentsList,maxAvailableComponentEmplacement);

        dataReader = new DataReader(myComponentsList);
        dataReader.readFile(fileName);
    }

    // On garantie ici que le controlleur ai une seule instance
    public static synchronized ComponentController getInstance() {
        if (instance == null) {
            instance = new ComponentController();
        }
        return instance;
    }

    public void addObserver(ComponentObserver observer) {
        observers.add(observer); // On ajoute les observeurs dans la liste
    }

    private void notifyObservers() {
        for (ComponentObserver observer : observers) {
            observer.update(myComponentsList); // La vue mets à jour l'affichage
        }
    }

    // Des que la liste des composants change on appel cette méthode
    public void myComponentsListChanged() {
        notifyObservers();
    }

    public static int getMaxAvailableComponentEmplacement() {
        return maxAvailableComponentEmplacement;
    }
}
