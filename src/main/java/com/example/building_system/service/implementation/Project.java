package com.example.building_system.service.implementation;

import jakarta.persistence.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Long ownerId;

    // Constructors
    public Project() {}

    public Project(String name, String description, Long ownerId) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setProjectDescription(Object projectDescription) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProjectDescription'");
    }

    public void setProjectName(Object projectName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProjectName'");
    }

    public String getProjectName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectName'");
    }

    public String getProjectDescription() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectDescription'");
    }
}
