package com.example.building_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository<Owner> extends JpaRepository<Owner, Long> {
}
