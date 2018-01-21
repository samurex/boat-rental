package com.teslaboat.model.db;

import javax.persistence.*;


@Entity
@Table(name = "equipment")
public class Equipment {

    private Long id;
    private EquipmentType equipmentType;
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
    @JoinColumn(name = "equipment_type_id", nullable = false)
    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
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
