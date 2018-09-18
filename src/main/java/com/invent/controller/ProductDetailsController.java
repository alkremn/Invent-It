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
    private Button cancelButton;


    @FXML
    private TextField partSearchField;


    private MainApp mainApp;

    private Product product;

    private Stage detailStage;

    private ObservableList<Part> tempPartsListA;

    private ObservableList<Part> tempPartsListB;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setProductFields(Product product) {
        this.product = product;
        tempPartsListA = FXCollections.observableArrayList();
        tempPartsListB = FXCollections.observableArrayList();
        if (product != null) {
            //setting up products fields
            productLabel.setText("Modify Product");
            productIdField.setText(product.productIDProperty().getValue().toString());
            productNameField.setText(product.nameProperty().getValue());
            productInvField.setText(product.inStockProperty().getValue().toString());
            productPriceField.setText(product.priceProperty().getValue().toString());
            productMaxField.setText(product.maxProperty().getValue().toString());
            productMinField.setText(product.minProperty().getValue().toString());

            tempPartsListB = FXCollections.observableArrayList(product.getAssociatedParts());
        }

        setPartTables();

        //setting up Available parts table
        partIdA.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameA.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvA.setCellValueFactory((cellData -> cellData.getValue().inStackProperty().asObject()));
        partPriceA.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        availablePartsTable.setItems(tempPartsListA);

        //setting up Product parts table
        partIdB.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameB.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partInvB.setCellValueFactory((cellData -> cellData.getValue().inStackProperty().asObject()));
        partPriceB.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        productPartsTable.setItems(tempPartsListB);
    }

    public void setProductStage(Stage detailStage) {
        this.detailStage = detailStage;
    }

    @FXML
    void AddPartHandler(ActionEvent event) {
        int index = availablePartsTable.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Part selectedPart = availablePartsTable.getSelectionModel().getSelectedItem();
            tempPartsListB.add(selectedPart);
            tempPartsListA.remove(selectedPart);
            availablePartsTable.getSelectionModel().clearSelection();
            Collections.sort(tempPartsListB);
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
                product.addPartList(tempPartsListB);
            } else {
                id = mainApp.getInventory().getAllProducts().size() + 1;
                Product newProduct = new Product(name, price, inv, min, max);
                newProduct.getAssociatedParts().addAll(tempPartsListB);
                mainApp.getInventory().getAllProducts().add(newProduct);
            }
            detailStage.close();
        }

    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        detailStage.close();
    }

    @FXML
    void deletePartHandler(ActionEvent event) {
        int index = productPartsTable.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Part part = productPartsTable.getSelectionModel().getSelectedItem();
            tempPartsListA.add(part);
            tempPartsListB.remove(part);
            productPartsTable.getSelectionModel().clearSelection();
            Collections.sort(tempPartsListA);
        } else {
            mainApp.showAlertMessage("No Part selected", "please, Select available part in the table");
        }
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
        if (tempPartsListB.isEmpty()) {
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

    private void setPartTables() {
        if (!tempPartsListB.isEmpty()) {
            for (Part part : mainApp.getInventory().getAllParts()) {
                Part foundPart = tempPartsListB.stream()
                        .filter(part1 -> part1.getPartID() == part.getPartID()).findFirst().orElse(null);
                if (foundPart == null) {
                    tempPartsListA.add(part);
                }
            }
        } else {
            tempPartsListA = FXCollections.observableArrayList(mainApp.getInventory().getAllParts());
        }
    }

    @FXML
    void partSearchHandler(ActionEvent event) {

        //TODO: Fix search problem. second search not searching from original list.
        String searchWord = partSearchField.getText();
        ObservableList<Part> foundList = FXCollections.observableArrayList();
        if (searchWord != null && !searchWord.isEmpty()) {
            for (Part part : tempPartsListA) {
                if (part.getName().toLowerCase().startsWith(searchWord.toLowerCase())) {
                    foundList.add(part);
                }

            }
            tempPartsListA = foundList;
        } else {
            setPartTables();
        }
        availablePartsTable.setItems(tempPartsListA);
    }


    @FXML
    void initialize() {
    }

}
