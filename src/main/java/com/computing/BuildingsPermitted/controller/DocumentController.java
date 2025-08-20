package com.computing.BuildingsPermitted.controller;

import com.computing.BuildingsPermitted.dto.DocumentDTO;
import com.computing.BuildingsPermitted.dto.UploadDocumentRequest;
import com.computing.BuildingsPermitted.model.Document;
import com.computing.BuildingsPermitted.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    // Owner uploads ToR
    @PostMapping("/owner/projects/{projectId}/documents/tor")
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public ResponseEntity<DocumentDTO> uploadTor(@PathVariable Long projectId,
                                                 @Valid @RequestBody UploadDocumentRequest req,
                                                 Authentication auth) {
        req.setProjectId(projectId);
        Document d = documentService.uploadTor(req, auth.getName());
        return ResponseEntity.ok(toDto(d));
    }

    // Consultant uploads building plan
    @PostMapping("/consultant/projects/{projectId}/documents/plan")
    @PreAuthorize("hasAnyRole('CONSULTANT','ADMIN')")
    public ResponseEntity<DocumentDTO> uploadPlan(@PathVariable Long projectId,
                                                  @Valid @RequestBody UploadDocumentRequest req,
                                                  Authentication auth) {
        req.setProjectId(projectId);
        Document d = documentService.uploadPlan(req, auth.getName());
        return ResponseEntity.ok(toDto(d));
    }

    // List documents for a project (visible to all involved roles)
    @GetMapping("/projects/{projectId}/documents")
    @PreAuthorize("hasAnyRole('OWNER','CONSULTANT','ENGINEER','GOVERNMENT_BOARD','ADMIN')")
    public ResponseEntity<List<DocumentDTO>> list(@PathVariable Long projectId) {
        List<DocumentDTO> items = documentService.listByProject(projectId)
                .stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(items);
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
