package com.computing.BuildingsPermitted.dto;

import java.time.LocalDateTime;

public class EnvironmentalAssessmentDTO {
    private Long id;
    private Long projectId;
    private Boolean floodsRisk;
    private Boolean erosionRisk;
    private Boolean volcanoRisk;
    private Boolean earthquakeRisk;
    private String notes;
    private String seiOfficerName;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public Boolean getFloodsRisk() { return floodsRisk; }
    public void setFloodsRisk(Boolean floodsRisk) { this.floodsRisk = floodsRisk; }
    public Boolean getErosionRisk() { return erosionRisk; }
    public void setErosionRisk(Boolean erosionRisk) { this.erosionRisk = erosionRisk; }
    public Boolean getVolcanoRisk() { return volcanoRisk; }
    public void setVolcanoRisk(Boolean volcanoRisk) { this.volcanoRisk = volcanoRisk; }
    public Boolean getEarthquakeRisk() { return earthquakeRisk; }
    public void setEarthquakeRisk(Boolean earthquakeRisk) { this.earthquakeRisk = earthquakeRisk; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getSeiOfficerName() { return seiOfficerName; }
    public void setSeiOfficerName(String seiOfficerName) { this.seiOfficerName = seiOfficerName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
