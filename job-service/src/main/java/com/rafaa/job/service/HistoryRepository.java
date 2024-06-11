package com.rafaa.job.service;

import com.rafaa.job.dto.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {
    History findByJobName(String jobName);
}
