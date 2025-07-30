package com.suzasob.building_permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suzasob.building_permission.model.Location;
import com.suzasob.building_permission.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public List<Location> findByRiskZone(String riskType) {
        return locationRepository.findByRiskZoneType(riskType);
    }
}
