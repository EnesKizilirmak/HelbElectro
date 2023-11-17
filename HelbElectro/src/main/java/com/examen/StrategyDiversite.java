package com.examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StrategyDiversite extends StrategyBase {

    private ProductController productController;
    private ComponentController componentController;

    public StrategyDiversite(ProductController productController, ComponentController componentController) {
        super(productController, componentController);
        this.productController = productController;
        this.componentController = componentController;
    }

    @Override
    public void startProduction() {
        List<String> productTypes = Arrays.asList(Drone.name, Robot.name, Voiture.name, Alarme.name, Moteur.name, Capteur.name, Batterie.name);

        // On mélange pour avoir un produit random
        Collections.shuffle(productTypes);

        for (String productType : productTypes) {
            Product product = null;
            switch (productType) {
                case "Drone":
                    if (isDroneOk()) {
                        product = new Drone(removeComponents(new C1(), new C2(), new C3()));
                    }
                    break;
                case "Robot":
                    if (isRobotOk()) {
                        product = new Robot(removeComponents(new C2(), new C3()));
                    }
                    break;
                case "Voiture":
                    if (isVoitureOk()) {
                        product = new Voiture(removeComponents(new C1(), new C3()));
                    }
                    break;
                case "Alarme":
                    if (isAlarmeOk()) {
                        product = new Alarme(removeComponents(new C1(), new C2()));
                    }
                    break;
                case "Moteur":
                    if (isMoteurOk()) {
                        product = new Moteur(removeComponents(new C3()));
                    }
                    break;
                case "Capteur":
                    if (isCapteurOk()) {
                        product = new Capteur(removeComponents(new C2()));
                    }
                    break;
                case "Batterie":
                    if (isBatterieOk()) {
                        product = new Batterie(removeComponents(new C1()));
                    }
                    break;
            }

            if (product != null) {
                productController.myProductsList.add(product);
                break;
            }
        }

        // Mettre à jour l'affichage des composants et des produits
        componentController.myComponentsListChanged();
        productController.myProductsListChanged();
    }
}
