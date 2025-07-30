package com.suzasob.building_permission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suzasob.building_permission.model.Location;
import com.suzasob.building_permission.service.LocationService;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.saveLocation(location));
    }

    @GetMapping("/risk/{type}")
    public ResponseEntity<List<Location>> getByRisk(@PathVariable String type) {
        return ResponseEntity.ok(locationService.findByRiskZone(type));
    }
}
