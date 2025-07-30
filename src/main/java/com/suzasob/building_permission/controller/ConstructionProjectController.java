package com.suzasob.building_permission.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.service.ConstructionProjectService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/projects")
@Tag(name = "Construction Projects", description = "API for managing construction projects and building permits")
public class ConstructionProjectController {

    @Autowired
    private ConstructionProjectService projectService;

    @PostMapping
    @Operation(summary = "Submit a new construction project", description = "Submit a new construction project for review and permit processing")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project submitted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid project data provided"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ConstructionProject> submit(
            @Parameter(description = "Construction project details", required = true) @RequestBody ConstructionProject project) {
        return ResponseEntity.ok(projectService.submitProject(project));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get projects by status", description = "Retrieve all construction projects with a specific status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status specified")
    })
    public ResponseEntity<List<ConstructionProject>> getByStatus(
            @Parameter(description = "Project status to filter by", required = true, example = "PENDING") @PathVariable String status) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConstructionProject> update(@PathVariable Long id, @RequestBody ConstructionProject project) {
        return ResponseEntity.ok(projectService.updateProject(id, project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConstructionProject> getById(@PathVariable Long id) {
        ConstructionProject project = (ConstructionProject) projectService.getProjectById(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(projectService.getProjectById(userId));
    }
}
