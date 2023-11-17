package com.examen;

public class ProductFactory {

    private static ProductFactory instance;
    private ProductController productController;
    private ComponentController componentController;
    IStrategy currentStrategy;

    private ProductFactory(String initialStrategyChoice, ProductController productController, ComponentController componentController) {
        this.productController = productController;
        this.componentController = componentController;
        
        setCurrentStrategy(initialStrategyChoice);
    }

    public static synchronized ProductFactory getInstance(String initialStrategyChoice, ProductController productController, ComponentController componentController) {
        if (instance == null) {
            instance = new ProductFactory(initialStrategyChoice, productController, componentController);
        }
        return instance;
    }

    public void executeStrategy() {
        if (this.currentStrategy != null) {
            this.currentStrategy.startProduction();
        }
    }

    public void setCurrentStrategy(String strategyChoice) {
        if (this.currentStrategy != null) {
            this.currentStrategy.stopProduction();
        }

        switch (strategyChoice) {
            case "Time":
                currentStrategy = new StrategyTime(productController, componentController);
                break;
            case "Ecoscore":
                currentStrategy = new StrategyEcoscore(productController, componentController);
                break;
            case "Lucrative":
                currentStrategy = new StrategyLucrative(productController, componentController);
                break;
            case "Diversite":
                currentStrategy = new StrategyDiversite(productController, componentController);
                break;
        }
    }
}
