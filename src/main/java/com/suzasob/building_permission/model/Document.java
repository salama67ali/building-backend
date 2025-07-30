package com.suzasob.building_permission.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @Column(nullable = false)
    private String documentName;

    @Column(nullable = false)
    private String documentType; // e.g., "PERMIT_APPLICATION", "BUILDING_PLAN", "INSPECTION_REPORT"

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private String mimeType;

    private Long fileSize;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "uploaded_at", nullable = false, updatable = false)
    private Date uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private ConstructionProject project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permit_id")
    private Permit permit;

    // Constructors
    public Document() {
        this.uploadedAt = new Date();
    }

    public Document(String documentName, String documentType, String filePath, String mimeType, Long fileSize) {
        this();
        this.documentName = documentName;
        this.documentType = documentType;
        this.filePath = filePath;
        this.mimeType = mimeType;
        this.fileSize = fileSize;
    }

    // Getters and Setters
    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public ConstructionProject getProject() {
        return project;
    }

    public void setProject(ConstructionProject project) {
        this.project = project;
    }

    public Permit getPermit() {
        return permit;
    }

    public void setPermit(Permit permit) {
        this.permit = permit;
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", documentName='" + documentName + '\'' +
                ", documentType='" + documentType + '\'' +
                ", filePath='" + filePath + '\'' +
                ", uploadedAt=" + uploadedAt +
                '}';
    }
}
