package com.invent.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Comparator;

public class InHouse extends Part {

    private IntegerProperty machineID;

    public InHouse(String name, double price, int inStack, int min, int max, int machineID) {
        super(name, price, inStack, min, max);
        this.machineID = new SimpleIntegerProperty(machineID);
    }

    public int getMachineID() {
        return machineID.get();
    }

    public IntegerProperty machineIDProperty() {
        return machineID;
    }

    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }


}
