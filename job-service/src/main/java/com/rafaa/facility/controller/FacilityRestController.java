package com.rafaa.facility.controller;

import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.enums.FacilityType;
import com.rafaa.facility.service.FacilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/update/{id}")
    public void updateFacility(@PathVariable UUID id, @RequestParam(name = "facilityName") String facilityName,
                               @RequestParam(name = "facilityNumber") String facilityNumber,
                               @RequestParam(name = "facilityType") String facilityType,
                               @RequestParam(name = "locationId") String locationId,
                               @RequestParam(name = "isDeleted", required = false) boolean isDeleted,
                               @RequestParam(name = "description", required = false) String description){
        log.info("FacilityRestController.updateFacility()");
//        PARKING_LOTS,PARKING_GARAGES,PARK_AND_RIDE,VALET_PARKING,SMART_PARKING
        FacilityType type = null;
        if(facilityType.equals("PARKING_LOTS")){
            type = FacilityType.PARKING_LOTS;
        } else if (facilityType.equals("PARKING_GARAGES")) {
            type = FacilityType.PARKING_GARAGES;
        } else if (facilityType.equals("PARK_AND_RIDE")) {
            type = FacilityType.PARK_AND_RIDE;
        } else if (facilityType.equals("VALET_PARKING")) {
            type = FacilityType.VALET_PARKING;
        } else if (facilityType.equals("SMART_PARKING")) {
            type = FacilityType.SMART_PARKING;
        } else if (facilityType.isEmpty()) {
            type = FacilityType.PARKING_LOTS;
        }
        facilityService.updateFacility(id,facilityName,facilityNumber,locationId,type,isDeleted,description);
    }

    @RequestMapping("/add/{id}")
    public void addFacility( @PathVariable(name = "id") UUID id,
                             @RequestParam(name = "facilityName") String facilityName,
                             @RequestParam(name = "facilityNumber") String facilityNumber,
                             @RequestParam(name = "facilityType") String facilityType,
                             @RequestParam(name = "locationId") String locationId,
                             @RequestParam(name = "reservedCapacity") Integer reservedCapacity,
                             @RequestParam(name = "nonReservedCapacity") Integer nonReservedCapacity,
                             @RequestParam(name = "preBooked") Integer preBooked
                             ){
//        reservedCapacity:this.facilityAddForm.value.reservedCapacity,
//                nonReservedCapacity:this.facilityAddForm.value.nonReservedCapacity,
//                preBooked:this.facilityAddForm.value.preBooked
        log.info("FacilityRestController.addFacility()");
//        PARKING_LOTS,PARKING_GARAGES,PARK_AND_RIDE,VALET_PARKING,SMART_PARKING
        FacilityType type = null;
        if(facilityType.equals("PARKING_LOTS")){
            type = FacilityType.PARKING_LOTS;
        } else if (facilityType.equals("PARKING_GARAGES")) {
            type = FacilityType.PARKING_GARAGES;
        } else if (facilityType.equals("PARK_AND_RIDE")) {
            type = FacilityType.PARK_AND_RIDE;
        } else if (facilityType.equals("VALET_PARKING")) {
            type = FacilityType.VALET_PARKING;
        } else if (facilityType.equals("SMART_PARKING")) {
            type = FacilityType.SMART_PARKING;
        } else if (facilityType.isEmpty()) {
            type = FacilityType.PARKING_LOTS;
        }
        facilityService.addFacility(facilityName,facilityNumber,locationId,type,reservedCapacity,nonReservedCapacity,preBooked,id);
    }

    @RequestMapping("/delete/{id}")
    public void deleteFacility( @PathVariable(name = "id") UUID id ){
        log.info("FacilityRestController.deleteFacility()");
        facilityService.deleteFacility(id);
    }

}
