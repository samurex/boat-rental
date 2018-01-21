package com.teslaboat.model.db;


import java.util.Set;
import javax.persistence.*;


@Entity
@Table(name = "equipment_types")
public class EquipmentType {

    private long id;
    private String name;
    private Set<Equipment> equipments;
    private Set<EquipmentTypePrice> equipmentTypePrices;

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

    @OneToMany(mappedBy = "equipmentType", cascade = CascadeType.ALL)
    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }

    @OneToMany(mappedBy = "equipmentType", cascade = CascadeType.ALL)
    public Set<EquipmentTypePrice> getEquipmentTypePrices() {
        return equipmentTypePrices;
    }

    public void setEquipmentTypePrices(Set<EquipmentTypePrice> equipmentTypePrices) {
        this.equipmentTypePrices = equipmentTypePrices;
    }
}
