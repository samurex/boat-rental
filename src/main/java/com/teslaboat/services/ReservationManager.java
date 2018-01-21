package com.teslaboat.services;

import com.teslaboat.model.*;
import com.teslaboat.model.db.*;
import com.teslaboat.repository.*;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationManager {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ReservationEquipmentRepository reservationEquipmentRepository;

    @Autowired
    BoatTypeRepository boatRepository;

    @Autowired
    BoatTypeRepository boatTypeRepository;

    @Autowired
    BoatTypePriceRepository boatTypePriceRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    EquipmentManager equipmentManager;

    @Autowired
    BoatManager boatManager;

    public Reservation addEquipmentToReservation(String reservationCode, Long equipmentTypeId, int quantity) {
        Reservation reservation = reservationRepository.findByCode(reservationCode);
        List<Equipment> equipment = equipmentManager.getAvailableEquipments(reservation.getLocation().getId(),
                equipmentTypeId,
                reservation.getReservationDate(),
                quantity);

        if (equipment.size() != quantity) {
            throw new IllegalArgumentException("Unable to find equipment for specified location and boatType");
        }

        equipment.stream().forEach(e -> {
            double price = equipmentManager.getEquipmentPrice(e.getEquipmentType().getId(),
                    reservation.getReservationDate());
            ReservationEquipment reservationEquipment = new ReservationEquipment();
            reservationEquipment.setEquipment(e);
            reservationEquipment.setReservation(reservation);
            reservationEquipment.setPrice(price);
            reservationEquipmentRepository.save(reservationEquipment);
            reservation.getReservationEquipment().add(reservationEquipment);
            reservation.setTotalPrice(reservation.getTotalPrice() + reservationEquipment.getPrice());
        });

        reservationRepository.save(reservation);

        return reservation;
    }

    public Reservation create(Long boatTypeId, Long locationId, Date reservationDate) {
        BoatType boatType = boatTypeRepository.findOne(boatTypeId);
        Location location = locationRepository.findOne(locationId);
        Optional<Boat> boat = boatManager.getAvailableBoat(locationId, boatTypeId, reservationDate);
        if (!boat.isPresent()) {
            throw new IllegalArgumentException("Unable to find boat for specified location and boatType");
        }
        Optional<BoatTypePrice> price =
                boatTypePriceRepository
                        .findFirst1ByBoatTypeIdAndDateFromLessThanEqualOrderByDateFromDesc(boatType.getId(), reservationDate);
        if (!price.isPresent()) {
            throw new IllegalArgumentException("Unable to find boat price for specified boatType and description");
        }

        Reservation reservation = new Reservation();
        reservation.setBoat(boat.get());
        reservation.setReservationDate(reservationDate);
        reservation.setLocation(location);
        reservation.setStatus(ReservationStatus.CREATED);
        reservation.setCode(generateReservationCode());
        reservation.setBoatPrice(price.get().getPrice());
        reservation.setTotalPrice(price.get().getPrice());
        reservation.setExpirationDate(new Date(System.currentTimeMillis()+ 5 * 60 * 1000));

        reservationRepository.save(reservation);

        return reservation;
    }

    public Reservation getByCode(String code) {
        return reservationRepository.findByCode(code);
    }

    private String generateReservationCode() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('a', 'z').build();
        String randomLetters = generator.generate(8);

        return randomLetters;
    }

    public Reservation markAsPaid(String code, String stripePaymentId) {
        Reservation reservation = reservationRepository.findByCode(code);

        reservation.setStripeRequestId(stripePaymentId);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservationRepository.save(reservation);

        return reservation;
    }

    public Reservation markAsCancelled(String code) {
        Reservation reservation = reservationRepository.findByCode(code);

        reservation.setCancellationDate(new Date());
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        return reservation;
    }

    public List<Reservation> markExpired() {

        List<Reservation> expiredReservations =
                this.reservationRepository.findByExpirationDateLessThanEqualAndStatus(new Date(), ReservationStatus.CREATED);

        expiredReservations.stream().forEach(r -> {
            r.setStatus(ReservationStatus.EXPIRED);
            reservationRepository.save(r);
        });

        return expiredReservations;
    }

    public Reservation updateEmail(String code, String email) {
        Reservation reservation = this.reservationRepository.findByCode(code);

        reservation.setEmail(email);
        return this.reservationRepository.save(reservation);
    }
}
