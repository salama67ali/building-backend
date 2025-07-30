package com.suzasob.controller;

import java.util.List;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.service.ConstructionProjectService;

@RestController
@RequestMapping("/api/projects")
public class ConstructionProjectController {

    private static final String ResponseEntity = null;
    @Autowired
    private ConstructionProjectService projectService;

    @PostMapping
    public ResponseEntity<ConstructionProject> submit(@RequestBody ConstructionProject project) {
        return ResponseEntity.ok(projectService.submitProject(project));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ConstructionProject>> getByStatus(@PathVariable String status) {
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
    }   @GetMapping("/user/{userId}")
public ResponseEntity<Object> getByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(projectService.getProjectById(userId));
}
}

