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
        Product foundProduct = allProducts.stream()
                .filter(listProduct -> listProduct.getProductID() == product.getProductID()).findFirst().orElse(null);
        if(foundProduct != null){
            int index = allProducts.indexOf(foundProduct);
            allProducts.remove(foundProduct);
            allProducts.add(index,foundProduct);
        } else {
            this.allProducts.add(foundProduct);
        }
    }
    public void removeProduct(final Product product){
        int index = allProducts.indexOf(product);
        allProducts.remove(index);
        for(int i = index; i < allProducts.size(); i++){
            allProducts.get(i).setProductID(i + 1);
        }
    }

    public void addPart(final Part part) {
        Part foundPart = allParts.stream().filter(listPart -> listPart.getPartID() == part.getPartID()).findFirst().orElse(null);
        if(foundPart != null){
            int index = allParts.indexOf(foundPart);
            allParts.remove(foundPart);
            allParts.add(index,part);
        } else {
            this.allParts.add(part);
        }
    }

    public void removePart(Part part){
        int index = allParts.indexOf(part);
        allParts.remove(index);
        for(int i = index; i < allParts.size(); i++){
            allParts.get(i).setPartID(i + 1);
        }
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
