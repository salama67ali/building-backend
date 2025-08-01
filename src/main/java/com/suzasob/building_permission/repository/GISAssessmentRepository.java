package com.suzasob.building_permission.repository;

import com.suzasob.building_permission.model.GISAssessment;
import com.suzasob.building_permission.model.ConstructionProject;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GISAssessmentRepository extends JpaRepository<GISAssessment, Long> {
    List<GISAssessment> findByProject(ConstructionProject project);

    List<GISAssessment> findByRiskType(String riskType);

    List<GISAssessment> findByRiskLevel(String riskLevel);

    List<GISAssessment> findByProject_ProjectId(Long projectId);
}
