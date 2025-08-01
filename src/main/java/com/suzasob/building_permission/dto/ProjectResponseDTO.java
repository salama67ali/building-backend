package com.suzasob.building_permission.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectResponseDTO {
    private Long projectId;
    private String name;
    private String address;
    private String status;
    private Date submissionDate;
    private String torDocument;
    private String buildingPlan;
    private Double latitude;
    private Double longitude;

    private SimpleUserDTO owner;
    private SimpleUserDTO consultant;
    private SimpleUserDTO engineer;

    // GIS Assessment fields
    private String riskType;
    private String riskLevel;
    private String reportFile;
    private Date assessmentDate;

    // Setters for GISAssessment data
    public void setRiskType(Object gisAssessment) {
        if (gisAssessment != null && gisAssessment instanceof com.suzasob.building_permission.model.GISAssessment) {
            this.riskType = ((com.suzasob.building_permission.model.GISAssessment) gisAssessment).getRiskType();
        }
    }

    public void setRiskLevel(Object gisAssessment) {
        if (gisAssessment != null && gisAssessment instanceof com.suzasob.building_permission.model.GISAssessment) {
            this.riskLevel = ((com.suzasob.building_permission.model.GISAssessment) gisAssessment).getRiskLevel();
        }
    }

    public void setReportFile(Object gisAssessment) {
        if (gisAssessment != null && gisAssessment instanceof com.suzasob.building_permission.model.GISAssessment) {
            this.reportFile = ((com.suzasob.building_permission.model.GISAssessment) gisAssessment).getReportFile();
        }
    }

    public void setAssessmentDate(Object gisAssessment) {
        if (gisAssessment != null && gisAssessment instanceof com.suzasob.building_permission.model.GISAssessment) {
            this.assessmentDate = ((com.suzasob.building_permission.model.GISAssessment) gisAssessment).getAssessmentDate();
        }
    }
}
