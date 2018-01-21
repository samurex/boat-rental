package com.teslaboat.services;

import com.teslaboat.model.*;
import com.teslaboat.model.db.Boat;
import com.teslaboat.model.db.BoatType;
import com.teslaboat.model.db.BoatTypePrice;
import com.teslaboat.model.db.Reservation;
import com.teslaboat.repository.BoatRepository;
import com.teslaboat.repository.BoatTypePriceRepository;
import com.teslaboat.repository.BoatTypeRepository;
import com.teslaboat.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoatManager {

    @Autowired
    BoatRepository boatRepository;

    @Autowired
    BoatTypeRepository boatTypeRepository;

    @Autowired
    BoatTypePriceRepository boatTypePriceRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public List<Boat> getAvailableBoats(long locationId, Date reservationDate)  {
        List<Boat> boats = boatRepository.findByLocationId(locationId);
        List<Reservation> reservations =
                reservationRepository.findByReservationDateAndStatus(reservationDate, ReservationStatus.CONFIRMED);

        // filter boats by reservations
        return boats.stream()
                .filter(b -> reservations.stream()
                        .noneMatch(r -> r.getBoat().getId() == b.getId()))
                .collect(Collectors.toList());
    }

    public Optional<Boat> getAvailableBoat(long locationId, long boatTypeId, Date reservationDate) {
        return this.getAvailableBoats(locationId, reservationDate)
                .stream()
                .filter(b -> b.getBoatType().getId() == boatTypeId)
                .findFirst();
    }

    public double getBoatPrice(Long boatTypeId, Date reservationDate) {
        Optional<BoatTypePrice> price =
                boatTypePriceRepository.findFirst1ByBoatTypeIdAndDateFromLessThanEqualOrderByDateFromDesc(boatTypeId, reservationDate);
        if (!price.isPresent()) {
            throw new IllegalArgumentException("Unable to find price for specified boat");
        }

        return price.get().getPrice();
    }

    public List<BoatType> getAvailableBoatTypes(long locationId, Date reservationDate) {
        return this.getAvailableBoats(locationId, reservationDate)
                .stream()
                .collect(Collectors.toMap(b -> b.getBoatType().getId(), b -> b.getBoatType(), (b1, b2) -> b1 ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }
}
