package com.suzasob.building_permission.model;

import java.sql.Date;

@Entity
public class Permit {

    private static final String GenerationType = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long permitId;

    private Date issueDate;

    private Date expiryDate;

    private String status;

    @OneToOne
    @JoinColumn(name = "project_id")
    private ConstructionProject project;

    @ManyToOne
    @JoinColumn(name = "issued_by")
    private User issuedBy;

    // Constructors, Getters, Setters
    public Permit() {
    }
    public Permit(Long permitId, Date issueDate, Date expiryDate, String status, ConstructionProject project, User issuedBy) {
        this.permitId = permitId;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.status = status;
        this.project = project;
        this.issuedBy = issuedBy;
    }
    public Long getPermitId() {
        return permitId;
    }
    public void setPermitId(Long permitId) {
        this.permitId = permitId;
    }
    public Date getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public ConstructionProject getProject() {
        return project;
    }
    public void setProject(ConstructionProject project) {
        this.project = project;
    }
    public User getIssuedBy() {
        return issuedBy;
    }
    public void setIssuedBy(User issuedBy) {
        this.issuedBy = issuedBy;
    }
    @Override
    public String toString() {
        return "Permit{" +
                "permitId=" + permitId +
                ", issueDate=" + issueDate +
                ", expiryDate=" + expiryDate +
                ", status='" + status + '\'' +
                ", project=" + project +
                ", issuedBy=" + issuedBy +
                '}';
    }
    
}
