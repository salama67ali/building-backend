package com.suzasob.building_permission.controller;

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
    public ResponseEntity<Object> assess(@RequestBody GISAssessment assessment) {
        return ResponseEntity.ok(service.assessRisk(assessment));
    }
    @PostMapping("/project")
    public ResponseEntity<Object> assessByProject(@RequestBody GISAssessment assessment) {
        return ResponseEntity.ok(service.assessRisk(assessment));
    }
    @PostMapping("/risk")
    public ResponseEntity<Object> assessByRisk(@RequestBody GISAssessment assessment) {
        return ResponseEntity.ok(service.assessRisk(assessment));
}
    @PostMapping("/level")
    public ResponseEntity<Object> assessByLevel(@RequestBody GISAssessment assessment) {
        return ResponseEntity.ok(service.assessRisk(assessment));
}
    @PostMapping("/id")
    public ResponseEntity<Object> assessById(@RequestBody GISAssessment assessment) {
        return ResponseEntity.ok(service.assessRisk(assessment));
}
    @PostMapping("/entity")
    public ResponseEntity<Object> mapToEntity(@RequestBody GISAssessment assessment) {
        return ResponseEntity.ok(service.mapToEntity(assessment, null));
    }
    @PostMapping("/projectId")
    public ResponseEntity<Object> getAssessmentsByProjectId(@RequestBody GISAssessment assessment) {
        Long projectId = assessment.getProject().getId();
        return ResponseEntity.ok(service.getAssessmentsByProjectId(projectId));
}
}