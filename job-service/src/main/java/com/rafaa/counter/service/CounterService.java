package com.rafaa.counter.service;

import com.rafaa.counter.entity.Counter;
import com.rafaa.counter.repository.CounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CounterService {

    private static final Logger log = LoggerFactory.getLogger(CounterService.class);
    private final CounterRepository counterRepository;

    public CounterService(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    public List<Counter> getAllCounterByFacilityId(UUID facility_id){
        log.info("Request received to get counters by facility id");
        List<Counter> counters = counterRepository.getAllCounterByFacilityId(facility_id);
        log.info("Returning counters: {}",counters);
        return counters;
    }


}
