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
    @JoinColumn(name = "location_id")
    private Location location;

    // Constructors, Getters, Setters
    public GISAssessment() {
    }
    public GISAssessment(Long assessmentId, String riskType, String riskLevel, String reportFile, Date assessmentDate, Location location) {
        this.assessmentId = assessmentId;
        this.riskType = riskType;
        this.riskLevel = riskLevel;
        this.reportFile = reportFile;
        this.assessmentDate = assessmentDate;
        this.location = location;
    }
}
