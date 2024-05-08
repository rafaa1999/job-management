package com.rafaa.contingent.service;

import com.rafaa.contingent.entity.Contingent;
import com.rafaa.contingent.repository.ContingentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContingentService {

    private static final Logger log = LoggerFactory.getLogger(ContingentService.class);

    private final ContingentRepository contingentRepository;

    public ContingentService(ContingentRepository contingentRepository) {
        this.contingentRepository = contingentRepository;
    }

    public List<Contingent> getContingentsByCounterId(UUID counterId) {
        List<Contingent> contingents = contingentRepository.getContingentsByCounterId(counterId);
        log.info("Returning all contingents: {}");
        return contingents;
    }

}
