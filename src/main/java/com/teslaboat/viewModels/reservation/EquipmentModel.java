package com.teslaboat.viewModels.reservation;

public class EquipmentModel {
    private Long id;
    private String name;
    private int quantity;
    private int availableQuantity;
    private double price;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String id) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public EquipmentModel() {}
    public EquipmentModel(Long id, String name, double price, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.availableQuantity = availableQuantity;
    }
}
