package com.rafaa.counter.repository;

import com.rafaa.counter.entity.Counter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CounterRepository extends JpaRepository<Counter, UUID> {
    @Query("SELECT c FROM Counter c WHERE c.facility.id = :facilityId")
    List<Counter> getAllCounterByFacilityId(UUID facilityId);
}
