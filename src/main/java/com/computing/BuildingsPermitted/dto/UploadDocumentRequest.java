package com.computing.BuildingsPermitted.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UploadDocumentRequest {
    @NotNull
    private Long projectId;

    @NotBlank
    private String name;

    // For simplicity we accept a URL/path; actual binary upload can be added later
    @NotBlank
    private String url;

    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}
