package com.suzasob.building_permission.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.locationtech.jts.geom.Point;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@Schema(description = "Construction project entity containing project details and embedded location information")
public class ConstructionProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Unique identifier for the construction project", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long projectId;

    @Schema(description = "Name of the construction project", example = "Residential Building A1", required = true)
    private String name;

    @Schema(description = "Current status of the project", example = "PENDING", allowableValues = { "PENDING",
            "APPROVED", "REJECTED", "IN_REVIEW" })
    private String status;

    @Schema(description = "Date when the project was submitted", example = "2025-07-30")
    private Date submissionDate;

    @Schema(description = "Terms of Reference document path or content", example = "/documents/tor_project_123.pdf")
    private String torDocument;

    @Schema(description = "Building plan document path or content", example = "/plans/building_plan_123.pdf")
    private String buildingPlan;

    // Location fields (embedded instead of separate entity)
    @Schema(description = "Physical address of the construction site", example = "123 Main Street, Downtown District", required = true)
    private String address;

    //@Schema(description = "Risk zone type of the location", example = "Flood", allowableValues = { "Flood", "Erosion",
        //    "Earthquake", "Safe" })
    //private String riskZoneType; // e.g., "Flood", "Erosion", etc.

    @Schema(description = "Latitude coordinate of the construction site", example = "40.7128")
    private Double latitude;

    @Schema(description = "Longitude coordinate of the construction site", example = "-74.0059")
    private Double longitude;

    @Column(columnDefinition = "geometry(Point,4326)")
    @JsonIgnore
    @Schema(description = "Geographical coordinates of the construction site in WGS84 format", example = "POINT(-74.0059 40.7128)", required = false, hidden = true)
    private Point geom;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @Schema(description = "Owner of the construction project")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    @Schema(description = "Consultant assigned to the project")
    private User consultant;

    @ManyToOne
    @JoinColumn(name = "engineer_id")
    @Schema(description = "Engineer assigned to the project")
    private User engineer;

   @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
private java.util.List<GISAssessment> gisAssessments = new java.util.ArrayList<>();
 // or whatever your type is
        public void addGISAssessment(GISAssessment assessment) {
                gisAssessments.add(assessment);
                assessment.setProject(this);
        }
        
        public void removeGISAssessment(GISAssessment assessment) {
                gisAssessments.remove(assessment);
                assessment.setProject(null);
        }

        public Object getGISAssessment() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getGISAssessment'");
        }

        public Long getId() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getId'");
        }
}