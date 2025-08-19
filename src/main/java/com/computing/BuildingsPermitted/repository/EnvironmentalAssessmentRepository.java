package com.computing.BuildingsPermitted.repository;

import com.computing.BuildingsPermitted.model.EnvironmentalAssessment;
import com.computing.BuildingsPermitted.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvironmentalAssessmentRepository extends JpaRepository<EnvironmentalAssessment, Long> {
    List<EnvironmentalAssessment> findByProject(Project project);
    List<EnvironmentalAssessment> findByProject_Id(Long projectId);
}
