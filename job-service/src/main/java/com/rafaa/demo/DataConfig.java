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
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;
import java.util.List;

@Configuration(proxyBeanMethods = false)
public class DataConfig {

    private final CarParkRepository carParkRepository;
    private final FacilityRepository facilityRepository;
    private final ContingentRepository contingentRepository;
    private final CounterRepository counterRepository;

    public DataConfig(CarParkRepository carParkRepository, FacilityRepository facilityRepository, ContingentRepository contingentRepository, CounterRepository counterRepository) {
        this.carParkRepository = carParkRepository;
        this.facilityRepository = facilityRepository;
        this.contingentRepository = contingentRepository;
        this.counterRepository = counterRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData(){

        TenantContextHolder.setTenantIdentifier("google");
        if(carParkRepository.count() == 0){
            CarPark carPark_one = CarPark.builder()
                    .carParkName("toyota")
                    .carParkNumber("#_3")
                    .timezone("Berlin/Europa")
                    .build();
            CarPark carPark_two = CarPark.builder()
                    .carParkName("ford")
                    .carParkNumber("#_4")
                    .timezone("Paris/Europa")
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

            Counter counter_one = Counter.builder()
                    .category("Reserved")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_one)
                    .build();
            Counter counter_two = Counter.builder()
                    .category("Non Reserved")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_one)
                    .build();
            Counter counter_three = Counter.builder()
                    .category("Pre-Booked")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_one)
                    .build();
            Counter counter_four = Counter.builder()
                    .category("Physical")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_one)
                    .build();

            Counter counter_one_facility_two = Counter.builder()
                    .category("Reserved")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_two)
                    .build();
            Counter counter_two_facility_two = Counter.builder()
                    .category("Non Reserved")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_two)
                    .build();
            Counter counter_three_facility_three = Counter.builder()
                    .category("Pre-Booked")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_two)
                    .build();
            Counter counter_four_facility_four = Counter.builder()
                    .category("Physical")
                    .available(20)
                    .capacity(60)
                    .occupied(40)
                    .facility(facility_two)
                    .build();

            counterRepository.saveAll(List.of(counter_one,counter_two,counter_three,counter_four,
                                              counter_one_facility_two,counter_two_facility_two,
                                              counter_three_facility_three,counter_four_facility_four));

            Contingent contingent_monday = Contingent.builder()
                    .name("contingent_one")
                    .value(23)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .dayOfWeek(LocalDate.now().getDayOfWeek())
                    .counter(counter_one)
                    .build();
            Contingent contingent_tuesday = Contingent.builder()
                    .name("contingent_one")
                    .value(23)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .dayOfWeek(LocalDate.now().getDayOfWeek())
                    .counter(counter_one)
                    .build();
            Contingent contingent_wednesday = Contingent.builder()
                    .name("contingent_one")
                    .value(23)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .dayOfWeek(LocalDate.now().getDayOfWeek())
                    .counter(counter_one)
                    .build();
            Contingent contingent_thursday = Contingent.builder()
                    .name("contingent_one")
                    .value(23)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .dayOfWeek(LocalDate.now().getDayOfWeek())
                    .counter(counter_one)
                    .build();
            Contingent contingent_friday = Contingent.builder()
                    .name("contingent_one")
                    .value(23)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .dayOfWeek(LocalDate.now().getDayOfWeek())
                    .counter(counter_one)
                    .build();
            Contingent contingent_saturday = Contingent.builder()
                    .name("contingent_one")
                    .value(23)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .dayOfWeek(LocalDate.now().getDayOfWeek())
                    .counter(counter_one)
                    .build();
            Contingent contingent_sunday = Contingent.builder()
                    .name("contingent_one")
                    .value(23)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now())
                    .dayOfWeek(LocalDate.now().getDayOfWeek())
                    .counter(counter_one)
                    .build();

            contingentRepository.saveAll(List.of(contingent_monday,contingent_tuesday,contingent_wednesday,contingent_thursday,
                                         contingent_friday,contingent_saturday,contingent_sunday));

        }
        TenantContextHolder.clear();

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
