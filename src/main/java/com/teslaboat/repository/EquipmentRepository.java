package com.teslaboat.repository;

import com.teslaboat.model.db.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByLocationId(Long locationId);
}
