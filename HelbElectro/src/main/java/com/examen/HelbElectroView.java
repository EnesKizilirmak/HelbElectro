package com.examen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelbElectroView {

    private Stage stage;
    public SplitPane mySplitPane;
    public ComboBox<String> comboBox;

    public HelbElectroView(Stage stage) {
        this.stage = stage;
        initUI();
    }

    private void initUI() {

        // Créer le ComboBox
        ObservableList<String> optionsOptimisation = FXCollections.observableArrayList(
                "Optimisation temps",
                "Optimisation écologique",
                "Optimisation lucrative",
                "Optimisation diversité");

        comboBox = new ComboBox<>(optionsOptimisation);
        comboBox.setPromptText("Sélectionnez une option");

        // Créer un objet pour pouvoir communiquer avec le contrôleur
        HelbElectroController.getInstance(this);

        // Ajouter le label "Zone Product" au-dessus du GridPane de stockage
        Label zoneProductLabel = new Label("Zone Product");
        VBox leftSideContainer = new VBox(10, zoneProductLabel, ProductController.productGridPane);
        leftSideContainer.setAlignment(Pos.TOP_CENTER);
        leftSideContainer.setPadding(new Insets(20));

        // Ajouter le label "Zone Components" et le ComboBox à un conteneur
        Label zoneComponentsLabel = new Label("Zone Component");

        VBox rightSideContainer = new VBox(30, zoneComponentsLabel, comboBox,ComponentController.componentsButtonVBox);
        rightSideContainer.setAlignment(Pos.TOP_CENTER);
        rightSideContainer.setPadding(new Insets(20));

        // Créer le SplitPane avec LeftSideContainer et RightSideContainer
        mySplitPane = new SplitPane(leftSideContainer, rightSideContainer);
        mySplitPane.setDividerPosition(0, 0.9);
        mySplitPane.setPrefHeight(500);

        Scene myScene = new Scene(mySplitPane);

        // ------>>> Partie Display Scene <<<------ //
        stage.setScene(myScene);
        stage.setTitle("Helb Electro");
        stage.show();
    }
}
