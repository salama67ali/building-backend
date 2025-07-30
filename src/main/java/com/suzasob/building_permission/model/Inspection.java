package com.suzasob.building_permission.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inspections")
public class Inspection<GovernmentBoards> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inspectionId;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date inspectionDate;

    @Column(nullable = false)
    private String status; // PENDING, COMPLETED, FAILED

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "report_file")
    private String reportFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ConstructionProject project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector_id")
    private User inspector;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private GovernmentBoards board;

    // Constructors
    public Inspection() {
    }

    public Inspection(Date inspectionDate, String status, ConstructionProject project) {
        this.inspectionDate = inspectionDate;
        this.status = status;
        this.project = project;
    }

    // Getters and Setters
    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getReportFile() {
        return reportFile;
    }

    public void setReportFile(String reportFile) {
        this.reportFile = reportFile;
    }

    public ConstructionProject getProject() {
        return project;
    }

    public void setProject(ConstructionProject project) {
        this.project = project;
    }

    public User getInspector() {
        return inspector;
    }

    public void setInspector(User inspector) {
        this.inspector = inspector;
    }

    public GovernmentBoards getBoard() {
        return board;
    }

    public void setBoard(GovernmentBoards board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Inspection{" +
                "inspectionId=" + inspectionId +
                ", inspectionDate=" + inspectionDate +
                ", status='" + status + '\'' +
                ", projectId=" + (project != null ? project.getProjectId() : null) +
                '}';
    }
}
