package com.suzasob.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suzasob.building_permission.model.GISAssessment;
import com.suzasob.building_permission.service.GISAssessmentService;

@RestController
@RequestMapping("/api/assessments")
public class GISAssessmentController {

    @Autowired
    private GISAssessmentService service;

    @PostMapping
    public ResponseEntity<GISAssessment> assess(@RequestBody GISAssessment assessment) {
        return ResponseEntity.ok(service.assessRisk(assessment));
    }
}
