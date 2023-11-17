package com.examen;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

public class DataReader {

    private List<Component> myComponentsList;

    private boolean continueReadingFile = true;
    private boolean isAlertShown = false;


    private int startLine = 0;
    private int currentLine = 0;
    private int lastReadLine = 0;

    public DataReader(List<Component> componentsList) {
        this.myComponentsList = componentsList;
    }

    // ------>>> Méthode de lecture de fichier <<<------ //
    public void readFile(String fileName) {

        System.out.println("\n\n\n\n\n\n\n // ------>>> Components are coming ... <<<------  // \n");
        try (BufferedReader myDataReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int accumulatedTime = 0;

            while (continueReadingFile && (line = myDataReader.readLine()) != null) {
                currentLine++;
                lastReadLine = currentLine;
                if (currentLine <= startLine) {
                    continue;
                }

                String[] delimitation = line.split(","); // Séparation d'informations avec la virgule

                if (delimitation.length >= 3) {
                    int arrivalTime = Integer.parseInt(delimitation[0]);
                    String name = delimitation[1];
                    Component component = null;

                    if ("Batterie".equals(name)) {
                        int batteryPercentage = Integer
                                .parseInt(delimitation[2].substring(0, delimitation[2].length() - 1));
                        component = new C1(batteryPercentage);
                    }

                    if ("Capteur".equals(name)) {
                        int range = Integer.parseInt(delimitation[2].substring(0, delimitation[2].length() - 1));
                        String color = delimitation[3];
                        component = new C2(range, color);
                    }

                    if ("Moteur".equals(name)) {
                        int power = Integer.parseInt(delimitation[2].substring(0, delimitation[2].length() - 1));
                        component = new C3(power);
                    }

                    if (component != null) {
                        final Component finalComponent = component;
                        accumulatedTime += arrivalTime;

                        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                // Si il n'y a plus de place disponible à dans la zone de composants
                                if (myComponentsList.size() >= ComponentController.getMaxAvailableComponentEmplacement()) {
                                    continueReadingFile = false;

                                    if (!isAlertShown) {
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                Alert alert = new Alert(AlertType.WARNING);
                                                alert.setTitle("Capacité maximale atteinte");
                                                alert.setHeaderText("Tous les emplacements de composants sont occupés !");
                                                alert.setContentText("Veuillez séléctionner un type de production pour libérer de l'espace");
                                                alert.showAndWait();
                                            }
                                        });
                                        isAlertShown = true;
                                    }
                                } else {
                                    myComponentsList.add(finalComponent);

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            int lastIndexOnList = myComponentsList.size() - 1;

                                            if (lastIndexOnList >= 0 && lastIndexOnList < myComponentsList.size()) {

                                                Button lastButton = (Button) ComponentController.componentsButtonVBox.getChildren().get(lastIndexOnList);
                                                ComponentController.componentView.updateButton(lastIndexOnList, myComponentsList, lastButton);
                                            }
                                        }
                                    });
                                    System.out.println("// ------>>> New " + finalComponent.getName() + " est arrivé! <<<------ //");
                                }
                            }
                        };

                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(accumulatedTime), eventHandler));
                        timeline.play();
                        lastReadLine = currentLine;
                    } else {
                        System.out.println("Invalid component type: " + name);
                    }
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
