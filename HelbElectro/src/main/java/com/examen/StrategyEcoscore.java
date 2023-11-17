package com.examen;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import java.util.stream.Collectors;

public class StrategyEcoscore extends StrategyBase {

    private ProductController productController;
    private ComponentController componentController;

    public StrategyEcoscore(ProductController productController, ComponentController componentController) {
        super(productController, componentController);
        this.productController = productController;
        this.componentController = componentController;
    }

    @Override
    public void startProduction() {

        Map<String, Character> productEcoScores = new HashMap<>();

        // On recup les valeurs statiquement comme ca pas d'instanciation inutile
        productEcoScores.put(Drone.name, Drone.ecoScore);
        productEcoScores.put(Robot.name, Robot.ecoScore);
        productEcoScores.put(Voiture.name, Voiture.ecoScore);
        productEcoScores.put(Alarme.name, Alarme.ecoScore);
        productEcoScores.put(Moteur.name, Moteur.ecoScore);
        productEcoScores.put(Capteur.name, Capteur.ecoScore);
        productEcoScores.put(Batterie.name, Batterie.ecoScore);

        List<Map.Entry<String, Character>> bestEcoscoreList = productEcoScores.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toList());

        for (Map.Entry<String, Character> entry : bestEcoscoreList) {
            Product bestEcoscoreProduct = null;
            switch (entry.getKey()) {
                case "Drone":
                    if (isDroneOk()) {
                        bestEcoscoreProduct = new Drone(removeComponents(new C1(), new C2(), new C3()));
                    }
                    break;
                case "Robot":
                    if (isRobotOk()) {
                        bestEcoscoreProduct = new Robot(removeComponents(new C2(), new C3()));
                    }
                    break;
                case "Voiture":
                    if (isVoitureOk()) {
                        bestEcoscoreProduct = new Voiture(removeComponents(new C1(), new C3()));
                    }
                    break;
                case "Alarme":
                    if (isAlarmeOk()) {
                        bestEcoscoreProduct = new Alarme(removeComponents(new C1(), new C2()));
                    }
                    break;
                case "Moteur":
                    if (isMoteurOk()) {
                        bestEcoscoreProduct = new Moteur(removeComponents(new C3()));
                    }
                    break;
                case "Capteur":
                    if (isCapteurOk()) {
                        bestEcoscoreProduct = new Capteur(removeComponents(new C2()));
                    }
                    break;
                case "Batterie":
                    if (isBatterieOk()) {
                        bestEcoscoreProduct = new Batterie(removeComponents(new C1()));
                    }
                    break;
            }

            if (bestEcoscoreProduct != null) {
                productController.myProductsList.add(bestEcoscoreProduct);
                break;
            }
        }

        // Mettre à jour l'affichage des composants et des produits
        componentController.myComponentsListChanged();
        productController.myProductsListChanged();
    }
}
