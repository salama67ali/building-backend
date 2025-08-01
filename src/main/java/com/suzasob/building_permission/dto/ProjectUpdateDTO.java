package com.suzasob.building_permission.dto;

import lombok.Data;

@Data
public class ProjectUpdateDTO {
    private Long projectId;
    private String status;
    private String buildingPlan;
    public String toUpperCase() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toUpperCase'");
    }
}