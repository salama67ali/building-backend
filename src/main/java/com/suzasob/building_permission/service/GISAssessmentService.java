package com.suzasob.building_permission.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suzasob.building_permission.model.GISAssessment;
import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.repository.GISAssessmentRepository;

@Service
public class GISAssessmentService {

    @Autowired
    private GISAssessmentRepository gisRepo;

    public GISAssessment assessRisk(GISAssessment assessment) {
        return gisRepo.save(assessment);
    }

    public List<GISAssessment> findByProject(ConstructionProject project) {
        return gisRepo.findByProject(project);
    }
}
