package com.examen;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.stream.Collectors;

public class StrategyLucrative extends StrategyBase {

    private ProductController productController;
    private ComponentController componentController;

    public StrategyLucrative(ProductController productController, ComponentController componentController) {
        super(productController, componentController);
        this.productController = productController;
        this.componentController = componentController;
    }

    @Override
    public void startProduction() {
        Map<String, Integer> productPrices = new HashMap<>();

        // On récupère les valeurs statiquement au lieu d'instancier les produits
        productPrices.put(Drone.name, Drone.price);
        productPrices.put(Robot.name, Robot.price);
        productPrices.put(Voiture.name, Voiture.price);
        productPrices.put(Alarme.name, Alarme.price);
        productPrices.put(Moteur.name, Moteur.price);
        productPrices.put(Capteur.name, Capteur.price);
        productPrices.put(Batterie.name, Batterie.price);

        List<Map.Entry<String, Integer>> highestPriceList = productPrices.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> entry : highestPriceList) {
            Product highestPriceProduct = null;
            switch (entry.getKey()) {
                case "Drone":
                    if (isDroneOk()) {
                        highestPriceProduct = new Drone(removeComponents(new C1(), new C2(), new C3()));
                    }
                    break;
                case "Robot":
                    if (isRobotOk()) {
                        highestPriceProduct = new Robot(removeComponents(new C2(), new C3()));
                    }
                    break;
                case "Voiture":
                    if (isVoitureOk()) {
                        highestPriceProduct = new Voiture(removeComponents(new C1(), new C3()));
                    }
                    break;
                case "Alarme":
                    if (isAlarmeOk()) {
                        highestPriceProduct = new Alarme(removeComponents(new C1(), new C2()));
                    }
                    break;
                case "Moteur":
                    if (isMoteurOk()) {
                        highestPriceProduct = new Moteur(removeComponents(new C3()));
                    }
                    break;
                case "Capteur":
                    if (isCapteurOk()) {
                        highestPriceProduct = new Capteur(removeComponents(new C2()));
                    }
                    break;
                case "Batterie":
                    if (isBatterieOk()) {
                        highestPriceProduct = new Batterie(removeComponents(new C1()));
                    }
                    break;
            }

            if (highestPriceProduct != null) {
                productController.myProductsList.add(highestPriceProduct);
                break;
            }
        }

        // Mettre à jour l'affichage des composants et des produits
        componentController.myComponentsListChanged();
        productController.myProductsListChanged();
    }
}
