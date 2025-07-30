package com.example.building_system.service;

import com.example.building_system.model.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BuildingService<buildingRepository, buildingRepository, buildingRepository, BuildingRepository> {

    @Autowired
    private BuildingRepository buildingRepository;  

    public List<Building> getAllProjects() {
        return (List<Building>) buildingRepository.findAll();
    }

    public Building saveProject(Building project) {
        return buildingRepository.save(project);
    }

    public Building getProjectById(Long id) {
        Optional<Building> project = buildingRepository.findById(id);
        return project.orElse(null); // unaweza kurekebisha utoe exception kama huipati
    }

    public <buildingRepository, buildingRepository, buildingRepository> void deleteProject(Long id) {
        buildingRepository.deleteById(id);
    }
}
