package com.teslaboat.repository;

import com.teslaboat.model.db.Reservation;
import com.teslaboat.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByCode(String code);

//    @Query("from Reservation r where status=:status and DATE(r.reservationDate) = DATE(:reservationDate)")
//    List<Reservation> findByReservationDateAndStatus(@Param("reservationDate") Date reservationDate,
//                                                     @Param("status") ReservationStatus status);

    List<Reservation> findByReservationDateAndStatus(Date reservationDate, ReservationStatus status);

    List<Reservation> findByExpirationDateLessThanEqualAndStatus(Date expirationDate, ReservationStatus status);
}
