package com.suzasob.building_permission.repository;

import com.suzasob.building_permission.model.Inspection;
import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    // Get inspections by assigned engineer
    List<Inspection> findByInspector(User inspector);

    // Get inspections by project
    List<Inspection> findByProject(ConstructionProject project);

    // Get inspections by status
    List<Inspection> findByStatus(String status);

    // Get inspections by project and status
    List<Inspection> findByProjectAndStatus(ConstructionProject project, String status);

    // Get inspections by date range
    List<Inspection> findByInspectionDateBetween(Date startDate, Date endDate);
}