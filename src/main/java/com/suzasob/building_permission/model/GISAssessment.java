package com.suzasob.building_permission.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class GISAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessmentId;

    private String riskType;

    private String riskLevel;

    private String reportFile;

    private Date assessmentDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ConstructionProject project;

    // Constructors, Getters, Setters
    public GISAssessment() {
    }

    public GISAssessment(Long assessmentId, String riskType, String riskLevel, String reportFile, Date assessmentDate,
            ConstructionProject project) {
        this.assessmentId = assessmentId;
        this.riskType = riskType;
        this.riskLevel = riskLevel;
        this.reportFile = reportFile;
        this.assessmentDate = assessmentDate;
        this.project = project;
    }

    // Getters and Setters
    public Long getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(Long assessmentId) {
        this.assessmentId = assessmentId;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public ConstructionProject getProject() {
        return project;
    }

    public void setProject(ConstructionProject project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "GISAssessment{" +
                "assessmentId=" + assessmentId +
                ", riskType='" + riskType + '\'' +
                ", riskLevel='" + riskLevel + '\'' +
                ", reportFile='" + reportFile + '\'' +
                ", assessmentDate=" + assessmentDate +
                ", project=" + (project != null ? project.getProjectId() : null) +
                '}';
    }
}
