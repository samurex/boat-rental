package com.teslaboat.viewModels.reservation;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationForm {
    private String reservationCode;
    private double boatPrice;

    private List<EquipmentModel> equipment;

    public ConfigurationForm() {
        this.equipment = new ArrayList<EquipmentModel>();
    }

    public List<EquipmentModel> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<EquipmentModel> equipment) {
        this.equipment = equipment;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public double getBoatPrice() {
        return boatPrice;
    }

    public void setBoatPrice(double boatPrice) {
        this.boatPrice = boatPrice;
    }

    public ConfigurationForm(String reservationCode, double boatPrice) {
        this.reservationCode = reservationCode;
        this.equipment = new ArrayList<>();
        this.boatPrice = boatPrice;
    }
}
