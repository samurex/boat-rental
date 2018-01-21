package com.teslaboat.viewModels.reservation;

public class BoatTypeModel {
    private Long id;
    private String name;
    private String description;
    private int seats;
    private double price;

    public BoatTypeModel() {}
    public BoatTypeModel(Long id, String name, String description, int seats, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seats = seats;
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
