package com.suzasob.building_permission.service;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.repository.ConstructionProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Optional;

@Service
public class ConstructionProjectService {

    @Autowired
    private ConstructionProjectRepository projectRepository;

    private final GeometryFactory geometryFactory = new GeometryFactory();

    // Create
    public ConstructionProject submitProject(ConstructionProject project) {
        // For new projects, ensure the ID is null so Hibernate treats it as an insert
        if (project.getProjectId() != null && project.getProjectId() <= 0) {
            project.setProjectId(null);
        }

        // Create Point geometry from latitude and longitude if provided
        if (project.getLatitude() != null && project.getLongitude() != null) {
            Coordinate coordinate = new Coordinate(project.getLongitude(), project.getLatitude());
            Point point = geometryFactory.createPoint(coordinate);
            point.setSRID(4326); // Set SRID for WGS84
            project.setGeom(point);
        }

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

            // Update location fields directly
            project.setAddress(updatedProject.getAddress());
            project.setRiskZoneType(updatedProject.getRiskZoneType());
            project.setLatitude(updatedProject.getLatitude());
            project.setLongitude(updatedProject.getLongitude());

            // Create Point geometry from updated latitude and longitude if provided
            if (updatedProject.getLatitude() != null && updatedProject.getLongitude() != null) {
                Coordinate coordinate = new Coordinate(updatedProject.getLongitude(), updatedProject.getLatitude());
                Point point = geometryFactory.createPoint(coordinate);
                point.setSRID(4326); // Set SRID for WGS84
                project.setGeom(point);
            }

            // Set user references
            project.setOwner(updatedProject.getOwner());
            project.setConsultant(updatedProject.getConsultant());
            project.setEngineer(updatedProject.getEngineer());

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
