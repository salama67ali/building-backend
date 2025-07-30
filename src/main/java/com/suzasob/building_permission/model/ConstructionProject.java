package com.suzasob.building_permission.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
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

}