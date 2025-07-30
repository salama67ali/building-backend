package com.suzasob.building_permission.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suzasob.building_permission.model.ConstructionProject;

@Repository
public interface ConstructionProjectRepository extends JpaRepository<ConstructionProject, Long> {
    List<ConstructionProject> findByStatus(String status);
    List<ConstructionProject> findByOwnerId(Long ownerId);
}
