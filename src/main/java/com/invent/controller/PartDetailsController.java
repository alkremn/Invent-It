package com.invent.controller;


import com.invent.MainApp;
import com.invent.model.InHouse;
import com.invent.model.Inventory;
import com.invent.model.Outsourced;
import com.invent.model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
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

    private boolean isSaveClicked = false;

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
            }

        }
        else{
            mainLabel.setText("Add Part");
        }
    }

    @FXML
    void partSaveHandler(ActionEvent event) {

//        if(outSourced.isArmed()){
//            int id = inventory.getAllParts().size();
//            String name = partNameField.getText();
//            double price = Double.parseDouble(partPriceField.getText());
//            int inStack = Integer.parseInt(partInvField.getText());
//            int min = Integer.parseInt(partMinField.getText());
//            int max;
//            String company;
//            Part part = new Outsourced(id,name,price,inStack,)
//        }
//        else{
//
//        }
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
        Window window = ((Node)event.getTarget()).getScene().getWindow();
        Stage stage = (Stage) window;
        stage.close();

    }

    private boolean isInputValid(){
        StringBuilder error = new StringBuilder();

        if(partNameField.getText() == null || partNameField.getText().length() == 0){
            error.append("Invalid part name!\n");
        }
        if(partInvField.getText() == null || partInvField.getText().length() == 0){
            error.append("Invalid part Inv");
        } else{
            try{
                Integer.parseInt(partInvField.getText());
            }
            catch (NumberFormatException e){
                error.append("Invalid Inv count(Must be an integer)!\n");
            }
        }
        if(partPriceField.getText() == null || partPriceField.getText().length() == 0){
            error.append("Invalid part price");
        } else{
            try{
                Double.parseDouble(partInvField.getText());
            }
            catch (NumberFormatException e) {
                error.append("Invalid part price(Must be an double)!\n");
            }
        }
        //TODO: Finish implementing this method


        return false;
    }

    @FXML
    void initialize() {
        outSourced.setSelected(true);
    }
}