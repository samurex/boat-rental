package com.teslaboat.repository;

import com.teslaboat.model.db.BoatType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoatTypeRepository extends JpaRepository<BoatType, Long> {
}
