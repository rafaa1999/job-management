package com.rafaa;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.service.CarParkService;
import com.rafaa.facility.controller.FacilityRestController;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.facility.service.FacilityService;
import com.rafaa.multitenancy.context.TenantContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JobServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobServiceApplication.class);
    }
}

@RestController
class AppController{
    private final CarParkService carParkService;
    private final FacilityService facilityService;
    private final FacilityRepository facilityRepository;

    AppController(CarParkService carParkService, FacilityService facilityService, FacilityRepository facilityRepository) {
        this.carParkService = carParkService;
        this.facilityService = facilityService;
        this.facilityRepository = facilityRepository;
    }

    @GetMapping("/tenant")
    public ResponseEntity<Tenant> tenant(@RequestHeader("X-TenantId") String tenantId){
        TenantContextHolder.setTenantIdentifier(tenantId);
        String tenantIdentifier = TenantContextHolder.getTenantIdentifier();
        System.out.println(tenantIdentifier);
        Tenant tenant = new Tenant(tenantIdentifier);
        return ResponseEntity.ok(tenant);
    }

    @GetMapping("/car-parks")
    public ResponseEntity<List<CarPark>> parks(@RequestHeader("X-TenantId") String tenantId){
        TenantContextHolder.setTenantIdentifier(tenantId);
        return ResponseEntity.ok(carParkService.getCarParks());
    }

    @GetMapping("/car-parks/{id}")
    public ResponseEntity<CarPark> car(@RequestHeader("X-TenantId") String tenantId,
                                       @PathVariable(name = "id") UUID id){
        TenantContextHolder.setTenantIdentifier(tenantId);
        return ResponseEntity.ok(carParkService.getCarParkById(id));
    }

    @GetMapping("/facilities/car-park/{car_park_id}")
    public ResponseEntity<List<Facility>> getFacilitiesByCarParkId(@RequestHeader("X-TenantId") String tenantId,
                                                                   @PathVariable(name = "car_park_id") UUID car_park_id){
       TenantContextHolder.setTenantIdentifier(tenantId);
       return ResponseEntity.ok(facilityService.getFacilitiesByCarParkId(car_park_id));
    }

    @GetMapping("facilities")
    public ResponseEntity<List<Facility>> facilities(@RequestHeader("X-TenantId") String tenantId,
                                                     @PathVariable(name = "car_park_id") UUID car_park_id){
        TenantContextHolder.setTenantIdentifier(tenantId);
        return ResponseEntity.ok(facilityRepository.findAll());
    }

}

record Tenant(String tenantIdentifier){}