package com.teslaboat.model.db;


import java.util.Date;
import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "boat_types")
public class BoatType {

    private long id;
    private String name;
    private String description;
    private int seats;
    private Set<Boat> boats;
    private Set<BoatTypePrice> boatTypePrices;

    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "description", columnDefinition = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "name", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "seats")
    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @OneToMany(mappedBy = "boatType", cascade = CascadeType.ALL)
    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    @OneToMany(mappedBy = "boatType", cascade = CascadeType.ALL)
    public Set<BoatTypePrice> getBoatTypePrices() {
        return boatTypePrices;
    }

    public void setBoatTypePrices(Set<BoatTypePrice> boatTypePrices) {
        this.boatTypePrices = boatTypePrices;
    }
}
