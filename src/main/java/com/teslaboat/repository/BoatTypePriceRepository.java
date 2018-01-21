package com.teslaboat.repository;

import com.teslaboat.model.db.BoatTypePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface BoatTypePriceRepository extends JpaRepository<BoatTypePrice, Long> {
    Optional<BoatTypePrice> findFirst1ByBoatTypeIdAndDateFromLessThanEqualOrderByDateFromDesc(Long boatTypeId, Date dateFrom);
}
