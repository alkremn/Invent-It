package com.invent.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SampleDataLoader {

    public static ObservableList<Part> loadSampleParts() {

        return FXCollections.observableArrayList(

                new InHouse(1, "Wheel", 2.99, 2, 1, 15, 34563),
                new InHouse(2, "Door", 4.99, 10, 1, 20, 12323),
                new InHouse(3, "Hood", 9.99, 5, 1, 80, 897343),
                new Outsourced(4, "Windshield", 5.99, 4, 2, 14, "Osd")

        );
    }

    public static ObservableList<Product> loadSampleProducts() {
        return FXCollections.observableArrayList(
                new Product(1, "Bike", 49.99, 4, 1, 5),
                new Product(2, "Boot", 149.99, 9, 1, 15),
                new Product(3, "Car", 549.99, 14, 1, 50),
                new Product(4, "Plane", 10000, 1, 1, 2)
        );
    }

}
