package com.teslaboat.repository;

import com.teslaboat.model.db.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
