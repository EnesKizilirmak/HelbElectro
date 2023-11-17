package com.examen;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

public class ProductView implements ProductObserver {

    private ProductController productController;

    private Button productButton;

    private Button buttonStats;
    private Button buttonSell;

    public ProductView(ProductController productController) {
        this.productController = productController;
        this.productController.addObserver(this); // L'observeur c'est ma vue
    }

    // Méthode qui met à jour l'affichage des produits
    @Override
    public void update(List<Product> myProductsList) {
        int productIndex = 0;
        for (int x = 0; x < ProductController.getRowSize(); x++) {
            for (int y = 0; y < ProductController.getColSize(); y++) {
                Button productButton = (Button) ProductController.productGridPane.getChildren().get(productIndex);
                updateButton(productIndex, myProductsList, productButton);
                productIndex++;
            }
        }
    }

    public GridPane createProductArea(List<Product> myProductsList, int rowSize, int colSize) {

        GridPane productsAreaGridPane = new GridPane();
        productsAreaGridPane.setHgap(50);
        productsAreaGridPane.setVgap(50);
        productsAreaGridPane.setAlignment(Pos.CENTER);

        int productIndex = 0;

        for (int y = 0; y < rowSize; y++) {
            for (int x = 0; x < colSize; x++) {
                Button productButton = createButtonForProduct(productIndex, x, y, myProductsList);
                productsAreaGridPane.add(productButton, x, y);
                productIndex++;
            }
        }

        buttonStats = new Button("Voir les statistiques");
        buttonSell = new Button("Vendre le produit");

        return productsAreaGridPane;
    }

    private Button createButtonForProduct(int index, int x, int y, List<Product> myProductsList) {
        productButton = new Button();
        productButton.setMinSize(120, 55);

        productButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (index < myProductsList.size()) {

                    Product product = myProductsList.get(index);
                    productController.setSelectedProduct(product);

                    // Infos Emplacement
                    Label labelEmplacement = new Label("Emplacement : (" + y + ", " + x + ")");

                    // Infos Status
                    Label labelStatus = new Label("Status : occupé");

                    // Infos produits
                    Label labelName = new Label("Produit : " + product.getName());
                    Label labelPrice = new Label("Prix : " + product.getPrice() + "€");
                    Label labelEcoscore = new Label("Ecoscore : " + product.getEcoScore());

                    // On met les elements les un en dessous des autres
                    VBox vbox = new VBox(10, labelEmplacement, labelStatus, labelName, labelPrice, labelEcoscore);
                    vbox.setAlignment(Pos.CENTER);

                    // Si c'est une instance de Drone, on affiche ses infos spécifiques
                    if (product instanceof Drone) {
                        Drone drone = (Drone) product;

                        Label labelBattery = new Label("Batterie : " + drone.getBatteryPercentage() + "%");
                        Label labelRange = new Label("Portée : " + drone.getRange() + "m");
                        Label labelColor = new Label("Couleur : " + drone.getColor());
                        Label labelPower = new Label("Puissance : " + drone.getPower() + "W");

                        vbox.getChildren().addAll(labelBattery, labelRange, labelColor, labelPower);
                    }

                    // Si c'est une instance de Robot, on affiche ses infos spécifiques
                    if (product instanceof Robot) {
                        Robot robot = (Robot) product;

                        Label labelRange = new Label("Portée : " + robot.getRange() + "m");
                        Label labelColor = new Label("Couleur : " + robot.getColor());
                        Label labelPower = new Label("Puissance : " + robot.getPower() + "W");

                        vbox.getChildren().addAll(labelRange, labelColor, labelPower);
                    }

                    // Si c'est une instance de Voiture, on affiche ses infos spécifiques
                    if (product instanceof Voiture) {
                        Voiture voiture = (Voiture) product;

                        Label labelBattery = new Label("Batterie : " + voiture.getBatteryPercentage() + "%");
                        Label labelPower = new Label("Puissance : " + voiture.getPower() + "W");

                        vbox.getChildren().addAll(labelBattery, labelPower);
                    }

                    // Si c'est une instance de Alarme, on affiche ses infos spécifiques
                    if (product instanceof Alarme) {
                        Alarme alarme = (Alarme) product;

                        Label labelBattery = new Label("Batterie : " + alarme.getBatteryPercentage() + "%");
                        Label labelRange = new Label("Portée : " + alarme.getRange() + "m");
                        Label labelColor = new Label("Couleur : " + alarme.getColor());

                        vbox.getChildren().addAll(labelBattery, labelRange, labelColor);
                    }

                    // Si c'est une instance de Moteur, on affiche ses infos spécifiques
                    if (product instanceof Moteur) {
                        Moteur moteur = (Moteur) product;

                        Label labelPower = new Label("Puissance : " + moteur.getPower() + "W");

                        vbox.getChildren().addAll(labelPower);
                    }

                    // Si c'est une instance de Capteur, on affiche ses infos spécifiques
                    if (product instanceof Capteur) {
                        Capteur capteur = (Capteur) product;

                        Label labelRange = new Label("Portée : " + capteur.getRange() + "m");
                        Label labelColor = new Label("Couleur : " + capteur.getColor());

                        vbox.getChildren().addAll(labelRange, labelColor);
                    }

                    // Si c'est une instance de Batterie, on affiche ses infos spécifiques
                    if (product instanceof Batterie) {
                        Batterie batterie = (Batterie) product;

                        Label labelBattery = new Label("Batterie : " + batterie.getBatteryPercentage() + "%");

                        vbox.getChildren().addAll(labelBattery);
                    }

                    buttonStats.setStyle("-fx-background-color: #00BFFF");
                    buttonSell.setStyle("-fx-background-color: #00FF00");

                    vbox.getChildren().add(buttonStats);
                    vbox.getChildren().add(buttonSell);

                    Stage productWindow = new Stage();
                    Scene scene = new Scene(vbox, 400, 400);

                    productWindow.setTitle(product.getName() + " informations");
                    productWindow.setScene(scene);

                    productWindow.show();
                } else {

                    // Le bouton est libre/ pas occupé

                    // Infos Emplacement
                    Label labelEmplacement = new Label("Emplacement : (" + x + ", " + y + ")");

                    // Infos Status
                    Label labelStatus = new Label("Status : libre");
                    VBox vbox = new VBox(10, labelEmplacement, labelStatus);
                    vbox.setAlignment(Pos.CENTER);

                    Stage componentWindow = new Stage();
                    Scene scene = new Scene(vbox, 200, 100);

                    componentWindow.setScene(scene);

                    componentWindow.show();
                }
            }
        });

        return productButton;
    }

    public void updateButton(int index, List<Product> myProductsList, Button productButton) {
        if (index < myProductsList.size()) {
            Product product = myProductsList.get(index); // On récupere le produit avedc son index

            if (product.getName().equals("Batterie")) {
                productButton.setText("P1");
                productButton.setStyle("-fx-background-color: #00BFFF;"); // Bleue
            }
            if (product.getName().equals("Capteur")) {
                productButton.setText("P2");
                productButton.setStyle("-fx-background-color: #00FF00;"); // Vert
            }
            if (product.getName().equals("Moteur")) {
                productButton.setText("P3");
                productButton.setStyle("-fx-background-color: #FF00FF;"); // Mauve
            }
            if (product.getName().equals("Alarme")) {
                productButton.setText("P4");
                productButton.setStyle("-fx-background-color: #FF0000;"); // Rouge
            }
            if (product.getName().equals("Voiture")) {
                productButton.setText("P5");
                productButton.setStyle("-fx-background-color: #FF4500;"); // Orange
            }
            if (product.getName().equals("Robot")) {
                productButton.setText("P6");
                productButton.setStyle("-fx-background-color: #FFD700;"); // Jaune
            }
            if (product.getName().equals("Drone")) {
                productButton.setText("P7");
                productButton.setStyle("-fx-background-color: #808080;"); // Gris
            }

        } else {
            // Si le produit n'existe pas encore, réinitialisez le bouton
            productButton.setText("");
            productButton.setStyle("");
        }
    }

    public void getStatsView() {
        Stage stage = new Stage();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Statistiques");
        titleLabel.setStyle("-fx-font-size: 16pt; -fx-font-weight: bold;");
        vbox.getChildren().add(titleLabel);

        Label productQuantity = new Label("Nombre de produits : " + productController.myProductsList.size() + " sur " + ProductController.getMaxAvailableProductEmplacement() + " disponibles !");
        vbox.getChildren().add(productQuantity);

        Label salesQuantity = new Label("Nombre de produits vendu : " + productController.getSalesCount());
        vbox.getChildren().add(salesQuantity);

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Statistiques");

        stage.show();
    }

    public Button getButtonStats() { // Mon bouton qui permet de voir les stats d'un emplacement
        return buttonStats;
    }

    public Button getButtonSell() { // Bouton qui permet de vendre un produit
        return buttonSell;
    }
}
