package com.invent.controller;

import com.invent.MainApp;
import com.invent.model.InHouse;
import com.invent.model.Outsourced;
import com.invent.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PartDetailsController {

    @FXML
    private Label partLabel;

    @FXML
    private RadioButton inHouse;

    @FXML
    private RadioButton outSourced;

    @FXML
    private TextField partIdField;

    @FXML
    private TextField partNameField;

    @FXML
    private TextField partInvField;

    @FXML
    private TextField partPriceField;

    @FXML
    private TextField partMaxField;

    @FXML
    private TextField partMinField;

    @FXML
    private Label companyMachineLabel;

    @FXML
    private TextField companyMachineField;


    private MainApp mainApp;

    private Part part;

    private Stage detailStage;

    private boolean isSaveClicked = false;

    private boolean isInHouseClicked = false;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setPartFields(Part part) {
        this.part = part;

        if (part != null) {
            partLabel.setText("Modify Part");
            partIdField.setText(part.partIDProperty().getValue().toString());
            partNameField.setText(part.nameProperty().getValue());
            partInvField.setText(part.inStackProperty().getValue().toString());
            partPriceField.setText(part.priceProperty().getValue().toString());
            partMaxField.setText(part.maxProperty().getValue().toString());
            partMinField.setText(part.minProperty().getValue().toString());

            if (part instanceof Outsourced) {
                companyMachineLabel.setText("Company Name");
                companyMachineField.setText(((Outsourced) part).companyNameProperty().getValue());
            } else {
                inHouse.setSelected(true);
                companyMachineLabel.setText("Machine ID");
                companyMachineField.setText(((InHouse) part).machineIDProperty().getValue().toString());
            }
        }
    }

    @FXML
    void partSaveHandler(ActionEvent event) {
        if (isInputValid()) {
            int id;
            String name = partNameField.getText();
            int inv = Integer.parseInt(partInvField.getText());
            double price = Double.parseDouble(partPriceField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int min = Integer.parseInt(partMinField.getText());

            if (part != null) {
                id = part.getPartID();
            } else {
                id = mainApp.getInventory().getAllParts().size() + 1;
            }
            if (isInHouseClicked) {
                int machineId = Integer.parseInt(companyMachineField.getText());
                mainApp.getInventory().addPart(new InHouse(id, name, price, inv, min, max, machineId));
            } else {
                String companyName = companyMachineField.getText();
                mainApp.getInventory().addPart(new Outsourced(id, name, price, inv, min, max, companyName));
            }
            isSaveClicked = true;
            detailStage.close();
        }
    }

    @FXML
    void inHouseButtonHandler(ActionEvent event) {
        companyMachineLabel.setText("Machine ID");
        companyMachineField.setPromptText("Mach ID");
        isInHouseClicked = true;
    }

    @FXML
    void outSourcedButtonHandler(ActionEvent event) {
        companyMachineLabel.setText("Company Name");
        companyMachineField.setPromptText("Comp Nm");
        isInHouseClicked = false;
    }

    @FXML
    void cancelButtonHandler(ActionEvent event) {
        detailStage.close();
    }

    public void setPartStage(Stage detailsStage) {
        this.detailStage = detailsStage;
    }

    //Method is check if the input from the user is valid
    private boolean isInputValid() {
        StringBuilder errorMessage = new StringBuilder();

        //validate name field
        if (partNameField.getText() == null || partNameField.getText().length() == 0) {
            errorMessage.append("Name cannot be empty!\n");
        }
        //validate Inv field
        if (partInvField.getText() == null || partInvField.getText().length() == 0) {
            errorMessage.append("Inv cannot be empty!\n");
        } else {
            //try to parse it to integer
            try {
                Integer.parseInt(partInvField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Inv count must be an integer!\n");
            }
        }
        //validate price field
        if (partPriceField.getText() == null || partPriceField.getText().length() == 0) {
            errorMessage.append("Price cannot be empty\n");
        } else {
            try {
                Double.parseDouble(partPriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Price must be a double!\n");
            }
        }
        //validate max field
        if (partMaxField.getText() == null || partMaxField.getText().length() == 0) {
            errorMessage.append("Max number cannot be empty!\n");
        } else {
            try {
                Integer.parseInt(partMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Max number must be an integer!\n");
            }
        }
        //validate min field
        if (partMinField.getText() == null || partMinField.getText().length() == 0) {
            errorMessage.append("Min number cannot be empty!\n");
        } else {
            try {
                Integer.parseInt(partMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Min number must be an integer!\n");
            }
        }
        if (Integer.parseInt(partMinField.getText()) >= Integer.parseInt(partMaxField.getText())) {
            errorMessage.append("Max value should be bigger min value\n");
        }
        if (companyMachineField.getText() == null || companyMachineField.getText().length() == 0) {
            if (inHouse.isSelected()) {
                errorMessage.append("Machine ID cannot be empty!\n");
            } else {
                errorMessage.append("Company Name cannot be empty!\n");
            }
        } else if (inHouse.isSelected()) {
            try {
                Integer.parseInt(companyMachineField.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Machine ID must be an integer!\n");
            }
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

    public boolean isSaveClicked() {
        return this.isSaveClicked;
    }

    @FXML
    void initialize() {
        outSourced.setSelected(true);
    }
}