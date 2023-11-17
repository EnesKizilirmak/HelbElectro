package com.examen;

import java.util.List;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HelbElectroController {

    private static HelbElectroController instance; // instance statique pour le Singleton
    private HelbElectroView view; // La vue générale

    private ComponentController componentController; // Controlleur responsable des composants
    private ProductController productController; // Controlleur responsable des produits

    // Constructeur privé pour eviter l'instanciation depuis d'autres classes
    private HelbElectroController(HelbElectroView view) {

        this.view = view;

        componentController = ComponentController.getInstance(); // Singleton
        productController = ProductController.getInstance(); // Singleton

        setAction();
    }

    // On garantie ici que le controlleur ai une seule instance
    public static synchronized HelbElectroController getInstance(HelbElectroView view) {
        if (instance == null) {
            instance = new HelbElectroController(view);
        }
        return instance;
    }

    // ------>>> Methode qui gère les événements provenant de la vue <<<------ //
    private void setAction() {
        view.comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleComboBoxSelection(componentController.myComponentsList);
            }
        });
    }

    // ------>>> Choix de stratégie par rapport à la selection <<<------ //
    private void handleComboBoxSelection(List<Component> myComponentsList) {

        // Permet de planifier des taches asynchrone a des moments precises
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        ProductFactory productFactory = ProductFactory.getInstance("", productController, componentController);

        // Une fois que l'option est séléctionné on répéte cette tache automatiquement
        Runnable task = () -> {
            Platform.runLater(() -> {

                if (productController.myProductsList.size() < ProductController.getMaxAvailableProductEmplacement()) {

                    // ------>>> Il y a de la place ! <<<------ //
                    if (!myComponentsList.isEmpty()) {

                        // L'option selectionnée depuis le ComboBox de la vue
                        String selectedOption = view.comboBox.getSelectionModel().getSelectedItem();

                        if (selectedOption != null) {

                            if (selectedOption.equals("Optimisation temps")) { // Option temps choisi
                                productFactory.setCurrentStrategy("Time");
                                productFactory.executeStrategy();
                            }
                            if (selectedOption.equals("Optimisation écologique")) { // Option écologique choisi
                                productFactory.setCurrentStrategy("Ecoscore");
                                productFactory.executeStrategy();

                            }
                            if (selectedOption.equals("Optimisation lucrative")) { // Option prix plus eleve choisi
                                productFactory.setCurrentStrategy("Lucrative");
                                productFactory.executeStrategy();

                            }
                            if (selectedOption.equals("Optimisation diversité")) { // Option diversite choisi
                                productFactory.setCurrentStrategy("Diversite");
                                productFactory.executeStrategy();
                            }
                        }
                    }
                }
            });
        };
        // Tant que la liste de composent n'est pas vide on appel la productFactory
        if (!myComponentsList.isEmpty()) {

            int initialDelay = 5;

            int periodBetweenEachProduction = 5;
            executorService.scheduleAtFixedRate(task, initialDelay, periodBetweenEachProduction, TimeUnit.SECONDS);
        }
    }
}
