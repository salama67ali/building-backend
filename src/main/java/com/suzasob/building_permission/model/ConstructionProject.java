package com.suzasob.building_permission.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class ConstructionProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String name;

    private String status;

    private Date submissionDate;

    private String torDocument;

    private String buildingPlan;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private User consultant;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    private User engineer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    // Constructors, Getters, Setters
    public ConstructionProject() {
    }
    public ConstructionProject(Long projectId, String name, String status, Date submissionDate, String torDocument,
                               String buildingPlan, User owner, User consultant, User engineer, Location location) {
        this.projectId = projectId;
        this.name = name;
        this.status = status;
        this.submissionDate = submissionDate;
        this.torDocument = torDocument;
        this.buildingPlan = buildingPlan;
        this.owner = owner;
        this.consultant = consultant;
        this.engineer = engineer;
        this.location = location;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getSubmissionDate() {
        return submissionDate;
    }
    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
    public String getTorDocument() {
        return torDocument;
    }
    public void setTorDocument(String torDocument) {
        this.torDocument = torDocument;
    }
    public String getBuildingPlan() {
        return buildingPlan;
    }
    public void setBuildingPlan(String buildingPlan) {
        this.buildingPlan = buildingPlan;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public User getConsultant() {
        return consultant;
    }
    public void setConsultant(User consultant) {
        this.consultant = consultant;
    }
    public User getEngineer() {
        return engineer;
    }
    public void setEngineer(User engineer) {
        this.engineer = engineer;
    }
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return "ConstructionProject{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", submissionDate=" + submissionDate +
                ", torDocument='" + torDocument + '\'' +
                ", buildingPlan='" + buildingPlan + '\'' +
                ", owner=" + owner +
                ", consultant=" + consultant +
                ", engineer=" + engineer +
                ", location=" + location +
                '}';
    }
}
