package com.teslaboat.services;

import com.teslaboat.model.db.Location;
import com.teslaboat.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationManager {

    @Autowired
    LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
