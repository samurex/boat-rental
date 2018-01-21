package com.teslaboat;

import com.teslaboat.model.db.*;
import com.teslaboat.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableScheduling
public class ReservationsApplication implements CommandLineRunner {

    @Autowired
    BoatRepository boatRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    BoatTypeRepository boatTypeRepository;

    @Autowired
    BoatTypePriceRepository boatTypePriceRepository;

    @Autowired
    EquipmentTypeRepository equipmentTypeRepository;

    @Autowired
    EquipmentTypePriceRepository equipmentTypePriceRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(ReservationsApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        Location location1 = new Location();
        location1.setName("Boat rental Gdansk");
        locationRepository.save(location1);

        Location location2 = new Location();
        location2.setName("Boat rental Gdynia");
        locationRepository.save(location2);

        BoatType type1 = new BoatType();
        type1.setName("10m electric boat");
        type1.setDescription("This is a 10 meters boat with 6 seats");
        type1.setSeats(6);
        boatTypeRepository.save(type1);

        BoatTypePrice boatTypePrice1 = new BoatTypePrice();
        boatTypePrice1.setPrice(200);
        boatTypePrice1.setDateFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01"));
        boatTypePrice1.setBoatType(type1);
        boatTypePriceRepository.save(boatTypePrice1);

        BoatType type2 = new BoatType();
        type2.setName("5m electric boat");
        type2.setDescription("This is a 6 meters boat with 4 seats");
        type2.setSeats(4);
        boatTypeRepository.save(type2);

        BoatTypePrice boatTypePrice2 = new BoatTypePrice();
        boatTypePrice2.setPrice(150);
        boatTypePrice2.setDateFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01"));
        boatTypePrice2.setBoatType(type2);
        boatTypePriceRepository.save(boatTypePrice2);

        Boat boat1 = new Boat();
        boat1.setBoatType(type1);
        boat1.setLocation(location1);
        boatRepository.save(boat1);

        Boat boat2 = new Boat();
        boat2.setBoatType(type2);
        boat2.setLocation(location1);
        boatRepository.save(boat2);

        Boat boat3 = new Boat();
        boat3.setBoatType(type1);
        boat3.setLocation(location2);
        boatRepository.save(boat3);

        EquipmentType equipmentType1 = new EquipmentType();
        equipmentType1.setName("Fishing rod");
        equipmentTypeRepository.save(equipmentType1);

        EquipmentTypePrice equipmentTypePrice1 = new EquipmentTypePrice();
        equipmentTypePrice1.setEquipmentType(equipmentType1);
        equipmentTypePrice1.setPrice(20);
        equipmentTypePrice1.setDateFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01"));
        equipmentTypePriceRepository.save(equipmentTypePrice1);


        EquipmentType equipmentType2 = new EquipmentType();
        equipmentType2.setName("Sunbed");
        equipmentTypeRepository.save(equipmentType2);

        EquipmentTypePrice equipmentTypePrice2 = new EquipmentTypePrice();
        equipmentTypePrice2.setEquipmentType(equipmentType2);
        equipmentTypePrice2.setPrice(15);
        equipmentTypePrice2.setDateFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01"));
        equipmentTypePriceRepository.save(equipmentTypePrice2);


        EquipmentType equipmentType3 = new EquipmentType();
        equipmentType3.setName("Accessibility equipment");
        equipmentTypeRepository.save(equipmentType3);

        EquipmentTypePrice equipmentTypePrice3 = new EquipmentTypePrice();
        equipmentTypePrice3.setEquipmentType(equipmentType3);
        equipmentTypePrice3.setPrice(10);
        equipmentTypePrice3.setDateFrom(new SimpleDateFormat("yyyy-MM-dd").parse("2017-01-01"));
        equipmentTypePriceRepository.save(equipmentTypePrice3);

        for (int i = 0; i < 10; ++i) {
            Equipment equipment = new Equipment();
            equipment.setEquipmentType(equipmentType1);
            equipment.setLocation(location1);
            equipmentRepository.save(equipment);
        }

        for (int i = 0; i < 10; ++i) {
            Equipment equipment = new Equipment();
            equipment.setEquipmentType(equipmentType2);
            equipment.setLocation(location1);
            equipmentRepository.save(equipment);
        }

        for (int i = 0; i < 10; ++i) {
            Equipment equipment = new Equipment();
            equipment.setEquipmentType(equipmentType3);
            equipment.setLocation(location1);
            equipmentRepository.save(equipment);
        }

    }
}
