package com.teslaboat.services;

import com.teslaboat.model.*;
import com.teslaboat.model.db.*;
import com.teslaboat.repository.EquipmentRepository;
import com.teslaboat.repository.EquipmentTypePriceRepository;
import com.teslaboat.repository.EquipmentTypeRepository;
import com.teslaboat.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EquipmentManager {

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    EquipmentTypeRepository equipmentTypeRepository;

    @Autowired
    EquipmentTypePriceRepository equipmentTypePriceRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public List<Equipment> getAvailableEquipments(long locationId, Date reservationDate) {
        List<Equipment> equipments = equipmentRepository.findByLocationId(locationId);
        List<Reservation> reservations =
                reservationRepository.findByReservationDateAndStatus(reservationDate, ReservationStatus.CONFIRMED);

        List<Equipment> reservationEquipment = reservations
                .stream()
                .map(r -> r.getReservationEquipment())
                .flatMap(re -> re.stream())
                .map(re -> re.getEquipment())
                .collect(Collectors.toList());

        List<Equipment> avaliableEquipment = equipments
                .stream()
                .filter(e -> reservationEquipment
                                .stream()
                                .noneMatch(re -> re.getId() == e.getId()))
                .collect(Collectors.toList());

        return avaliableEquipment;
    }

    public List<Equipment> getAvailableEquipments(long locationId, long equipmentTypeId, Date reservationDate, int quantity) {
            return this.getAvailableEquipments(locationId, reservationDate)
                .stream()
                .filter(e -> e.getEquipmentType().getId() == equipmentTypeId)
                .limit(quantity)
                .collect(Collectors.toList());
    }

    public double getEquipmentPrice(Long equipmentTypeId, Date reservationDate) {
        Optional<EquipmentTypePrice> price =
                equipmentTypePriceRepository.findFirst1ByEquipmentTypeIdAndDateFromLessThanEqualOrderByDateFromDesc(equipmentTypeId, reservationDate);
        if (!price.isPresent()) {
            throw new IllegalArgumentException("Unable to find price for specified equipment");
        }

        return price.get().getPrice();
    }
}