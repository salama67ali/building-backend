package com.example.building_system.dto;

public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;

    public ProjectResponseDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
}
