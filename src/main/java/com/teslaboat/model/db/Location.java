package com.teslaboat.model.db;


import com.teslaboat.model.db.Boat;

import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "locations")
public class Location {

    private long id;
    private String name;
    private Set<Boat> boats;


    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name", length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "boatType", cascade = CascadeType.ALL)
    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }
}
