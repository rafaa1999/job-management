package com.rafaa.contingent.controller;

import com.rafaa.contingent.entity.Contingent;
import com.rafaa.contingent.service.ContingentService;
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
@RequestMapping("/api/contingents")
public class ContingentRestController {

    private static final Logger log = LoggerFactory.getLogger(ContingentRestController.class);
    private final ContingentService contingentService;

    public ContingentRestController(ContingentService contingentService) {
        this.contingentService = contingentService;
    }

    @GetMapping("/counter/{counter_id}")
    public ResponseEntity<List<Contingent>> getContingentsByCounterId(@PathVariable(name = "counter_id") UUID counter_id){
        log.info("ContingentRestController.getContingentsByCounterId()");
        return ResponseEntity.ok(contingentService.getContingentsByCounterId(counter_id));
    }

}
