package com.invent.model;


import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public Inventory() {
        this.allParts = FXCollections.observableArrayList();
        this.allProducts = FXCollections.observableArrayList();
    }

    public void addProduct(final Product product) {
        this.allProducts.add(product);
    }

    public void addPart(final Part part) {

        //TODO: figure out how to implement this.
        Part foundPart = allParts.stream().filter(listPart -> listPart.getPartID() == part.getPartID()).findFirst().orElse(null);
        if(foundPart != null){
            int index = allParts.indexOf(foundPart);
            allParts.remove(foundPart);
            allParts.add(index,part);
        }
        this.allParts.add(part);
    }

    public void addPartList(ObservableList<Part> parts) {
        this.allParts.addAll(parts);
    }

    public void addProductList(final ObservableList<Product> products) {
        this.allProducts.addAll(products);
    }

    public ObservableList<Part> getAllParts() {
        return new SimpleListProperty<>(allParts);
    }

    public ObservableList<Product> getAllProducts() {
        return new SimpleListProperty<>(allProducts);
    }
}
