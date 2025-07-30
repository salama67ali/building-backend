package com.example.building_system.model;

import org.locationtech.jts.geom.Geometry;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Building {
    private Long UserId = null;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
public void setId (Long id) 
{ this.id = id; }
public Long getUserId() 
{  return UserId;}
public void setUserId(Long userId)
 { this.UserId = userId; }

 public Long getId() {
        return id;
    }
    private String projectName;
public String getProjectName() {
        return projectName;
    }
    private String ownerName;
    public String getOwnerName() {
        return ownerName;
    }
    private Geometry location;
    public Geometry getLocation() {
        return location;
        
    }
    private String status;
    public String getStatus() {
        return status;
    }
}
