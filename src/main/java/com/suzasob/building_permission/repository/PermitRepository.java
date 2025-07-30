package com.suzasob.building_permission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.model.Permit;

@Repository
public interface PermitRepository extends JpaRepository<Permit, Long> {
    Optional<Permit> findByProject(ConstructionProject project);
    List<Permit> findByStatus(String status);
}

