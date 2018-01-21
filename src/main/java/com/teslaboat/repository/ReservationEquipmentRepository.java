package com.teslaboat.repository;


import com.teslaboat.model.db.ReservationEquipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationEquipmentRepository  extends JpaRepository<ReservationEquipment, Long> {
}
