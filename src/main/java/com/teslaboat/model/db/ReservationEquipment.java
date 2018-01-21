package com.teslaboat.model.db;

import javax.persistence.*;

@Entity
@Table(name = "reservation_equipment",
        uniqueConstraints={@UniqueConstraint(columnNames = {"equipment_id", "reservation_id"})})
public class ReservationEquipment {
    private Long id;
    private Reservation reservation;
    private Equipment equipment;
    private double price;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
