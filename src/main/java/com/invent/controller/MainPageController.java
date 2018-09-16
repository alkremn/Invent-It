package com.invent.controller;

import com.invent.MainApp;
import com.invent.model.Part;
import com.invent.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.util.Optional;

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
    private TextField searchPartField;

    @FXML
    private TextField searchProductField;

    private MainApp mainApp;

    private ObservableList<Part> tempPartList;

    private ObservableList<Product> tempProductList;


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

    }

    public void setAllFields(){
        tempPartList = FXCollections.observableArrayList(mainApp.getInventory().getAllParts());
        tempProductList = FXCollections.observableArrayList(mainApp.getInventory().getAllProducts());
        partTableView.setItems(tempPartList);
        productTableView.setItems(tempProductList);
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
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if(selectedPart != null) {
            mainApp.getInventory().getAllParts().remove(selectedPart);
            tempPartList.remove(selectedPart);
            partTableView.getSelectionModel().clearSelection();
        }
        else{
            mainApp.showAlertMessage("No Part selected", "please, Select the part in the table");
        }
    }

    @FXML
    void deleteProductButtonHandler(ActionEvent event) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if(selectedProduct != null) {
            mainApp.getInventory().getAllProducts().remove(selectedProduct);
            tempProductList.remove(selectedProduct);
            productTableView.getSelectionModel().clearSelection();
        }
        else{
            mainApp.showAlertMessage("No Product selected", "please, Select the product in the table");
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
            mainApp.showAlertMessage("No Part selected", "please, Select the part in the table");
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
            mainApp.showAlertMessage("No Product selected", "please, Select the product in the table");
        }
    }

    @FXML
    void partSearchButtonHandler(ActionEvent event) {
        String searchWord = searchPartField.getText();
        ObservableList<Part> foundList = FXCollections.observableArrayList();
        if(searchWord != null && !searchWord.isEmpty()){
            for (Part part : tempPartList) {
                if (part.getName().toLowerCase().startsWith(searchWord.toLowerCase())) {
                    foundList.add(part);
                }

            }
            tempPartList = foundList;
        }else{
            tempPartList = mainApp.getInventory().getAllParts();
        }
        partTableView.setItems(tempPartList);
    }

    @FXML
    void searchProductButtonHandler(ActionEvent event) {
        String searchWord = searchProductField.getText();

        ObservableList<Product> foundList = FXCollections.observableArrayList();
        if(searchWord != null && !searchWord.isEmpty()){
            for (Product product : tempProductList) {
                if (product.getName().toLowerCase().startsWith(searchWord.toLowerCase())) {
                    foundList.add(product);
                }

            }
            tempProductList = foundList;
        }else{
            tempProductList = mainApp.getInventory().getAllProducts();
        }
        productTableView.setItems(tempProductList);

    }

    @FXML
    void ExitButtonHandler(ActionEvent event) {
        Window window = ((Node)event.getTarget()).getScene().getWindow();
        Stage stage = (Stage) window;
        stage.close();
    }


}
