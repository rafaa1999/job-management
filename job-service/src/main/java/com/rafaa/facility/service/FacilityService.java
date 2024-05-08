package com.rafaa.facility.service;

import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.exception.FacilityNotFoundException;
import com.rafaa.facility.repository.FacilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacilityService {

    private static final Logger log = LoggerFactory.getLogger(FacilityService.class);
    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    public List<Facility> getFacilitiesByCarParkId(UUID car_park_id){
        log.info("Request received to get all facilities by car park id");
        List<Facility> facilities = facilityRepository.getFacilitiesByCarParkId(car_park_id);
        log.info("Returning facilities: {}", facilities);
        return facilities;
    }

    public Facility getFacilityById(UUID facility_id){
        log.info("Request received to get facility by facility id");
        Optional<Facility> facility = facilityRepository.findById(facility_id);
        if(facility.isPresent()){
           throw new FacilityNotFoundException("Facility not found with this id: " + facility_id);
        }
        log.info("Returning facility: {}", facility);
        return facility.orElse(null);
    }

    public List<Facility> getAllFacilities() {
        List<Facility> facilities = facilityRepository.findAll();
        log.info("Returning all facilities: {}", facilities);
        return facilities;
    }

}
