package com.computing.BuildingsPermitted.service;

import com.computing.BuildingsPermitted.dto.UploadDocumentRequest;
import com.computing.BuildingsPermitted.model.Document;
import com.computing.BuildingsPermitted.model.Project;
import com.computing.BuildingsPermitted.model.User;
import com.computing.BuildingsPermitted.repository.DocumentRepository;
import com.computing.BuildingsPermitted.repository.ProjectRepository;
import com.computing.BuildingsPermitted.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public DocumentService(DocumentRepository documentRepository,
                           ProjectRepository projectRepository,
                           UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Document uploadTor(UploadDocumentRequest req, String uploaderEmail) {
        return upload(req, uploaderEmail, "TOR");
    }

    public Document uploadPlan(UploadDocumentRequest req, String uploaderEmail) {
        return upload(req, uploaderEmail, "PLAN");
    }

    private Document upload(UploadDocumentRequest req, String uploaderEmail, String type) {
        Project project = projectRepository.findById(req.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found: " + req.getProjectId()));
        User uploader = userRepository.findByEmail(uploaderEmail)
                .orElseThrow(() -> new RuntimeException("Uploader not found: " + uploaderEmail));

        Document d = new Document();
        d.setProject(project);
        d.setName(req.getName());
        d.setUrl(req.getUrl());
        d.setType(type);
        d.setUploadedBy(uploader);
        return documentRepository.save(d);
    }

    public List<Document> listByProject(Long projectId) {
        return documentRepository.findByProject_Id(projectId);
    }
}
