package com.invent;

import com.invent.controller.MainPageController;
import com.invent.controller.PartDetailsController;
import com.invent.controller.ProductDetailsController;
import com.invent.model.Inventory;
import com.invent.model.Part;
import com.invent.model.Product;
import com.invent.model.SampleDataLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    private Inventory inventory;

    private MainPageController mainPageController;

    public MainApp() {
        inventory = new Inventory();

        inventory.addPartList(SampleDataLoader.loadSampleParts());
        inventory.addProductList(SampleDataLoader.loadSampleProducts());

    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Management System");

        initMainPageLayout();

    }

    //Sets up the main layout
    private void initMainPageLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/MainPage.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("resources/css/style.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

            mainPageController = loader.getController();
            mainPageController.setMain(this);
            mainPageController.setPartTableView(inventory.getAllParts());
            mainPageController.setProductTableView(inventory.getAllProducts());
            Parent root = loader.getRoot();
            root.requestFocus();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPartWindow(Part part) {
        try {
            //Load FXML file and create new Stage for Add Part Window
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/PartDetails.fxml"));

            Stage detailsWindow = new Stage();
            detailsWindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("resources/css/style.css").toExternalForm());
            detailsWindow.setScene(scene);

            PartDetailsController controller = loader.getController();
            controller.setMainApp(this);
            controller.setPartFields(part);
            controller.setPartStage(detailsWindow);

            if (part == null) {
                detailsWindow.setTitle("Add New Part");
            } else {
                detailsWindow.setTitle("Modify Part");
            }
            Parent root = loader.getRoot();
            root.requestFocus();
            detailsWindow.showAndWait();

            if(controller.isSaveClicked()){
                mainPageController.setPartTableView(inventory.getAllParts());
                mainPageController.setProductTableView(inventory.getAllProducts());
            }
            System.out.println(inventory.getAllParts().size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showProductWindow(Product product) {
        try {
            //Load FXML file and create new Stage for Add Product Window
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/ProductDetails.fxml"));

            Stage detailsWindow = new Stage();
            detailsWindow.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("resources/css/style.css").toExternalForm());
            detailsWindow.setScene(scene);

            ProductDetailsController controller = loader.getController();
            controller.setMainApp(this);
            controller.setProductFields(product);
            controller.setProductStage(detailsWindow);

            if (product == null) {
                detailsWindow.setTitle("Add New Product");
            } else {
                detailsWindow.setTitle("Modify Product");
            }
            Parent root = loader.getRoot();
            root.requestFocus();
            detailsWindow.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showAlertMessage(final String header, final String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selection Warning");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }


    public Inventory getInventory() {
        return inventory;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
