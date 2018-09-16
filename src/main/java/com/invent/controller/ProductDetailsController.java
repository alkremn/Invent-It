package com.invent.controller;

import com.invent.MainApp;
import com.invent.model.Part;
import com.invent.model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ProductDetailsController {


    @FXML
    private Label productLabel;

    @FXML
    private TextField productIdField;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productInvField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField productMaxField;

    @FXML
    private TextField productMinField;

    @FXML
    private TableView<Part> availablePartsTable;

    @FXML
    private TableColumn<Part, Integer> partIdA;

    @FXML
    private TableColumn<Part, String> partNameA;

    @FXML
    private TableColumn<Part, Integer> partInvA;

    @FXML
    private TableColumn<Part, Double> partPriceA;

    @FXML
    private TableView<Part> productPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIdB;

    @FXML
    private TableColumn<Part, String> partNameB;

    @FXML
    private TableColumn<Part, Integer> partInvB;

    @FXML
    private TableColumn<Part, Double> partPriceB;


    @FXML
    private TextField productSearchField;


    private MainApp mainApp;

    private Product product;

    private Stage detailStage;


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    public void setProductFields(Product product){
        this.product = product;
        if (product != null) {

            productLabel.setText("Modify Product");
            productIdField.setText(product.productIDProperty().getValue().toString());
            productNameField.setText(product.nameProperty().getValue());
            productInvField.setText(product.inStockProperty().getValue().toString());
            productPriceField.setText(product.priceProperty().getValue().toString());
            productMaxField.setText(product.maxProperty().getValue().toString());
            productMinField.setText(product.minProperty().getValue().toString());
        }
        ObservableList<Part> availableParts = mainApp.getInventory().getAllParts();
        setPartFields(availableParts);
    }

    private void setPartFields(ObservableList<Part> parts){
        partIdA.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameA.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvA.setCellValueFactory((cellData -> cellData.getValue().inStackProperty().asObject()));
        partPriceA.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        availablePartsTable.setItems(parts);
    }

    private void setProductPartFields(ObservableList<Part> parts){

        //TODO: Fix that!!!

        partIdB.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameB.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvB.setCellValueFactory((cellData -> cellData.getValue().inStackProperty().asObject()));
        partPriceB.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        productPartsTable.setItems(parts);
    }


    public void setProductStage(Stage detailStage){
        this.detailStage = detailStage;
    }

    @FXML
    void AddPartHandler(ActionEvent event) {
        Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();
        product.setAssociatedParts(selectedPart);
    }

    @FXML
    void ProductSaveHandler(ActionEvent event) {
        //TODO: Implement save method
    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        detailStage.close();
    }

    @FXML
    void deletePartHandler(ActionEvent event) {
        //TODO: Implement delete method
    }

    @FXML
    void partSearchHandler(ActionEvent event) {
        //TODO: Implement Search method
    }

    @FXML
    void initialize() {

    }

}
