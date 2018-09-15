package com.invent;

import com.invent.controller.PartDetailsController;
import com.invent.controller.MainPageController;
import com.invent.controller.ProductDetailsController;
import com.invent.model.Inventory;
import com.invent.model.Part;
import com.invent.model.Product;
import com.invent.model.SampleDataLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;

    private Inventory inventory;

    public MainApp(){
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
    private void initMainPageLayout(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fxml/MainPage.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("resources/css/style.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();

            MainPageController mainPageController = loader.getController();
            mainPageController.setMain(this);


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showPartWindow(Part part){
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

            if(part == null) {
                detailsWindow.setTitle("Add New Part");
            }
            else{
                detailsWindow.setTitle("Modify Part");
            }

            detailsWindow.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showProductWindow(Product product){
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

            if(product == null) {
                detailsWindow.setTitle("Add New Product");
            }
            else{
                detailsWindow.setTitle("Modify Product");
            }

            detailsWindow.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //TODO: DO I need this?
    // returns reference to the main stage
    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public Inventory getInventory(){
        return inventory;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
