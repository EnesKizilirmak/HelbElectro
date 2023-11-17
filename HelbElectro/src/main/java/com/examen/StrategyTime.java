package com.examen;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

import java.util.stream.Collectors;

public class StrategyTime extends StrategyBase {

    private ProductController productController;
    private ComponentController componentController;

    public StrategyTime(ProductController productController, ComponentController componentController) {
        super(productController, componentController);
        this.productController = productController;
        this.componentController = componentController;
    }

    @Override
    public void startProduction() {
        Map<String, Integer> productFabricationTimes = new HashMap<>();

        // On récupère les valeurs statiquement au lieu d'instancier les produits
        productFabricationTimes.put(Drone.name, Drone.fabricationTime);
        productFabricationTimes.put(Robot.name, Robot.fabricationTime);
        productFabricationTimes.put(Voiture.name, Voiture.fabricationTime);
        productFabricationTimes.put(Alarme.name, Alarme.fabricationTime);
        productFabricationTimes.put(Moteur.name, Moteur.fabricationTime);
        productFabricationTimes.put(Capteur.name, Capteur.fabricationTime);
        productFabricationTimes.put(Batterie.name, Batterie.fabricationTime);

        List<Map.Entry<String, Integer>> shortestFabricationTimeList = productFabricationTimes.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        for (Map.Entry<String, Integer> entry : shortestFabricationTimeList) {
            Product shortestFabricationTimeProduct = null;
            switch (entry.getKey()) {
                case "Drone":
                    if (isDroneOk()) {
                        shortestFabricationTimeProduct = new Drone(removeComponents(new C1(), new C2(), new C3()));
                    }
                    break;
                case "Robot":
                    if (isRobotOk()) {
                        shortestFabricationTimeProduct = new Robot(removeComponents(new C2(), new C3()));
                    }
                    break;
                case "Voiture":
                    if (isVoitureOk()) {
                        shortestFabricationTimeProduct = new Voiture(removeComponents(new C1(), new C3()));
                    }
                    break;
                case "Alarme":
                    if (isAlarmeOk()) {
                        shortestFabricationTimeProduct = new Alarme(removeComponents(new C1(), new C2()));
                    }
                    break;
                case "Moteur":
                    if (isMoteurOk()) {
                        shortestFabricationTimeProduct = new Moteur(removeComponents(new C3()));
                    }
                    break;
                case "Capteur":
                    if (isCapteurOk()) {
                        shortestFabricationTimeProduct = new Capteur(removeComponents(new C2()));
                    }
                    break;
                case "Batterie":
                    if (isBatterieOk()) {
                        shortestFabricationTimeProduct = new Batterie(removeComponents(new C1()));
                    }
                    break;
            }

            if (shortestFabricationTimeProduct != null) {
                productController.myProductsList.add(shortestFabricationTimeProduct);
                break;
            }
        }

        // Mettre à jour l'affichage des composants et des produits
        componentController.myComponentsListChanged();
        productController.myProductsListChanged();
    }
}
