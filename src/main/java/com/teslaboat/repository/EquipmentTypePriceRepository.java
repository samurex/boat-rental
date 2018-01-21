package com.teslaboat.repository;

import com.teslaboat.model.db.EquipmentTypePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface EquipmentTypePriceRepository extends JpaRepository<EquipmentTypePrice, Long> {

    // TODO: rewire to custom query
    Optional<EquipmentTypePrice> findFirst1ByEquipmentTypeIdAndDateFromLessThanEqualOrderByDateFromDesc(Long equipmentTypeId, Date dateFrom);
}
