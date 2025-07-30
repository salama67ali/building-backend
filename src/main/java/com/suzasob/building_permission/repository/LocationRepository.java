package com.suzasob.building_permission.repository;

import com.suzasob.building_permission.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

/**
 ▪️ Find locations based on a given risk zone type (e.g., "Flood", "Erosion").
 */
List<Location> findByRiskZoneType(String riskZoneType);

/**
 ▪️ Find all locations within a given radius (in meters) of a specified geographic point.
 ▪️ Uses ST_DWithin + ST_MakePoint with PostGIS spatial functions.
 ▪️ Parameters:
 * @param lng - Longitude of the center point
 * @param lat - Latitude of the center point
 * @param radiusInMeters - Search radius in meters
 * @return list of nearby locations
 */
@Query(
    value = "SELECT * FROM location " +
            "WHERE ST_DWithin(geom, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :radius)", 
    nativeQuery = true
)
List<Location> findNearbyLocations(
    @Param("lng") double longitude,
    @Param("lat") double latitude,
    @Param("radius") double radiusInMeters
);

/**
 ▪️ Optional: find by coordinates match (for exact location)
 */
@Query(
    value = "SELECT * FROM location " +
            "WHERE ST_Equals(geom, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326))", 
    nativeQuery = true
)
List<Location> findByExactCoordinates(
    @Param("lng") double longitude,
    @Param("lat") double latitude
);
}
