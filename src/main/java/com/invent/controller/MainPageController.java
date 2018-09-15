package com.invent.controller;

import com.invent.MainApp;
import com.invent.model.Part;
import com.invent.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainPageController {


    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    @FXML
    private TableColumn<Part, String> partNameColumn;

    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    @FXML
    private TableColumn<Product, String> productNameColumn;

    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;

    @FXML
    private TableColumn<Product, Double> productPriceColumn;


    @FXML
    private TextField partSearchField;

    @FXML
    private TextField searchProductField;

    private MainApp mainApp;


    //Default Constructor
    public MainPageController(){}

    @FXML
    void initialize() {
        partIdColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().inStackProperty().asObject());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        productIdColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().inStockProperty().asObject());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());


    }

    public void setMain(MainApp mainApp){
        this.mainApp = mainApp;

        partTableView.setItems(mainApp.getInventory().getAllParts());
        productTableView.setItems(mainApp.getInventory().getAllProducts());
    }



    @FXML
    void addPartButtonHandler(ActionEvent event) {
        mainApp.showPartWindow(null);
    }

    @FXML
    void addProductButtonHandler(ActionEvent event) {
        mainApp.showProductWindow(null);

    }

    @FXML
    void deletePartButtonHandler(ActionEvent event) {
        int index = partTableView.getSelectionModel().getSelectedIndex();

        if(index >= 0) {
            mainApp.getInventory().getAllParts().remove(index);
            partTableView.getSelectionModel().clearSelection();
        }
        else{
            showAlertMessage("No Part selected", "please, Select the part in the table");
        }
    }

    @FXML
    void deleteProductButtonHandler(ActionEvent event) {
        int index = productTableView.getSelectionModel().getSelectedIndex();

        if(index >= 0) {
            mainApp.getInventory().getAllProducts().remove(index);
            productTableView.getSelectionModel().clearSelection();
        }
        else{
            showAlertMessage("No Part selected", "please, Select the part in the table");
        }
    }

    @FXML
    void modifyPartButtonHandler(ActionEvent event) {
        if(partTableView.getSelectionModel().getSelectedIndex() >= 0){
            Part selectedProduct = partTableView.getSelectionModel().getSelectedItem();
            if( selectedProduct != null){
                mainApp.showPartWindow(selectedProduct);
            }
        }else{
            showAlertMessage("No Part selected", "please, Select the part in the table");
        }
    }

    @FXML
    void modifyProductButtonHandler(ActionEvent event) {
        if(productTableView.getSelectionModel().getSelectedIndex() >= 0){
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if(selectedProduct != null){
                mainApp.showProductWindow(selectedProduct);
            }
        }else {
            showAlertMessage("No Product selected", "please, Select the product in the table");
        }
    }

    @FXML
    void partSearchButtonHandler(ActionEvent event) {

        //TODO: implement search part method
    }

    @FXML
    void searchProductButtonHandler(ActionEvent event) {

        //TODO: implement search product method

    }

    @FXML
    void ExitButtonHandler(ActionEvent event) {
        Window window = ((Node)event.getTarget()).getScene().getWindow();
        Stage stage = (Stage) window;
        stage.close();
    }

    private void showAlertMessage(final String header, final String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Selection Warning");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

}
