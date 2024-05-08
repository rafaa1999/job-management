package com.rafaa.carpark.service;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.exception.CarParkNotFoundException;
import com.rafaa.carpark.repository.CarParkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarParkService {

    private static final Logger log = LoggerFactory.getLogger(CarParkService.class);
    private final CarParkRepository carParkRepository;

    public CarParkService(CarParkRepository carParkRepository) {
        this.carParkRepository = carParkRepository;
    }

    public List<CarPark> getAllCarParks(String name,String timezone){
        log.info("Request received to get all car parks");
        List<CarPark> carParks = carParkRepository.getAllCarParks(name,timezone);
        log.info("Returing car parks: {}", carParks);
        return carParks;
    }

    public CarPark getCarParkById(UUID id){
        log.info("Request received to get car park by id");
        Optional<CarPark> carPark = carParkRepository.findById(id);
        if(!carPark.isPresent()){
           throw new CarParkNotFoundException("Car park not found with the id: "+ id);
        }
        log.info("Returning this car park:{}", carPark);
        return  carPark.get();
    }

}
