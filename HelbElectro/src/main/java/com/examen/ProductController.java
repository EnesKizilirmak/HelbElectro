package com.examen;

import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

public class ProductController {

    // ------>>> Controlleur responsable de la logique des Produits <<<------ //
    private static ProductController instance; // instance statique pour le Singleton

    public ProductView productView; // Vue responsable de mes produits
    public static GridPane productGridPane; // Les boutons produits sont dans un gridpane

    private final static int rowSize = 4;
    private final static int colSize = 4;
    private final static int MaxAvailableProductEmplacement = rowSize * colSize;

    private Product selectedProduct;
    private int salesCount;

    private Button buttonStats;
    private Button buttonSell;

    public List<Product> myProductsList = new ArrayList<>();
    private List<ProductObserver> observers = new ArrayList<>(); // La liste des observeurs

    // Constructeur privé pour eviter l'instanciation depuis d'autres classes
    private ProductController() {

        productView = new ProductView(this);
        productGridPane = productView.createProductArea(myProductsList, rowSize, colSize);

        this.buttonStats = productView.getButtonStats();
        buttonStats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getStats();
            }
        });

        this.buttonSell = productView.getButtonSell();
        buttonSell.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printCassaTicket();
            }
        });

        checkIfEmplacementIsFull();
    }

    // On garantie ici que le controlleur ai une seule instance
    public static synchronized ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }

    public void addObserver(ProductObserver observer) {
        observers.add(observer); // On ajoute la vue dans la liste d'observeur !
    }

    // Notifie toutes mes vues
    private void notifyObservers() {
        for (ProductObserver observer : observers) {
            observer.update(myProductsList); // La vue met a jour l'affichage
        }
    }

    // On appel cette méthode à chaque fois qu'il y a un changement dans ma liste
    public void myProductsListChanged() {
        notifyObservers();
    }

    private void checkIfEmplacementIsFull() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (myProductsList.size() == MaxAvailableProductEmplacement) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            executorService.shutdown();

                            Alert alertMaxSize = new Alert(Alert.AlertType.WARNING);
                            alertMaxSize.setTitle("Alerte");
                            alertMaxSize.setHeaderText(null);
                            alertMaxSize.setContentText("Plus de place disponible ! \nAppuyez sur 'OK' pour déplacer les produits dans un autre entrepôt");

                            Optional<ButtonType> result = alertMaxSize.showAndWait();

                            if (result.isPresent() && result.get() == ButtonType.OK) {
                                for (int i = myProductsList.size() - 1; i >= 0; i--) {
                                    myProductsList.remove(i);
                                    myProductsListChanged();
                                }
                            }
                        }
                    });
                }
            }
        }, 0, 1, TimeUnit.SECONDS); // Vérifie toutes les secondes
    }

    private void printCassaTicket() {
        if (selectedProduct == null) {
            System.out.println("No product selected.");
            return;
        }
        try {
            String directoryName = "tickets"; // nom du repertoire a créer

            // si il existe pas déja on créer un nouveau
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
            }

            // Pour le nom du fichier
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");
            LocalDateTime now = LocalDateTime.now();

            // Pour le contenu du fichier
            DateTimeFormatter dtfInfile = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            LocalDateTime nowInFile = LocalDateTime.now();

            String fileName = "t_" + dtf.format(now) + ".txt"; // nom du fichier

            File file = new File(directoryName + "/" + fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("------------>>> HelbElectro Ticket de caisse <<<------------");
            bw.newLine();
            bw.write("Date et heure: " + dtfInfile.format(nowInFile));
            bw.newLine();
            bw.newLine();
            bw.write("Produit : " + selectedProduct.getName());
            bw.newLine();
            bw.write("Prix : " + selectedProduct.getPrice() + "€");
            bw.newLine();
            bw.write("Ecoscore : " + selectedProduct.getEcoScore());
            bw.newLine();

            // Si c'est une instance de Drone, on affiche ses infos spécifiques
            if (selectedProduct instanceof Drone) {
                Drone drone = (Drone) selectedProduct;
                bw.write("Batterie : " + drone.getBatteryPercentage() + "%");
                bw.newLine();
                bw.write("Portée : " + drone.getRange() + "m");
                bw.newLine();
                bw.write("Couleur : " + drone.getColor());
                bw.newLine();
                bw.write("Puissance : " + drone.getPower() + "W");
                bw.newLine();
            }

            // Si c'est une instance de Robot, on affiche ses infos spécifiques
            if (selectedProduct instanceof Robot) {
                Robot robot = (Robot) selectedProduct;

                bw.write("Portée : " + robot.getRange() + "m");
                bw.newLine();
                bw.write("Couleur : " + robot.getColor());
                bw.newLine();
                bw.write("Puissance : " + robot.getPower() + "W");
                bw.newLine();
            }

            // Si c'est une instance de Voiture, on affiche ses infos spécifiques
            if (selectedProduct instanceof Voiture) {
                Voiture voiture = (Voiture) selectedProduct;

                bw.write("Batterie : " + voiture.getBatteryPercentage() + "%");
                bw.newLine();
                bw.write("Puissance : " + voiture.getPower() + "W");
                bw.newLine();
            }

            // Si c'est une instance de Alarme, on affiche ses infos spécifiques
            if (selectedProduct instanceof Alarme) {
                Alarme alarme = (Alarme) selectedProduct;

                bw.write("Batterie : " + alarme.getBatteryPercentage() + "%");
                bw.newLine();
                bw.write("Portée : " + alarme.getRange() + "m");
                bw.newLine();
                bw.write("Couleur : " + alarme.getColor());
                bw.newLine();
            }

            // Si c'est une instance de Moteur, on affiche ses infos spécifiques
            if (selectedProduct instanceof Moteur) {
                Moteur moteur = (Moteur) selectedProduct;

                bw.write("Puissance : " + moteur.getPower() + "W");
                bw.newLine();
            }

            // Si c'est une instance de Capteur, on affiche ses infos spécifiques
            if (selectedProduct instanceof Capteur) {
                Capteur capteur = (Capteur) selectedProduct;

                bw.write("Portée : " + capteur.getRange() + "m");
                bw.newLine();
                bw.write("Couleur : " + capteur.getColor());
                bw.newLine();
            }

            // Si c'est une instance de Batterie, on affiche ses infos spécifiques
            if (selectedProduct instanceof Batterie) {
                Batterie batterie = (Batterie) selectedProduct;

                bw.write("Batterie : " + batterie.getBatteryPercentage() + "%");
                bw.newLine();
            }

            bw.newLine();
            bw.write("------------>>> HelbElectro vous remercie  ! <<<------------");

            bw.close();

            myProductsList.remove(selectedProduct); // On retire le produits vendu
            selectedProduct = null; // Il n'y a plus de selected produits

            myProductsListChanged();
            incrementSales(); // Ma méthode qui incrémente le nombre de ventes

            Alert alertTicket = new Alert(Alert.AlertType.INFORMATION);
            alertTicket.setTitle("Ticket produit");
            alertTicket.setHeaderText(null);
            alertTicket.setContentText("Votre ticket a été imprimé, à bientot !");
            alertTicket.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getStats() {
        productView.getStatsView();
    }

    public void setSelectedProduct(Product product) {
        this.selectedProduct = product;
    }

    public static int getRowSize() {
        return rowSize;
    }

    public static int getColSize() {
        return colSize;
    }

    public static int getMaxAvailableProductEmplacement() {
        return MaxAvailableProductEmplacement;
    }

    public int getSalesCount() {
        return this.salesCount;
    }

    public void incrementSales() {
        this.salesCount++;
    }
}
