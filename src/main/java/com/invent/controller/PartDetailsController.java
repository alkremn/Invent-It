package com.invent.controller;


import com.invent.MainApp;
import com.invent.model.InHouse;
import com.invent.model.Outsourced;
import com.invent.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.Window;


public class PartDetailsController {

    @FXML
    private Label mainLabel;

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

    private boolean isInhouse = false;

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    public void setPartFields(Part part){
        this.part = part;
        if(part != null){

            mainLabel.setText("Modify Part");
            partIdField.setText(part.partIDProperty().getValue().toString());
            partNameField.setText(part.nameProperty().getValue());
            partInvField.setText(part.inStackProperty().getValue().toString());
            partPriceField.setText(part.priceProperty().getValue().toString());
            partMaxField.setText(part.maxProperty().getValue().toString());
            partMinField.setText(part.minProperty().getValue().toString());

            if(part instanceof Outsourced) {
                companyMachineLabel.setText("Company Name");
                companyMachineField.setText(((Outsourced) part).companyNameProperty().getValue());
            }
            else {
                inHouse.setSelected(true);
                companyMachineLabel.setText("Machine ID");
                companyMachineField.setText(((InHouse)part).machineIDProperty().getValue().toString());
                isInhouse = true;
            }

        }
        else{
            mainLabel.setText("Add Part");
        }
    }

    @FXML
    void partSaveHandler(ActionEvent event) {

        if(isInputValid()){

            String name = partNameField.getText();
            int inv = Integer.parseInt(partInvField.getText());
            double price = Double.parseDouble(partPriceField.getText());
            int max = Integer.parseInt(partMaxField.getText());
            int min = Integer.parseInt(partMinField.getText());

            int id;
            if(part != null) {
                part.nameProperty().setValue(name);
                part.inStackProperty().setValue(inv);
                part.priceProperty().setValue(price);
                part.maxProperty().setValue(max);
                part.minProperty().setValue(min);
                if (isInhouse){
                    ((InHouse)part).machineIDProperty().setValue(Integer.parseInt(companyMachineField.getText()));
                }
                else{
                    ((Outsourced)part).companyNameProperty().setValue(companyMachineField.getText());
                }
            }else {
                id = mainApp.getInventory().getAllParts().size() + 1;

                if (isInhouse) {
                    int machineId = Integer.parseInt(companyMachineField.getText());
                    mainApp.getInventory().addPart(new InHouse(id,
                            name, price, inv, max, min, machineId));
                } else {
                    String companyName = companyMachineField.getText();
                    mainApp.getInventory().addPart(new Outsourced(id,
                            name, price, inv, max, min, companyName));
                }
            }
            isSaveClicked = true;
            detailStage.close();
        }

    }
    @FXML
    void inHouseButtonHandler(ActionEvent event){
        companyMachineLabel.setText("Machine ID");
        companyMachineField.setPromptText("Mach ID");

    }
    @FXML
    void outSourcedButtonHandler(ActionEvent event){
        companyMachineLabel.setText("Company Name");
        companyMachineField.setPromptText("Comp Nm");
    }



    @FXML
    void cancelButtonHandler(ActionEvent event) {
        detailStage.close();
    }

    public void setStage(Stage detailsStage){
        this.detailStage = detailsStage;
    }

    private boolean isInputValid(){

        StringBuilder error = new StringBuilder();

        //validate name field
        if(partNameField.getText() == null || partNameField.getText().length() == 0){
            error.append("Invalid part name!\n");
        }
        //validate Inv field
        if(partInvField.getText() == null || partInvField.getText().length() == 0){
            error.append("Invalid part Inv!\n");
        } else{
            //try to parse it to integer
            try{
                Integer.parseInt(partInvField.getText());
            }
            catch (NumberFormatException e){
                error.append("Invalid Inv count(Must be an integer)!\n");
            }
        }
        //validate price field
        if(partPriceField.getText() == null || partPriceField.getText().length() == 0){
            error.append("Invalid part price\n");
        } else{
            try{
                Double.parseDouble(partInvField.getText());
            }
            catch (NumberFormatException e) {
                error.append("Invalid part price(Must be an double)!\n");
            }
        }
        //validate max field
        if (partMaxField.getText() == null || partMaxField.getText().length() == 0){
            error.append("Invalid Max number!\n");
        }
        else{
            try{
                Integer.parseInt(partMaxField.getText());
            }catch (NumberFormatException e){
                error.append("Max number must be a integer!\n");
            }
        }
        //validate min field
        if (partMinField.getText() == null || partMinField.getText().length() == 0){
            error.append("Invalid Min number!\n");
        }
        else{
            try{
                Integer.parseInt(partMinField.getText());
            }catch (NumberFormatException e){
                error.append("Min number must be a integer!\n");
            }
        }
        if(companyMachineField.getText() == null || companyMachineField.getText().length() == 0){
            if(isInhouse){
                error.append("Invalid Machine ID!\n");
            }
            else {
                error.append("Invalid Company Name!\n");
            }
        }
        if(error.length() == 0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid fields");
            alert.setContentText(error.toString());
            alert.showAndWait();
            return false;
        }
    }

    @FXML
    void initialize() {
        outSourced.setSelected(true);
    }
}