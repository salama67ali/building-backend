package com.example.building_system.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.building_system.service.OwnerService;

@RestController
@RequestMapping("/api")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners/{ownerId}/notifications")
    public ResponseEntity<List<String>> getOwnerNotifications(@PathVariable Long ownerId) {
        List<String> notifications = ownerService.getNotifications(ownerId);
        return ResponseEntity.ok(notifications);
    }
}
