package com.invent.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SampleDataLoader {

    public static ObservableList<Part> loadSampleParts() {

        return FXCollections.observableArrayList(

                new InHouse("Wheel", 2.99, 2, 1, 15, 34563),
                new InHouse("Door", 4.99, 10, 1, 20, 12323),
                new InHouse("Hood", 9, 5, 1, 80, 897343),
                new Outsourced("Windshield", 5.99, 4, 2, 14, "Osd")

        );
    }

    public static ObservableList<Product> loadSampleProducts() {
        return FXCollections.observableArrayList(
                new Product("Bike", 49.99, 4, 1, 5),
                new Product( "Boot", 149.99, 9, 1, 15),
                new Product("Car", 549.00, 14, 1, 50),
                new Product( "Plane", 10000.00, 1, 1, 2)
        );
    }

}
