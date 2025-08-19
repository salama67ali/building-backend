package com.computing.BuildingsPermitted.controller;

import com.computing.BuildingsPermitted.model.Project;
import com.computing.BuildingsPermitted.service.ProjectService;
import com.computing.BuildingsPermitted.dto.ProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ProjectDTO>> getProjectsByOwner(@PathVariable Long ownerId) {
        List<ProjectDTO> projects = projectService.getProjectsByOwner(ownerId)
                .stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id)
                .map(p -> ResponseEntity.ok(toDto(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody Project project) {
        return ResponseEntity.ok(toDto(projectService.createProject(project)));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ProjectDTO> updateProjectStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(required = false) String notes) {
        return ResponseEntity.ok(toDto(projectService.updateProjectStatus(id, status, notes)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    private ProjectDTO toDto(Project p) {
        ProjectDTO dto = new ProjectDTO();
        dto.setId(p.getId());
        dto.setProjectName(p.getProjectName());
        dto.setProjectType(p.getProjectType());
        dto.setAddress(p.getAddress());
        dto.setCity(p.getCity());
        dto.setState(p.getState());
        dto.setStatus(p.getStatus());
        dto.setOwnerId(p.getOwner() != null ? p.getOwner().getId() : null);
        dto.setSubmissionDate(p.getSubmissionDate());
        dto.setApprovalDate(p.getApprovalDate());
        dto.setCompletionDate(p.getCompletionDate());
        return dto;
    }
}
