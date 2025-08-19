package com.computing.BuildingsPermitted.dto;

import java.time.LocalDateTime;

public class DocumentDTO {
    private Long id;
    private Long projectId;
    private String name;
    private String url;
    private String uploadedByEmail;
    private LocalDateTime uploadedAt;

    public DocumentDTO() {}

    public DocumentDTO(Long id, Long projectId, String name, String url, String uploadedByEmail, LocalDateTime uploadedAt) {
        this.id = id; this.projectId = projectId; this.name = name; this.url = url; this.uploadedByEmail = uploadedByEmail; this.uploadedAt = uploadedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getUploadedByEmail() { return uploadedByEmail; }
    public void setUploadedByEmail(String uploadedByEmail) { this.uploadedByEmail = uploadedByEmail; }
    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
