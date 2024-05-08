package com.rafaa.facility.repository;

import com.rafaa.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, UUID> {
    @Query("SELECT f FROM Facility f WHERE f.carPark.id = :carParkId")
    List<Facility> getFacilitiesByCarParkId(UUID carParkId);
}
