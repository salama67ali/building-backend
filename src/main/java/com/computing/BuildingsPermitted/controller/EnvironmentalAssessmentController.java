package com.computing.BuildingsPermitted.controller;

import com.computing.BuildingsPermitted.dto.EnvironmentalAssessmentRequest;
import com.computing.BuildingsPermitted.dto.EnvironmentalAssessmentDTO;
import com.computing.BuildingsPermitted.model.EnvironmentalAssessment;
import com.computing.BuildingsPermitted.service.EnvironmentalAssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin(origins = "http://localhost:5173")
public class EnvironmentalAssessmentController {

    private final EnvironmentalAssessmentService service;

    public EnvironmentalAssessmentController(EnvironmentalAssessmentService service) {
        this.service = service;
    }

    // Owners can submit assessments for their projects; Admin and Government Board can manage
    @PostMapping
    @PreAuthorize("hasAnyRole('OWNER','ADMIN','GOVERNMENT_BOARD')")
    public ResponseEntity<EnvironmentalAssessmentDTO> create(@Valid @RequestBody EnvironmentalAssessmentRequest req) {
        return ResponseEntity.ok(toDto(service.create(req)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GOVERNMENT_BOARD')")
    public ResponseEntity<EnvironmentalAssessmentDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody EnvironmentalAssessmentRequest req) {
        return ResponseEntity.ok(toDto(service.update(id, req)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('OWNER','CONSULTANT','ENGINEER','GOVERNMENT_BOARD','ADMIN')")
    public ResponseEntity<EnvironmentalAssessmentDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(toDto(service.get(id)));
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('OWNER','CONSULTANT','ENGINEER','GOVERNMENT_BOARD','ADMIN')")
    public ResponseEntity<List<EnvironmentalAssessmentDTO>> listByProject(@PathVariable Long projectId) {
        List<EnvironmentalAssessmentDTO> out = service.listByProject(projectId)
                .stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GOVERNMENT_BOARD')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    private EnvironmentalAssessmentDTO toDto(EnvironmentalAssessment ea) {
        EnvironmentalAssessmentDTO dto = new EnvironmentalAssessmentDTO();
        dto.setId(ea.getId());
        dto.setProjectId(ea.getProject() != null ? ea.getProject().getId() : null);
        dto.setFloodsRisk(ea.getFloodsRisk());
        dto.setErosionRisk(ea.getErosionRisk());
        dto.setVolcanoRisk(ea.getVolcanoRisk());
        dto.setEarthquakeRisk(ea.getEarthquakeRisk());
        dto.setNotes(ea.getNotes());
        dto.setSeiOfficerName(ea.getSeiOfficerName());
        dto.setCreatedAt(ea.getCreatedAt());
        return dto;
    }
}
