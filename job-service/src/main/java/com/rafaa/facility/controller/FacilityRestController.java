package com.rafaa.facility.controller;

import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.service.FacilityService;
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
@RequestMapping("/api/facilities")
public class FacilityRestController {

    private static final Logger log = LoggerFactory.getLogger(FacilityRestController.class);
    private final FacilityService facilityService;

    public FacilityRestController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/car-park/{car_park_id}")
    public ResponseEntity<List<Facility>> getFacilitiesByCarParkId(@PathVariable(name = "car_park_id") UUID car_park_id){
       log.info("FacilityRestController.getFacilitiesByCarParkId");
       return ResponseEntity.ok(facilityService.getFacilitiesByCarParkId(car_park_id));
    }

    @GetMapping
    public ResponseEntity<List<Facility>> getAllFacilities(){
        log.info("FacilityRestController.getAllFacilities()");
        return ResponseEntity.ok(facilityService.getAllFacilities());
    }

}
