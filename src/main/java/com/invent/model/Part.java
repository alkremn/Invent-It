package com.invent.model;

import javafx.beans.property.*;

public abstract class Part implements Comparable {
    private static int counter = 0;
    private IntegerProperty partID;
    private StringProperty name;
    private DoubleProperty price;
    private IntegerProperty inStack;
    private IntegerProperty min;
    private IntegerProperty max;

    public Part(String name, double price, int inStack, int min, int max) {
        this.partID = new SimpleIntegerProperty(++counter);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.inStack = new SimpleIntegerProperty(inStack);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }

    public int getPartID() {
        return partID.get();
    }

    public IntegerProperty partIDProperty() {
        return partID;
    }

    public void setPartID(int partID) {
        this.partID.set(partID);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getInStack() {
        return inStack.get();
    }

    public IntegerProperty inStackProperty() {
        return inStack;
    }

    public void setInStack(int inStack) {
        this.inStack.set(inStack);
    }

    public int getMin() {
        return min.get();
    }

    public IntegerProperty minProperty() {
        return min;
    }

    public void setMin(int min) {
        this.min.set(min);
    }

    public int getMax() {
        return max.get();
    }

    public IntegerProperty maxProperty() {
        return max;
    }

    public void setMax(int max) {
        this.max.set(max);
    }

    public StringProperty toObservString(){
        String str = String.format("$%.2f",price.getValue());
        return new SimpleStringProperty(str);
    }

    public int compareTo(Object obj) {
        if (obj instanceof Part) {
            Part other = (Part) obj;
            if (this == other) {
                return 0;
            }
            return Integer.compare(this.getPartID(), other.getPartID());
        }
        return -1;
    }
}
