package com.invent.controller;

import com.invent.MainApp;
import com.invent.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    }

    public void setProductStage(Stage detailStage){
        this.detailStage = detailStage;
    }

    @FXML
    void AddPartHandler(ActionEvent event) {

    }

    @FXML
    void ProductSaveHandler(ActionEvent event) {

    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        detailStage.close();
    }

    @FXML
    void deletePartHandler(ActionEvent event) {

    }

    @FXML
    void partSearchHandler(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }

}
