package com.teslaboat.model.db;

import com.teslaboat.model.ReservationStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "reservations")
public class Reservation {

    private Long id;
    private String code;
    private ReservationStatus status;
    private Boat boat;
    private Location location;
    private Date reservationDate;
    private Date expirationDate;
    private Date cancellationDate;
    private double totalPrice;
    private double boatPrice;
    private String email;
    private String stripeRequestId;
    private Set<ReservationEquipment> reservationEquipment;

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "code", length = 255, unique = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "status", length = 255)
    @Enumerated(EnumType.STRING)
    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Column(name = "boat_price")
    public double getBoatPrice() {
        return boatPrice;
    }

    public void setBoatPrice(double boatPrice) {
        this.boatPrice = boatPrice;
    }

    @Column(name = "total_price")
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reservation_date")
    public Date getReservationDate() {
        return this.reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "cancellation_date")
    public Date getCancellationDate() {
        return this.cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expiration_date")
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @ManyToOne
    @JoinColumn(name = "boat_id", nullable = false)
    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @OneToMany(mappedBy = "reservation")
    public Set<ReservationEquipment> getReservationEquipment() {
        return reservationEquipment;
    }

    public void setReservationEquipment(Set<ReservationEquipment> reservationEquipment) {
        this.reservationEquipment = reservationEquipment;
    }

    @Column(name = "email", length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "stripe_request_id")
    public String getStripeRequestId() {
        return stripeRequestId;
    }

    public void setStripeRequestId(String stripeRequestId) {
        this.stripeRequestId = stripeRequestId;
    }
}
