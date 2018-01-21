package com.teslaboat.viewModels.reservation;

public class EquipmentCheckoutModel {
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public EquipmentCheckoutModel(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
