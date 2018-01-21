package com.teslaboat.repository;

import com.teslaboat.model.db.Boat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Long> {
    List<Boat> findByLocationId(Long locationId);
}
