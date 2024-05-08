package com.rafaa.counter.controller;

import com.rafaa.counter.entity.Counter;
import com.rafaa.counter.service.CounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/counters")
public class CounterRestController {

    private static final Logger log = LoggerFactory.getLogger(CounterRestController.class);
    private final CounterService counterService;

    public CounterRestController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/facility/{facility_id}")
    public ResponseEntity<List<Counter>> getCountersByFacilityId(@PathVariable("facility_id") UUID facility_id){
       log.info("CounterRestController.getCountersByFacilityID()");
       return ResponseEntity.ok(counterService.getAllCounterByFacilityId(facility_id));
    }

}
