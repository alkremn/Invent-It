package com.invent.controller;

import com.invent.MainApp;
import com.invent.model.Part;
import com.invent.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Collections;

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
    private TableColumn<Part, String> partPriceA;

    @FXML
    private TableView<Part> productPartsTable;

    @FXML
    private TableColumn<Part, Integer> partIdB;

    @FXML
    private TableColumn<Part, String> partNameB;

    @FXML
    private TableColumn<Part, Integer> partInvB;

    @FXML
    private TableColumn<Part, String> partPriceB;

    @FXML
    private TextField partSearchField;


    private MainApp mainApp;

    private Product product;

    private Stage detailStage;

    private ObservableList<Part> tempAvailableParts;

    private ObservableList<Part> tempProductParts;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }


    @FXML
    void initialize() {

        //setting up Available parts table
        partIdA.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameA.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvA.setCellValueFactory((cellData -> cellData.getValue().inStackProperty().asObject()));
        partPriceA.setCellValueFactory(cellData -> cellData.getValue().toObservString());

        //setting up Product parts table
        partIdB.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameB.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvB.setCellValueFactory((cellData -> cellData.getValue().inStackProperty().asObject()));
        partPriceB.setCellValueFactory(cellData -> cellData.getValue().toObservString());

    }

    public void setProductFields(Product product) {
        this.product = product;

        //setting up products fields
        if (product != null) {
            productIdField.setText(product.productIDProperty().getValue().toString());
            productNameField.setText(product.nameProperty().getValue());
            productInvField.setText(product.inStockProperty().getValue().toString());
            productPriceField.setText(product.priceProperty().getValue().toString());
            productMaxField.setText(product.maxProperty().getValue().toString());
            productMinField.setText(product.minProperty().getValue().toString());

            tempAvailableParts = getAvailableParts(product.getAssociatedParts());
            tempProductParts = product.getAssociatedParts();

            availablePartsTable.setItems(tempAvailableParts);
            productPartsTable.setItems(tempProductParts);

        } else {
            tempAvailableParts = getAvailableParts(null);
            tempProductParts = FXCollections.observableArrayList();

            productLabel.setText("Modify Product");
            availablePartsTable.setItems(tempAvailableParts);
            productPartsTable.setItems(tempProductParts);
        }

    }

    public void setProductStage(Stage detailStage) {
        this.detailStage = detailStage;
    }

    @FXML
    void AddPartHandler(ActionEvent event) {
        int index = availablePartsTable.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();
            tempProductParts.add(selectedPart);
            tempAvailableParts.remove(selectedPart);
            availablePartsTable.getSelectionModel().clearSelection();
            Collections.sort(tempProductParts);
        } else {
            mainApp.showAlertMessage("No Part selected", "please, Select available part in the table");
        }
    }

    @FXML
    void deletePartHandler(ActionEvent event) {
        int index = productPartsTable.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Part part = productPartsTable.getSelectionModel().getSelectedItem();
            tempAvailableParts.add(part);
            tempProductParts.remove(part);
            productPartsTable.getSelectionModel().clearSelection();
            Collections.sort(tempAvailableParts);
        } else {
            mainApp.showAlertMessage("No Part selected", "please, Select available part in the table");
        }
    }

    @FXML
    void ProductSaveHandler(ActionEvent event) {
        if (isInputValid()) {

            String name = productNameField.getText();
            int inv = Integer.parseInt(productInvField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            int max = Integer.parseInt(productMaxField.getText());
            int min = Integer.parseInt(productMinField.getText());

            int id;
            if (product != null) {
                product.nameProperty().setValue(name);
                product.inStockProperty().setValue(inv);
                product.priceProperty().setValue(price);
                product.maxProperty().setValue(max);
                product.minProperty().setValue(min);
               // product.addPartList(tempPartsListB);
            } else {
                id = mainApp.getInventory().getAllProducts().size() + 1;
                Product newProduct = new Product(id, name, price, inv, min, max);
                //newProduct.getAssociatedParts().addAll(tempPartsListB);
                mainApp.getInventory().getAllProducts().add(newProduct);
            }
            detailStage.close();
        }

    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        detailStage.close();
    }



    private boolean isInputValid() {

        //TODO: implement additional numeric boundaries check.

        StringBuilder errorMessage = new StringBuilder();
        //validate name input
        if (productNameField.getText() == null || productNameField.getText().length() == 0) {
            errorMessage.append("Name field cannot be empty!\n");
        }
        //validate Inventory input
        if (productInvField.getText() == null || productInvField.getText().length() == 0) {
            errorMessage.append("Inventory field cannot be empty!\n");
        } else {
            //try to parse it to integer
            try {
                Integer.parseInt(productInvField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Inventory count must be an integer!\n");
            }
        }
        //validate price input
        if (productPriceField.getText() == null || productPriceField.getText().length() == 0) {
            errorMessage.append("Price field cannot be empty!\n");
        } else {
            //try to parse it to double
            try {
                Double.parseDouble(productPriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Price must be an integer!\n");
            }
        }
        //validate maximum input
        if (productMaxField.getText() == null || productMaxField.getText().length() == 0) {
            errorMessage.append("Maximum field cannot be empty!\n");
        } else {
            //try to parse it to integer
            try {
                Integer.parseInt(productMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Maximum must be an integer!\n");
            }
        }
        //validate minimum input
        if (productMinField.getText() == null || productMinField.getText().length() == 0) {
            errorMessage.append("Minimum field cannot be empty!\n");
        } else {
            //try to parse it to integer
            try {
                Integer.parseInt(productMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Minimum must be an integer!\n");
            }
        }
        if (product.getAssociatedParts().isEmpty()) {
            errorMessage.append("Part list cannot be Empty");
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fields");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return false;
        }
    }
    //creates list of unique parts available for user to add to the product
    private ObservableList<Part> getAvailableParts(ObservableList<Part> productParts) {
        ObservableList<Part> availableParts = FXCollections.observableArrayList();
            if(productParts != null && !productParts.isEmpty()){
                for(Part availPart : mainApp.getInventory().getAllParts()){
                    Part foundPart = productParts.stream().filter(prodPart ->
                            prodPart.getPartID() == availPart.getPartID()).findFirst().orElse(null);

                    if(foundPart == null){
                        availableParts.add(availPart);
                    }
                }
            } else {
                availableParts.addAll(mainApp.getInventory().getAllParts());
            }
        return availableParts;
    }

    //Allows user to search for specific part in available parts list
    @FXML
    void partSearchHandler(ActionEvent event) {
        String searchWord = partSearchField.getText();

        ObservableList<Part> foundProducts = FXCollections.observableArrayList();

        if (searchWord != null && !searchWord.isEmpty()) {
            for (Part availablePart : getAvailableParts(tempProductParts)) {
                if (availablePart.getName().toLowerCase().startsWith(searchWord.toLowerCase())) {
                    foundProducts.add(availablePart);
                }
            }
            tempAvailableParts = foundProducts;
        } else {
            tempAvailableParts = FXCollections.observableArrayList(getAvailableParts(tempProductParts));
        }
        availablePartsTable.setItems(tempAvailableParts);
    }
}
