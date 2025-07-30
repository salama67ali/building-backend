package com.suzasob.building_permission.model;
import org.springframework.data.geo.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String address;

    private String riskZoneType; // e.g., "Flood", "Erosion", etc.

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point geom;

    // Constructors, Getters, Setters
    public Location() {
    }
    public Location(Long locationId, String address, String riskZoneType, Point geom) {
        this.locationId = locationId;
        this.address = address;
        this.riskZoneType = riskZoneType;
        this.geom = geom;
    }
    public Long getLocationId() {
        return locationId;
    }
    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRiskZoneType() {
        return riskZoneType;
    }
    public void setRiskZoneType(String riskZoneType) {
        this.riskZoneType = riskZoneType;
    }
    public Point getGeom() {
        return geom;
    }
    public void setGeom(Point geom) {
        this.geom = geom;
    }
    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", address='" + address + '\'' +
                ", riskZoneType='" + riskZoneType + '\'' +
                ", geom=" + geom +
                '}';
    }
}
