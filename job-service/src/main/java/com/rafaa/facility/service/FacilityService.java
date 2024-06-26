package com.rafaa.facility.service;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.repository.CarParkRepository;
import com.rafaa.counter.entity.Counter;
import com.rafaa.counter.repository.CounterRepository;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.enums.FacilityType;
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
    private final CounterRepository counterRepository;
    private final CarParkRepository carParkRepository;

    public FacilityService(FacilityRepository facilityRepository, CounterRepository counterRepository, CarParkRepository carParkRepository) {
        this.facilityRepository = facilityRepository;
        this.counterRepository = counterRepository;
        this.carParkRepository = carParkRepository;
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

    public void updateFacility(UUID id, String facilityName, String facilityNumber,
                               String locationId, FacilityType type, boolean isDeleted, String description) {
        Facility facility = facilityRepository.findById(id).get();
        facility.setFacilityName(facilityName);
        facility.setFacilityNumber(facilityNumber);
        facility.setLocationId(locationId);
        facility.setFacilityType(type);
        facility.setDeleted(isDeleted);
        if(!description.isEmpty() || description != null){
            facility.setDescription(description);
        }
        System.out.println("#)($*#$%$*#&$&##$#$");
        System.out.println(facility);
        facilityRepository.save(facility);
    }

    public void addFacility(String facilityName, String facilityNumber, String locationId,
                            FacilityType type, Integer reservedCapacity, Integer nonReservedCapacity, Integer preBooked, UUID id) {

        CarPark carPark = carParkRepository.findById(id).get();

        Facility facility = Facility.builder()
                .carPark(carPark)
                .facilityType(type)
                .facilityName(facilityName)
                .facilityNumber(facilityNumber)
                .locationId(locationId)
                .build();

        facilityRepository.save(facility);

        Counter reserved_counter = Counter.builder()
                .facility(facility)
                .category("Reserved")
                .capacity(reservedCapacity)
                .occupied(reservedCapacity / 2)
                .available(reservedCapacity / 2)
                .build();
        Counter non_reserved_counter = Counter.builder()
                .facility(facility)
                .category("Non_Reserved")
                .capacity(nonReservedCapacity)
                .occupied(nonReservedCapacity / 2)
                .available(nonReservedCapacity / 2)
                .build();
        Counter pre_booked_counter = Counter.builder()
                .facility(facility)
                .category("Pre_booked")
                .capacity(preBooked)
                .occupied(preBooked / 2)
                .available(preBooked / 2)
                .build();
        Counter physical_counter = Counter.builder()
                .facility(facility)
                .category("Physical")
                .capacity(preBooked + reservedCapacity + nonReservedCapacity)
                .occupied( (preBooked / 2) + (reservedCapacity / 2) + (nonReservedCapacity / 2) )
                .available( (preBooked / 2) + (reservedCapacity / 2) + (nonReservedCapacity / 2) )
                .build();

        List<Counter> counters = List.of(reserved_counter,non_reserved_counter,pre_booked_counter,physical_counter);

        counterRepository.saveAll(counters);

        log.info("this is facility added: {}",facility);
        log.info("this is counter added: {}",counters);
    }

    public void deleteFacility(UUID id) {
        Facility facility = facilityRepository.findById(id).get();
        List<Counter> counters = counterRepository.findAll();
        for(Counter c: counters){
            if(c.getFacility().getId() == facility.getId()){
                counterRepository.deleteById(c.getId());
            }
        }
        facilityRepository.deleteById(id);
    }
}
