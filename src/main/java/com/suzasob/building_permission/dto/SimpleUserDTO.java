package com.suzasob.building_permission.dto;

import lombok.Data;

@Data
public class SimpleUserDTO {
    private Long userId;
    private String name;
    private String email;
    private String role;
}