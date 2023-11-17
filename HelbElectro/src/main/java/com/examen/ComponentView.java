package com.examen;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;

import javafx.stage.Stage;

public class ComponentView implements ComponentObserver {

    private ComponentController componentController;

    private Button componentButton;

    public ComponentView(ComponentController componentController) {
        this.componentController = componentController;
        this.componentController.addObserver(this); // On ajoute cette vue comme observateurs du controlleur
    }

    @Override
    public void update(List<Component> myComponentsList) {
        for (int i = 0; i < ComponentController.getMaxAvailableComponentEmplacement(); i++) {
            Button componentButton = (Button) ComponentController.componentsButtonVBox.getChildren().get(i);
            updateButton(i, myComponentsList, componentButton);
        }
    }

    public VBox createComponentsArea(List<Component> myComponentsList, int maxAvailableComponentEmplacement) {
        VBox componentVBox = new VBox();
        componentVBox.setSpacing(10);

        for (int i = 0; i < maxAvailableComponentEmplacement; i++) {
            Button componentButton = createButtonForComponent(i, myComponentsList);
            componentVBox.getChildren().add(componentButton);
        }

        return componentVBox;
    }

    private Button createButtonForComponent(int index, List<Component> myComponentsList) {
        componentButton = new Button();
        componentButton.setMinSize(100, 30);

        componentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // Les boutons qui contiennent des composants
                if (index < myComponentsList.size()) {

                    // On récupere nos composants de la liste grace à l'index passé en parametre
                    Component component = myComponentsList.get(index);

                    // Infos Emplacement
                    Label labelEmplacement = new Label("Emplacement : " + (index + 1));

                    // Infos Status
                    Label labelStatus = new Label("Status : occupé");

                    // Infos composants
                    Label labelName = new Label("Composant : " + component.getName());

                    VBox vbox = new VBox(10, labelEmplacement, labelStatus, labelName);
                    vbox.setAlignment(Pos.CENTER);

                    // Si c'est une instance de C1 on affiche ses infos spécifiques
                    if (component instanceof C1) {
                        Label labelBattrC1 = new Label("Batterie : " + ((C1) component).getBatteryPercentage() + "%");
                        vbox.getChildren().add(labelBattrC1);
                    }

                    // Si c'est une instance de C2 on affiche ses infos spécifiques
                    if (component instanceof C2) {
                        Label labelRangeC2 = new Label("Portée : " + ((C2) component).getRange() + "m");
                        Label labelColorC2 = new Label("Couleur : " + ((C2) component).getColor());
                        vbox.getChildren().add(labelRangeC2);
                        vbox.getChildren().add(labelColorC2);
                    }

                    // Si c'est une instance de C3 on affiche ses infos spécifiques
                    if (component instanceof C3) {
                        Label labelPowerC3 = new Label("Power : " + ((C3) component).getPower() + "W");
                        vbox.getChildren().add(labelPowerC3);
                    }

                    // on creer notre fnetre
                    Stage componentWindow = new Stage();
                    Scene scene = new Scene(vbox, 300, 300);

                    componentWindow.setTitle(component.getName() + " informations");
                    componentWindow.setScene(scene);

                    // On affiche la fenetre a l'ecran
                    componentWindow.show();

                } else {
                    // L'emplacement est libre µ

                    // Infos Emplacement
                    Label labelEmplacement = new Label("Emplacement : " + (index + 1));

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
        return componentButton;
    }

    public void updateButton(int index, List<Component> myComponentsList, Button componentButton) {
        if (index < myComponentsList.size()) {
            Component component = myComponentsList.get(index);

            if (component.getName().equals("Batterie")) {
                componentButton.setText("C1");
                componentButton.setStyle("-fx-background-color: #00BFFF"); // Bleue
            }
            if (component.getName().equals("Capteur")) {
                componentButton.setText("C2");
                componentButton.setStyle("-fx-background-color: #00FF00;"); // Vert
            }
            if (component.getName().equals("Moteur")) {
                componentButton.setText("C3");
                componentButton.setStyle("-fx-background-color: #FF00FF;"); // Mauve
            }
        } else {
            // Si le composant n'existe pas encore, réinitialisez le bouton
            componentButton.setText("");
            componentButton.setStyle("");
        }
    }

}
