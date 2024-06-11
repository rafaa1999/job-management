package com.rafaa.job.service;

import com.rafaa.job.dto.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {
    @Query("SELECT h FROM History h where h.jobName = :jobName")
    List<History> getHistoryByName(String jobName);
}
