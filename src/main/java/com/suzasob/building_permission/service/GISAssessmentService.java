package com.suzasob.building_permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.model.GISAssessment;
import com.suzasob.building_permission.repository.ConstructionProjectRepository;
import com.suzasob.building_permission.repository.GISAssessmentRepository;

@Service
public class GISAssessmentService {

    @Autowired
    private GISAssessmentRepository gisRepo;

    @Autowired
    private ConstructionProjectRepository projectRepository;

    // 1. Assess risk with explicit project
    public GISAssessment assessRisk(GISAssessment assessment, ConstructionProject project) {
        assessment.setProject(project);
        project.getGisAssessments().add(assessment);
        projectRepository.save(project);
        return assessment;
    }

    // 2. Assess risk using project inside GISAssessment
    public GISAssessment assessRisk(GISAssessment assessment) {
        ConstructionProject project = assessment.getProject();
        if (project != null) {
            project.getGisAssessments().add(assessment);
            projectRepository.save(project);  // Save updated project
        }
        return gisRepo.save(assessment);  // Save GISAssessment
    }

    // 3. Just map to entity, no DB operations
    public GISAssessment mapToEntity(GISAssessment assessment, ConstructionProject project) {
        assessment.setProject(project);
        return assessment;
    }

    public List<GISAssessment> findByProject(ConstructionProject project) {
        return gisRepo.findByProject(project);
    }

    public List<GISAssessment> getAssessmentsByProjectId(Long projectId) {
        return gisRepo.findByProject_ProjectId(projectId);
    }

    public List<GISAssessment> findByRiskType(String riskType) {
        return gisRepo.findByRiskType(riskType);
    }

    public List<GISAssessment> findByRiskLevel(String riskLevel) {
        return gisRepo.findByRiskLevel(riskLevel);
    }
}
