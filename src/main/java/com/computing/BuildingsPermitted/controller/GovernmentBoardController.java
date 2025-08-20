package com.computing.BuildingsPermitted.controller;

import com.computing.BuildingsPermitted.dto.DecisionRequest;
import com.computing.BuildingsPermitted.dto.DocumentDTO;
import com.computing.BuildingsPermitted.dto.EnvironmentalAssessmentDTO;
import com.computing.BuildingsPermitted.model.Document;
import com.computing.BuildingsPermitted.model.EnvironmentalAssessment;
import com.computing.BuildingsPermitted.model.Project;
import com.computing.BuildingsPermitted.repository.DocumentRepository;
import com.computing.BuildingsPermitted.repository.EnvironmentalAssessmentRepository;
import com.computing.BuildingsPermitted.repository.ProjectRepository;
import com.computing.BuildingsPermitted.repository.UserRepository;
import com.computing.BuildingsPermitted.service.EmailService;
import com.computing.BuildingsPermitted.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/government-board")
public class GovernmentBoardController {

    private final DocumentRepository documentRepository;
    private final EnvironmentalAssessmentRepository assessmentRepository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public GovernmentBoardController(DocumentRepository documentRepository,
                                     EnvironmentalAssessmentRepository assessmentRepository,
                                     ProjectRepository projectRepository,
                                     ProjectService projectService,
                                     UserRepository userRepository,
                                     EmailService emailService) {
        this.documentRepository = documentRepository;
        this.assessmentRepository = assessmentRepository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // View TOR, PLAN and latest Environmental Assessment (GIS results proxy)
    @GetMapping("/projects/{projectId}/bundle")
    @PreAuthorize("hasAnyRole('GOVERNMENT_BOARD','ADMIN')")
    public ResponseEntity<?> bundle(@PathVariable Long projectId) {
        Project p = projectRepository.findById(projectId).orElse(null);
        if (p == null) return ResponseEntity.notFound().build();
        List<Document> docs = documentRepository.findByProject_Id(projectId);
        List<DocumentDTO> docDtos = docs.stream()
                .filter(d -> "TOR".equalsIgnoreCase(d.getType()) || "PLAN".equalsIgnoreCase(d.getType()))
                .map(this::toDocDto).collect(Collectors.toList());

        List<EnvironmentalAssessment> assess = assessmentRepository.findByProject_Id(projectId);
        EnvironmentalAssessmentDTO eaDto = assess.isEmpty() ? null : toEaDto(assess.get(assess.size()-1));

        return ResponseEntity.ok(Map.of(
                "projectId", p.getId(),
                "status", p.getStatus(),
                "documents", docDtos,
                "assessment", eaDto
        ));
    }

    // Final decision with notifications
    @PostMapping("/projects/{projectId}/decision")
    @PreAuthorize("hasAnyRole('GOVERNMENT_BOARD','ADMIN')")
    public ResponseEntity<?> decide(@PathVariable Long projectId,
                                    @Valid @RequestBody DecisionRequest req) {
        Project p = projectRepository.findById(projectId).orElse(null);
        if (p == null) return ResponseEntity.notFound().build();

        String decision = req.getDecision().trim().toUpperCase();
        String newStatus;
        if ("APPROVE".equals(decision)) {
            newStatus = "PERMIT_GRANTED";
        } else if ("REJECT".equals(decision)) {
            newStatus = "PERMIT_REJECTED";
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "decision must be APPROVE or REJECT"));
        }
        Project updated = projectService.updateProjectStatus(projectId, newStatus, req.getNotes());

        // Notify admin, owner, engineer, consultant
        List<String> recipients = new ArrayList<>();
        if (p.getOwner() != null && p.getOwner().getEmail() != null) recipients.add(p.getOwner().getEmail());
        if (p.getEngineer() != null && p.getEngineer().getEmail() != null) recipients.add(p.getEngineer().getEmail());
        if (p.getConsultant() != null && p.getConsultant().getEmail() != null) recipients.add(p.getConsultant().getEmail());
        userRepository.findByRole("ADMIN").forEach(u -> { if (u.getEmail()!=null) recipients.add(u.getEmail()); });

        String subject = "Project " + p.getProjectName() + " decision: " + newStatus;
        String body = "Project ID: " + p.getId() + "\nStatus: " + newStatus + (req.getNotes()!=null?"\nNotes: "+req.getNotes():"");
        for (String to : recipients) {
            try { emailService.sendSimple(to, subject, body); } catch (Exception ignored) {}
        }

        return ResponseEntity.ok(Map.of("projectId", updated.getId(), "status", updated.getStatus(), "notified", recipients));
    }

    private DocumentDTO toDocDto(Document d) {
        return new DocumentDTO(
                d.getId(),
                d.getProject() != null ? d.getProject().getId() : null,
                d.getName(),
                d.getUrl(),
                d.getUploadedBy() != null ? d.getUploadedBy().getEmail() : null,
                d.getUploadedAt()
        );
    }

    private EnvironmentalAssessmentDTO toEaDto(EnvironmentalAssessment ea) {
        EnvironmentalAssessmentDTO dto = new EnvironmentalAssessmentDTO();
        dto.setId(ea.getId());
        dto.setProjectId(ea.getProject() != null ? ea.getProject().getId() : null);
        dto.setFloodsRisk(ea.getFloodsRisk());
        dto.setErosionRisk(ea.getErosionRisk());
        dto.setVolcanoRisk(ea.getVolcanoRisk());
        dto.setEarthquakeRisk(ea.getEarthquakeRisk());
        dto.setNotes(ea.getNotes());
        dto.setSeiOfficerName(ea.getSeiOfficerName());
        dto.setCreatedAt(ea.getCreatedAt());
        return dto;
    }
}
