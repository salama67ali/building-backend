package com.computing.BuildingsPermitted.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "environmental_assessments")
public class EnvironmentalAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    private Boolean floodsRisk;
    private Boolean erosionRisk;
    private Boolean volcanoRisk;
    private Boolean earthquakeRisk;

    @Column(length = 2000)
    private String notes;

    private String seiOfficerName;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public EnvironmentalAssessment() {}

    public EnvironmentalAssessment(Project project) {
        this.project = project;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

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
