package com.teslaboat.model.db;

import javax.persistence.*;


@Entity
@Table(name = "boats")
public class Boat {

    private Long id;
    private BoatType boatType;
    private Location location;

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
    @JoinColumn(name = "boat_type_id", nullable = false)
    public BoatType getBoatType() {
        return boatType;
    }

    public void setBoatType(BoatType boatType) {
        this.boatType = boatType;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
