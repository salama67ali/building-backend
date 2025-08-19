package com.computing.BuildingsPermitted.repository;

import com.computing.BuildingsPermitted.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByProject_Id(Long projectId);
    Page<Document> findByProject_Id(Long projectId, Pageable pageable);
}
