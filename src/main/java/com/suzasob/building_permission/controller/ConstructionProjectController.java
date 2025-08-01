package com.suzasob.building_permission.controller;

import com.suzasob.building_permission.dto.ProjectRegistrationDTO;
import com.suzasob.building_permission.dto.ProjectResponseDTO;
import com.suzasob.building_permission.dto.ProjectUpdateDTO;
import com.suzasob.building_permission.service.ConstructionProjectService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ConstructionProjectController {

    private final ConstructionProjectService projectService;

    //  1. Register a new project
    @PostMapping
    public ResponseEntity<ProjectResponseDTO> submitProject(@RequestBody ProjectRegistrationDTO dto) {
        ProjectResponseDTO response = projectService.submitProject(dto);
        return ResponseEntity.ok(response);
    }

    //  2. Get all projects
    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    //  3. Get projects by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectsByStatus(@PathVariable String status) {
        List<ProjectResponseDTO> projects = projectService.getProjectsByStatus(status);
        return ResponseEntity.ok(projects);
    }

     @GetMapping("/api/projects/{id}")
public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
    return ResponseEntity.ok(projectService.getProjectById(id));
}
    //  4. Update project status
    @PutMapping("/{id}/status")
    public ResponseEntity<ProjectResponseDTO> updateProjectStatus(@PathVariable Long id, @RequestBody ProjectUpdateDTO updateDTO) {
        ProjectResponseDTO updatedProject = projectService.updateProjectStatus(id, updateDTO);
        return ResponseEntity.ok(updatedProject);
    }

}
