package com.suzasob.building_permission.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ProjectRegistrationDTO {
    private String name;
    private String address;
    private String status;
    private Date submissionDate;
    private String torDocument;
    private String buildingPlan;
    private Double latitude;
    private Double longitude;
    private Long ownerId;
    private Long consultantId;
    private Long engineerId;
}