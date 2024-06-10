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

import java.time.ZoneId;
import java.util.List;
import java.util.stream.IntStream;

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

//            ZoneId paris = ZoneId.of("Europe/Paris");
            CarPark carPark_one = CarPark.builder()
                    .carParkName("nisan")
                    .carParkNumber("#_6")
                    .timezone("Europe/Paris")
                    .build();

            CarPark carPark_two = CarPark.builder()
                    .carParkName("reno")
                    .carParkNumber("#_9")
                    .timezone("Europe/Madrid")
                    .build();

            carParkRepository.saveAll(List.of(carPark_one,carPark_two ));

            Facility facility_one = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB2222.2222.020222")
                    .facilityType(FacilityType.PARKING_LOTS)
                    .facilityNumber("12")
                    .facilityName("facility_12")
                    .description("The number of this facility is 12")
                    .isDeleted(false)
                    .build();

//            SB1111.1111.010111
            Facility facility_two = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB1111.1111.010111")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("43")
                    .facilityName("facility_two")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();

            Facility facility_12 = Facility.builder()
                    .carPark(carPark_two)
                    .locationId("SB7777.7777.070777")
                    .facilityType(FacilityType.PARKING_LOTS)
                    .facilityNumber("112")
                    .facilityName("facility_12")
                    .description("The number of this facility is 12")
                    .isDeleted(false)
                    .build();

//            SB1111.1111.010111
            Facility facility_13 = Facility.builder()
                    .carPark(carPark_two)
                    .locationId("SB8888.8888.080888")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("443")
                    .facilityName("facility_two")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();


            facilityRepository.saveAll(List.of(facility_one,facility_two ,facility_12,facility_13));

            Counter counter_reserved = Counter.builder()
                    .category("Reserved")
                    .capacity(200)
                    .occupied(50)
                    .available(50)
//                    .occupied(50)
//                    .available(150)
                    .facility(facility_one)
                    .build();

            Counter counter_non_reserved = Counter.builder()
                    .category("Non_Reserved")
                    .capacity(400)
                    .occupied(100)
                    .available(50)
//                    .occupied(200)
//                    .available(200)
                    .facility(facility_one)
                    .build();

            Counter counter_pre_booked = Counter.builder()
                    .category("Pre_Booked")
                    .capacity(400)
                    .occupied(200)
                    .available(50)
//                    .occupied(250)
//                    .available(150)
                    .facility(facility_one)
                    .build();

            Counter counter_physic = Counter.builder()
                    .category("Physic")
                    .capacity(1000)
                    .occupied(350)
                    .available(150)
                    .facility(facility_one)
                    .build();

            Counter counter_reserved1 = Counter.builder()
                    .category("Reserved")
                    .capacity(200)
                    .occupied(50)
                    .available(50)
//                    .occupied(50)
//                    .available(150)
                    .facility(facility_two)
                    .build();

            Counter counter_non_reserved1 = Counter.builder()
                    .category("Non_Reserved")
                    .capacity(400)
                    .occupied(100)
                    .available(50)
//                    .occupied(200)
//                    .available(200)
                    .facility(facility_two)
                    .build();

            Counter counter_pre_booked1 = Counter.builder()
                    .category("Pre_Booked")
                    .capacity(400)
                    .occupied(200)
                    .available(50)
//                    .occupied(250)
//                    .available(150)
                    .facility(facility_two)
                    .build();

            Counter counter_physic1 = Counter.builder()
                    .category("Physic")
                    .capacity(1000)
                    .occupied(350)
                    .available(150)
                    .facility(facility_two)
                    .build();

//            counterRepository.saveAll(List.of(counter_reserved,counter_non_reserved,counter_pre_booked,counter_physic));
            counterRepository.saveAll(List.of(counter_reserved,counter_non_reserved,counter_pre_booked,counter_physic,
                    counter_non_reserved1,counter_reserved1,counter_physic1,counter_pre_booked1));

            Contingent monday = Contingent.builder()
                    .name("Monday")
                    .value(100)
                    .counter(counter_reserved)
                    .build();
            Contingent tuesday = Contingent.builder()
                    .name("Tuesday")
                    .value(200)
                    .counter(counter_reserved)
                    .build();
            Contingent wednesday = Contingent.builder()
                    .name("Wednesday")
                    .value(300)
                    .counter(counter_reserved)
                    .build();
            Contingent thursday = Contingent.builder()
                    .name("Thursday")
                    .value(400)
                    .counter(counter_reserved)
                    .build();
            Contingent friday = Contingent.builder()
                    .name("Friday")
                    .value(500)
                    .counter(counter_reserved)
                    .build();
            Contingent saturday = Contingent.builder()
                    .name("Saturday")
                    .value(200)
                    .counter(counter_reserved)
                    .build();
            Contingent sunday = Contingent.builder()
                    .name("Sunday")
                    .value(300)
                    .counter(counter_reserved)
                    .build();

            contingentRepository.saveAll(List.of(monday,tuesday,wednesday,thursday,friday,sunday,saturday));
        }
        TenantContextHolder.clear();

        TenantContextHolder.setTenantIdentifier("google");
        if(carParkRepository.count() == 0){

            CarPark carPark_one = CarPark.builder()
                    .carParkName("ford")
                    .carParkNumber("#_6")
                    .timezone("Roma/Europa")
                    .build();

            CarPark carPark_two = CarPark.builder()
                    .carParkName("Volkswagen")
                    .carParkNumber("#_9")
                    .timezone("Madrid/Europa")
                    .build();

            CarPark carPark_three = CarPark.builder()
                    .carParkName("jetta")
                    .carParkNumber("#_2")
                    .timezone("Valencia/Europa")
                    .build();

            CarPark carPark_four = CarPark.builder()
                    .carParkName("civic")
                    .carParkNumber("#_7")
                    .timezone("Lisbon/Europa")
                    .build();

            CarPark carPark_five = CarPark.builder()
                    .carParkName("audi")
                    .carParkNumber("#_12")
                    .timezone("London/Europa")
                    .build();

            CarPark carPark_six = CarPark.builder()
                    .carParkName("bmw")
                    .carParkNumber("#_8")
                    .timezone("Paris/Europa")
                    .build();

            carParkRepository.saveAll(List.of(carPark_one,carPark_two,carPark_three,
                    carPark_four,carPark_five,carPark_six ));

            Facility facility_one = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB2222.2222.020222")
                    .facilityType(FacilityType.PARKING_LOTS)
                    .facilityNumber("12")
                    .facilityName("facility_12")
                    .description("The number of this facility is 12")
                    .isDeleted(false)
                    .build();

//            SB1111.1111.010111
            Facility facility_two = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB1111.1111.010111")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("43")
                    .facilityName("facility_two")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();

            Facility facility_three = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB3333.3333.030333")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("47")
                    .facilityName("facility_three")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();

            Facility facility_four = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB4444.4444.040444")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("33")
                    .facilityName("facility_four")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();

            Facility facility_five = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB5555.5555.050555")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("67")
                    .facilityName("facility_five")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();

            Facility facility_six = Facility.builder()
                    .carPark(carPark_one)
                    .locationId("SB6666.6666.060666")
                    .facilityType(FacilityType.PARKING_GARAGES)
                    .facilityNumber("57")
                    .facilityName("facility_six")
                    .description("this facility is for orange group")
                    .isDeleted(false)
                    .build();

            facilityRepository.saveAll(List.of(facility_one,facility_two,facility_three,facility_four,facility_five,facility_six));

            Counter counter_reserved = Counter.builder()
                    .category("Reserved")
                    .capacity(200)
                    .occupied(50)
                    .available(50)
//                    .occupied(50)
//                    .available(150)
                    .facility(facility_one)
                    .build();

            Counter counter_non_reserved = Counter.builder()
                    .category("Non_Reserved")
                    .capacity(400)
                    .occupied(100)
                    .available(50)
//                    .occupied(200)
//                    .available(200)
                    .facility(facility_one)
                    .build();

            Counter counter_pre_booked = Counter.builder()
                    .category("Pre_Booked")
                    .capacity(400)
                    .occupied(200)
                    .available(50)
//                    .occupied(250)
//                    .available(150)
                    .facility(facility_one)
                    .build();

            Counter counter_physic = Counter.builder()
                    .category("Physic")
                    .capacity(1000)
                    .occupied(350)
                    .available(150)
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
