package com.computing.BuildingsPermitted.controller;

import com.computing.BuildingsPermitted.dto.DecisionRequest;
import com.computing.BuildingsPermitted.dto.DocumentDTO;
import com.computing.BuildingsPermitted.model.Document;
import com.computing.BuildingsPermitted.model.Project;
import com.computing.BuildingsPermitted.repository.DocumentRepository;
import com.computing.BuildingsPermitted.repository.ProjectRepository;
import com.computing.BuildingsPermitted.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/engineer")
public class ReviewController {

    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;

    public ReviewController(DocumentRepository documentRepository,
                            ProjectRepository projectRepository,
                            ProjectService projectService) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
    }

    // Engineer views TOR and PLAN documents
    @GetMapping("/projects/{projectId}/documents")
    @PreAuthorize("hasAnyRole('ENGINEER','ADMIN')")
    public ResponseEntity<List<DocumentDTO>> listDocs(@PathVariable Long projectId) {
        List<Document> docs = documentRepository.findByProject_Id(projectId);
        List<DocumentDTO> out = docs.stream()
                .filter(d -> "TOR".equalsIgnoreCase(d.getType()) || "PLAN".equalsIgnoreCase(d.getType()))
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(out);
    }

    // Engineer decision: APPROVE or REJECT (sets project status accordingly)
    @PostMapping("/projects/{projectId}/review")
    @PreAuthorize("hasAnyRole('ENGINEER','ADMIN')")
    public ResponseEntity<?> review(@PathVariable Long projectId,
                                    @Valid @RequestBody DecisionRequest req) {
        Project p = projectRepository.findById(projectId).orElse(null);
        if (p == null) return ResponseEntity.notFound().build();

        String decision = req.getDecision().trim().toUpperCase();
        String newStatus;
        if ("APPROVE".equals(decision)) {
            newStatus = "ENGINEER_APPROVED";
        } else if ("REJECT".equals(decision)) {
            newStatus = "ENGINEER_REJECTED";
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "decision must be APPROVE or REJECT"));
        }
        Project updated = projectService.updateProjectStatus(projectId, newStatus, req.getNotes());
        return ResponseEntity.ok(Map.of("projectId", updated.getId(), "status", updated.getStatus()));
    }

    private DocumentDTO toDto(Document d) {
        return new DocumentDTO(
                d.getId(),
                d.getProject() != null ? d.getProject().getId() : null,
                d.getName(),
                d.getUrl(),
                d.getUploadedBy() != null ? d.getUploadedBy().getEmail() : null,
                d.getUploadedAt()
        );
    }
}
