package com.rafaa.contingent.repository;

import com.rafaa.contingent.entity.Contingent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContingentRepository extends JpaRepository<Contingent,UUID> {
    @Query("SELECT c FROM Contingent c WHERE c.counter.id = :counterId")
    List<Contingent> getContingentsByCounterId(UUID counterId);
}
