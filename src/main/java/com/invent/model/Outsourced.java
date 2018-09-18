package com.invent.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Outsourced extends Part {

    private StringProperty companyName;

    public Outsourced(String name, double price, int inStack,
                      int min, int max, String companyName) {
        super(name, price, inStack, min, max);
        this.companyName = new SimpleStringProperty(companyName);
    }


    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
}
