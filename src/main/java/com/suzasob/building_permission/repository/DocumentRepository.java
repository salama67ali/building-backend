package com.suzasob.building_permission.repository;

import com.suzasob.building_permission.model.ConstructionProject;
import com.suzasob.building_permission.model.Document;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Fetch all documents related to a project
    List<Document> findByProject(ConstructionProject project);
}
