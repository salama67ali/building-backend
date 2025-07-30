package com.suzasob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suzasob.building_permission.model.Permit;
import com.suzasob.building_permission.service.PermitService;

@RestController
@RequestMapping("/api/permits")
public class PermitController {

    @Autowired
    private PermitService permitService;

    @PostMapping
    public ResponseEntity<Permit> issue(@RequestBody Permit permit) {
        return ResponseEntity.ok(permitService.issuePermit(permit));
    }
}
