package com.example.building_system.controller;

import com.example.building_system.model.Building;
import com.example.building_system.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200") // Hii ni domain ya Angular yako
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public List<Building> getAllProjects() {
        return buildingService.getAllProjects();
    }

    @PostMapping
    public Building createProject(@RequestBody Building project) {
        return buildingService.saveProject(project);
    }

    @GetMapping("/{id}")
    public Building getProject(@PathVariable Long id) {
        return buildingService.getProjectById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Long id) {
        buildingService.deleteProject(id);
    }
}
