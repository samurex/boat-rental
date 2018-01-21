package com.teslaboat.viewModels.reservation;

import com.teslaboat.model.Currency;
import com.teslaboat.model.db.Reservation;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CheckoutForm {
    private double boatPrice;
    private double totalPrice;
    private int totalPriceInCents;
    private String boatTypeName;
    private List<EquipmentCheckoutModel> equipment;
    private String stripePublicKey;
    private String currency;

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getBoatPrice() {
        return boatPrice;
    }

    public String getBoatTypeName() {
        return boatTypeName;
    }

    public List<EquipmentCheckoutModel> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<EquipmentCheckoutModel> equipment) {
        this.equipment = equipment;
    }

    public String getStripePublicKey() {
        return stripePublicKey;
    }

    public String getCurrency() {
        return currency;
    }

    public int getTotalPriceInCents() {
        return totalPriceInCents;
    }

    public CheckoutForm(Reservation reservation, String stripePublicKey, String currency) {
        this.stripePublicKey = stripePublicKey;
        this.currency = currency;
        this.boatPrice = reservation.getBoatPrice();
        this.totalPrice = reservation.getTotalPrice();
        this.totalPriceInCents = (int)reservation.getTotalPrice() * 100;
        this.boatTypeName = reservation.getBoat().getBoatType().getName();
        this.equipment = reservation.getReservationEquipment()
            .stream()
            .map(e -> new EquipmentCheckoutModel(e.getEquipment().getEquipmentType().getName(), e.getPrice()))
            .sorted(Comparator.comparing(EquipmentCheckoutModel::getName))
            .collect(Collectors.toList());
    }
}
