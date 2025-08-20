package com.computing.BuildingsPermitted.controller;

import com.computing.BuildingsPermitted.dto.*;
import com.computing.BuildingsPermitted.model.Document;
import com.computing.BuildingsPermitted.model.Project;
import com.computing.BuildingsPermitted.model.User;
import com.computing.BuildingsPermitted.repository.DocumentRepository;
import com.computing.BuildingsPermitted.repository.ProjectRepository;
import com.computing.BuildingsPermitted.repository.UserRepository;
import com.computing.BuildingsPermitted.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final EmailService emailService;

    public AdminController(UserRepository userRepository,
                           DocumentRepository documentRepository,
                           ProjectRepository projectRepository,
                           EmailService emailService) {
        this.userRepository = userRepository;
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
        this.emailService = emailService;
    }

    // Dashboard statistics for admin
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminStats() {
        long totalUsers = userRepository.count();
        int totalOwners = userRepository.findByRole("OWNER").size();
        int totalConsultants = userRepository.findByRole("CONSULTANT").size();
        int totalEngineers = userRepository.findByRole("ENGINEER").size();
        int totalGovernmentBoards = userRepository.findByRole("GOVERNMENT_BOARD").size();

        long pendingApplications = projectRepository.findByStatus("pending").size();
        long approvedApplications =
                projectRepository.findByStatus("approved").size()
                        + projectRepository.findByStatus("ENGINEER_APPROVED").size()
                        + projectRepository.findByStatus("PERMIT_GRANTED").size();
        long rejectedApplications =
                projectRepository.findByStatus("rejected").size()
                        + projectRepository.findByStatus("ENGINEER_REJECTED").size()
                        + projectRepository.findByStatus("PERMIT_REJECTED").size();

        return ResponseEntity.ok(Map.of(
                "totalUsers", totalUsers,
                "totalOwners", totalOwners,
                "totalConsultants", totalConsultants,
                "totalEngineers", totalEngineers,
                "totalGovernmentBoards", totalGovernmentBoards,
                "pendingApplications", pendingApplications,
                "approvedApplications", approvedApplications,
                "rejectedApplications", rejectedApplications
        ));
    }

    // Send notifications (placeholder: returns targeted recipients count)
    @PostMapping("/notifications")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody NotificationRequest req) {
        List<User> targets;
        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            targets = userRepository.findByEmail(req.getEmail()).map(List::of).orElse(List.of());
        } else if (req.getRole() != null && !req.getRole().isBlank()) {
            targets = userRepository.findByRole(req.getRole());
        } else {
            targets = userRepository.findAll();
        }
        // Send real emails (simple text). In production, consider async sending & error handling per recipient.
        String subject = req.getSubject();
        String body = req.getMessage();
        targets.forEach(u -> {
            if (u.getEmail() != null && !u.getEmail().isBlank()) {
                try { emailService.sendSimple(u.getEmail(), subject, body); } catch (Exception ignored) {}
            }
        });
        return ResponseEntity.ok(Map.of(
                "message", "Notification sent",
                "subject", req.getSubject(),
                "recipients", targets.stream().map(User::getEmail).collect(Collectors.toList()),
                "count", targets.size()
        ));
    }

    // View submitted documents (optionally by projectId)
    @GetMapping("/documents")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listDocuments(@RequestParam(required = false) Long projectId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(1, size));
        Page<Document> docPage = (projectId != null)
                ? documentRepository.findByProject_Id(projectId, pageable)
                : documentRepository.findAll(pageable);
        List<DocumentDTO> items = docPage.getContent().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
                "items", items,
                "page", docPage.getNumber(),
                "size", docPage.getSize(),
                "totalElements", docPage.getTotalElements(),
                "totalPages", docPage.getTotalPages()
        ));
    }

    // View permission status for a project
    @GetMapping("/permission-status/{projectId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionStatusDTO> getPermissionStatus(@PathVariable Long projectId) {
        Project p = projectRepository.findById(projectId).orElse(null);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toStatusDto(p));
    }

    // View permission status for all projects
    @GetMapping("/permission-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listPermissionStatuses(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(1, size));
        Page<Project> p = projectRepository.findAll(pageable);
        List<PermissionStatusDTO> items = p.getContent().stream().map(this::toStatusDto).collect(Collectors.toList());
        return ResponseEntity.ok(Map.of(
                "items", items,
                "page", p.getNumber(),
                "size", p.getSize(),
                "totalElements", p.getTotalElements(),
                "totalPages", p.getTotalPages()
        ));
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

    private PermissionStatusDTO toStatusDto(Project p) {
        PermissionStatusDTO dto = new PermissionStatusDTO();
        dto.setProjectId(p.getId());
        dto.setProjectName(p.getProjectName());
        dto.setStatus(p.getStatus());
        dto.setSubmissionDate(p.getSubmissionDate());
        dto.setApprovalDate(p.getApprovalDate());
        dto.setCompletionDate(p.getCompletionDate());
        return dto;
    }
}
