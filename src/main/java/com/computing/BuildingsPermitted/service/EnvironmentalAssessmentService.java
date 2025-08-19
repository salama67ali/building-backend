package com.computing.BuildingsPermitted.service;

import com.computing.BuildingsPermitted.dto.EnvironmentalAssessmentRequest;
import com.computing.BuildingsPermitted.model.EnvironmentalAssessment;
import com.computing.BuildingsPermitted.model.Project;
import com.computing.BuildingsPermitted.repository.EnvironmentalAssessmentRepository;
import com.computing.BuildingsPermitted.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnvironmentalAssessmentService {

    private final EnvironmentalAssessmentRepository repo;
    private final ProjectRepository projectRepository;

    public EnvironmentalAssessmentService(EnvironmentalAssessmentRepository repo, ProjectRepository projectRepository) {
        this.repo = repo;
        this.projectRepository = projectRepository;
    }

    public EnvironmentalAssessment create(EnvironmentalAssessmentRequest req) {
        Project project = projectRepository.findById(req.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found: " + req.getProjectId()));
        EnvironmentalAssessment ea = new EnvironmentalAssessment(project);
        apply(ea, req);
        return repo.save(ea);
    }

    public EnvironmentalAssessment update(Long id, EnvironmentalAssessmentRequest req) {
        EnvironmentalAssessment ea = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("EnvironmentalAssessment not found: " + id));
        // If projectId provided and different, remap
        if (req.getProjectId() != null && (ea.getProject() == null || !req.getProjectId().equals(ea.getProject().getId()))) {
            Project project = projectRepository.findById(req.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found: " + req.getProjectId()));
            ea.setProject(project);
        }
        apply(ea, req);
        return repo.save(ea);
    }

    public EnvironmentalAssessment get(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("EnvironmentalAssessment not found: " + id));
    }

    public List<EnvironmentalAssessment> listByProject(Long projectId) {
        return repo.findByProject_Id(projectId);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private void apply(EnvironmentalAssessment ea, EnvironmentalAssessmentRequest req) {
        ea.setFloodsRisk(req.getFloodsRisk());
        ea.setErosionRisk(req.getErosionRisk());
        ea.setVolcanoRisk(req.getVolcanoRisk());
        ea.setEarthquakeRisk(req.getEarthquakeRisk());
        ea.setNotes(req.getNotes());
        ea.setSeiOfficerName(req.getSeiOfficerName());
    }
}
