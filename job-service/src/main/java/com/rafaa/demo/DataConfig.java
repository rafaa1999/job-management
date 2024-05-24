package com.rafaa.demo;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.repository.CarParkRepository;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.enums.FacilityType;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.multitenancy.context.TenantContextHolder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.List;

@Configuration(proxyBeanMethods = false)
public class DataConfig {

    private final CarParkRepository carParkRepository;
    private final FacilityRepository facilityRepository;

    public DataConfig(CarParkRepository carParkRepository, FacilityRepository facilityRepository) {
        this.carParkRepository = carParkRepository;
        this.facilityRepository = facilityRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData(){

        TenantContextHolder.setTenantIdentifier("meta");
        if(carParkRepository.count() == 0){
            CarPark carPark_one = CarPark.builder()
                    .carParkName("nisan")
                    .carParkNumber("#_6")
                    .timezone("Roma/Europa")
                    .build();
            CarPark carPark_two = CarPark.builder()
                    .carParkName("reno")
                    .carParkNumber("#_9")
                    .timezone("Madrid/Europa")
                    .build();
            carParkRepository.saveAll(List.of(carPark_one,carPark_two));

            Facility facility_one = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("first_row")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("12")
                    .facilityName("orange")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();
            Facility facility_two = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("second_row")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("43")
                    .facilityName("orange")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();

            facilityRepository.saveAll(List.of(facility_one,facility_two));
        }
        TenantContextHolder.clear();

    }

}
