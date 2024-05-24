package com.rafaa.demo;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.repository.CarParkRepository;
import com.rafaa.contingent.entity.Contingent;
import com.rafaa.contingent.repository.ContingentRepository;
import com.rafaa.counter.entity.Counter;
import com.rafaa.counter.repository.CounterRepository;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.enums.FacilityType;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.multitenancy.context.TenantContextHolder;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class DataConfig {

    private final CarParkRepository carParkRepository;
    private final FacilityRepository facilityRepository;
    private final CounterRepository counterRepository;
    private final ContingentRepository contingentRepository;

    public DataConfig(CarParkRepository carParkRepository, FacilityRepository facilityRepository, CounterRepository counterRepository, ContingentRepository contingentRepository) {
        this.carParkRepository = carParkRepository;
        this.facilityRepository = facilityRepository;
        this.counterRepository = counterRepository;
        this.contingentRepository = contingentRepository;
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

            Counter counter_reserved = Counter.builder()
                    .category("Reserved")
                    .facility(facility_one)
                    .build();
            Counter counter_non_reserved = Counter.builder()
                    .category("Non_Reserved")
                    .facility(facility_one)
                    .build();
            Counter counter_pre_booked = Counter.builder()
                    .category("Pre_Booked")
                    .facility(facility_one)
                    .build();
            Counter counter_physic = Counter.builder()
                    .category("Physic")
                    .facility(facility_one)
                    .build();

            counterRepository.saveAll(List.of(counter_reserved,counter_non_reserved,counter_pre_booked,counter_physic));

            Contingent monday = Contingent.builder()
                    .name("Monday")
                    .counter(counter_reserved)
                    .build();
            Contingent tuesday = Contingent.builder()
                    .name("Tuesday")
                    .counter(counter_reserved)
                    .build();
            Contingent wednesday = Contingent.builder()
                    .name("Wednesday")
                    .counter(counter_reserved)
                    .build();
            Contingent thursday = Contingent.builder()
                    .name("Thursday")
                    .counter(counter_reserved)
                    .build();
            Contingent friday = Contingent.builder()
                    .name("Friday")
                    .counter(counter_reserved)
                    .build();
            Contingent saturday = Contingent.builder()
                    .name("Saturday")
                    .counter(counter_reserved)
                    .build();
            Contingent sunday = Contingent.builder()
                    .name("Sunday")
                    .counter(counter_reserved)
                    .build();

            contingentRepository.saveAll(List.of(monday,tuesday,wednesday,thursday,friday,sunday,saturday));
        }
        TenantContextHolder.clear();

    }

}
