package com.suzasob.building_permission.dto;

import lombok.Data;

@Data
public class ProjectUpdateDTO {
    private Long projectId;
    private String name;
    private String address;
    private String torDocument;
    private String status;
    private String buildingPlan;
    public String toUpperCase() {
        String status = null;
        return status.toUpperCase();
    }
}