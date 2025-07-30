package com.suzasob.building_permission.service;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.repository.ConstructionProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConstructionProjectService {

    @Autowired
    private ConstructionProjectRepository projectRepository;

    // Create
    public ConstructionProject submitProject(ConstructionProject project) {
        return projectRepository.save(project);
    }

    // Read all
    public List<ConstructionProject> getAllProjects() {
        return projectRepository.findAll();
    }

    // Read by ID
    public ConstructionProject getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    // Read by status
    public List<ConstructionProject> getProjectsByStatus(String status) {
        return projectRepository.findByStatus(status);
    }

    // Read by user ID (assuming it's owner_id)
    public Optional<ConstructionProject> getProjectsByUserId(Long userId) {
        return projectRepository.findById(userId);
    }

    // Update
    public ConstructionProject updateProject(Long id, ConstructionProject updatedProject) {
        Optional<ConstructionProject> existing = projectRepository.findById(id);
        if (existing.isPresent()) {
            ConstructionProject project = existing.get();
            project.setName(updatedProject.getName());
            project.setStatus(updatedProject.getStatus());
            project.setSubmissionDate(updatedProject.getSubmissionDate());
            project.setTorDocument(updatedProject.getTorDocument());
            project.setBuildingPlan(updatedProject.getBuildingPlan());
            project.setLocation(updatedProject.getLocation());
            // Set other fields as needed
            return projectRepository.save(project);
        } else {
            return null;
        }
    }

    // Delete
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
