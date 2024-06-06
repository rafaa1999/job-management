package com.rafaa.carpark.controller;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.service.CarParkService;
import com.rafaa.multitenancy.context.TenantContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/car-parks")
public class CarParkRestController {

    private static final Logger log = LoggerFactory.getLogger(CarParkRestController.class);
    private final CarParkService carParkService;

    public CarParkRestController(CarParkService carParkService) {
        this.carParkService = carParkService;
    }

    @GetMapping
//    public ResponseEntity<List<CarPark>> getAllCarPark(@RequestHeader("X-TenantId") String tenantId
//                                                       ,@RequestParam(name = "name", required = false) String name,
//                                                       @RequestParam(name = "timezone", required = false) String timezone){
        public ResponseEntity<List<CarPark>> getAllCarPark(
                @RequestParam(name = "name", required = false) String name,
                @RequestParam(name = "timezone", required = false) String timezone){
//       TenantContextHolder.setTenantIdentifier(tenantId);
       log.info("CarParkRestController.getAllCarPark()");
       return ResponseEntity.ok(carParkService.getAllCarParks(name,timezone));
    }

    @GetMapping("/all")
    public ResponseEntity<List<CarPark>> getAllCarParks() throws SQLException {
//        TenantContextHolder.setTenantIdentifier(tenantId);
        log.info("CarParkRestController.getCarParks()");
        return ResponseEntity.ok(carParkService.getCarParks());
    }

    @GetMapping("/{car_park_id}")
    public ResponseEntity<CarPark> getCarParkById(@RequestHeader("X-TenantId") String tenantId,
                                                  @PathVariable(name = "car_park_id") UUID car_park_id){
        TenantContextHolder.setTenantIdentifier(tenantId);
        log.info("CarParkRestController.getCarParkById()");
        return ResponseEntity.ok(carParkService.getCarParkById(car_park_id));
    }

}
