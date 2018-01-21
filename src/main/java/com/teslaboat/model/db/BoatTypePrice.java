package com.teslaboat.model.db;


import java.util.Date;
import javax.persistence.*;


@Entity
@Table(name = "boat_type_prices",
        uniqueConstraints={@UniqueConstraint(columnNames = {"boat_type_id", "date_from"})})
public class BoatTypePrice {

    private long id;
    private double price;
    private Date dateFrom;
    private BoatType boatType;

    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "date_from")
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @ManyToOne
    @JoinColumn(name = "boat_type_id", nullable = false)
    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
        this.boatType = boatType;
    }
}
