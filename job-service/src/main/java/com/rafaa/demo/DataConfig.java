package com.rafaa.demo;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.repository.CarParkRepository;
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

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Configuration(proxyBeanMethods = false)
public class DataConfig {

    private final CarParkRepository carParkRepository;
    private final FacilityRepository facilityRepository;
    private final CounterRepository counterRepository;
    private final ContingentRepository contingentRepository;

    public DataConfig(CarParkRepository carParkRepository, FacilityRepository facilityRepository,
                      CounterRepository counterRepository, ContingentRepository contingentRepository) {
        this.carParkRepository = carParkRepository;
        this.facilityRepository = facilityRepository;
        this.counterRepository = counterRepository;
        this.contingentRepository = contingentRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData(){

        List<String> timezones = List.of("Europe/Paris","Europe/Berlin",
                "Europe/Sofia","Europe/Madrid","Europe/Athens",
                "Europe/Tallinn","Europe/Moscow","Australia/Melbourne",
                "Europe/Prague","Europe/Bucharest");

        List<String> carParkNames = List.of("ParkMentor","Parking Lot Ice","Parking Lot Hop","Pro Parking Lot",
                "Parking Lot Io","Parking Lot Me","Go Parking Lot","Parking Lot Up","We Parking Lot ",
                "Re Parking Lot","Parking Lot Excellence","Parking-a-Rama","Davie Parking Systems","Parkland Parking",
                "Co Parking");

        List<String> carParkNumbers = List.of("#_1","#_2","#_3","#_4","#_5","#_6","#_7","#_8","#_9","#_10","#11",
                "#_12","#_13","#_14","#_15","#_16","#_17","#_18","#_19","#_20","#_21","#22",
                "#_23","#_24","#_25","#_26","#_27","#_28","#_29","#_30","#_31","#_32","#_33",
                "#_34","#_35","#_36","#_37","#_38","#_39","#_40","#_41","#_42","#_42","#_43");

        List<String> locationsId = List.of("SB1111.1111.010111",
                "SB2222.2222.020222", "SB3333.3333.030333", "SB4444.4444.050555",
                "SB6666.6666.060666", "SB7777.7777.070777", "SB8888.8888.080888",
                "SB9999.9999.090999", "SB1222.2122.010212", "SB3133.3313.020323",
                "SB2434.4644.070575", "SB6261.6364.050696", "SB1727.7477.070737",
                "SB1488.8888.080898", "SB9419.9299.090997", "SB5419.9295.090997",
                "SB2134.4614.070515", "SB9261.4363.040696", "SB1823.7477.060739",
                "SB8434.4614.010575", "SB3261.1364.010696", "SB3721.7477.030731",
                "SB5431.1641.020575", "SB9261.1314.050691", "SB8121.7417.010717",
                "SB9934.4144.010575", "SB2361.1361.070686", "SB8721.1476.080731",
                "SB2034.0644.670075", "SB8461.6267.190616", "SB9728.7477.090111",
                "SB0134.0614.070515", "SB9061.4303.140696", "SB1803.0407.060709",
                "SB4030.4014.501507", "SB1201.4303.140608", "SB1205.7007.164739",
                "SB9004.4014.970515", "SB1200.4060.140690", "SB8411.3400.167739",
                "SB2030.0911.190588", "SB9061.4300.009491", "SB1020.0470.960739"
                );

        List<FacilityType> facilityTypes = List.of(FacilityType.PARKING_GARAGES,FacilityType.PARK_AND_RIDE,
                FacilityType.PARKING_LOTS,FacilityType.SMART_PARKING,FacilityType.VALET_PARKING);

        List<String> facilityNumbers = List.of("#_1","#_2","#_3","#_4","#_5","#_6","#_7","#_8","#_9","#_10","#11",
                "#_12","#_13","#_14","#_15","#_16","#_17","#_18","#_19","#_20","#_21","#22",
                "#_23","#_24","#_25","#_26","#_27","#_28","#_29","#_30","#_31","#_32","#33",
                "#_34","#_35","#_36","#_37","#_38","#_39","#_40","#_41","#_42","#_42","#_43",
                "#_44","#_45","#_46","#_47","#_48","#_49","#_50","#_51","#_52","#_53","#_54"
                );

        List<Integer> counterCapacities = List.of(50,60,70,80,90,100,110,120,130,140,150,160,170,180,190);

        Random random = new Random();

        TenantContextHolder.setTenantIdentifier("meta");
        if(carParkRepository.count() == 0){

            IntStream.range(1,4).forEach(i -> {

                int randomIndexForCarParNames = random.nextInt(carParkNames.size());
                String carParkName = carParkNames.get(randomIndexForCarParNames);
                int randomIndexForCarParkNumbers = random.nextInt(carParkNumbers.size());
                String carParkNumber = carParkNumbers.get(randomIndexForCarParkNumbers);
                int randomIndexForTimezone = random.nextInt(timezones.size());
                String timezone = timezones.get(randomIndexForTimezone);

                CarPark carPark = CarPark.builder()
                        .timezone(timezone)
                        .carParkNumber(carParkNumber)
                        .carParkName(carParkName)
                        .build();

                carParkRepository.save(carPark);

                IntStream.range(1,3).forEach(j -> {

                   int randomIndexForLocationId = random.nextInt(locationsId.size());
                   String locationId = locationsId.get(randomIndexForLocationId);
                   int randomIndexForFacilityType = random.nextInt(facilityTypes.size());
                   FacilityType facilityType = facilityTypes.get(randomIndexForFacilityType);
                   int randomIndexForFacilityNumber = random.nextInt(facilityNumbers.size());
                   String facilityNumber = facilityNumbers.get(randomIndexForFacilityNumber);
                   String facilityName = "fa_ci_lity_" + facilityNumber;
                   String description = "This is facility: " + locationId;
                   boolean isDeleted = false;

                   Facility facility = Facility.builder()
                           .carPark(carPark)
                           .locationId(locationId)
                           .facilityType(facilityType)
                           .facilityNumber(facilityNumber)
                           .facilityName(facilityName)
                           .description(description)
                           .isDeleted(isDeleted)
                           .build();

                   facilityRepository.save(facility);

                   int randomIndexForCounterCapacitiesReserved = random.nextInt(counterCapacities.size());
                   int capacity_reserved = counterCapacities.get(randomIndexForCounterCapacitiesReserved);
                   int occupied_reserved = capacity_reserved / 2;
                   int available_reserved = capacity_reserved - occupied_reserved;

                    Counter counter_reserved = Counter.builder()
                            .category("Reserved")
                            .capacity(capacity_reserved)
                            .occupied(occupied_reserved)
                            .available(available_reserved)
                            .facility(facility)
                            .build();

                    int randomIndexForCounterCapacitiesNonReserved = random.nextInt(counterCapacities.size());
                    int capacity_no_reserved = counterCapacities.get(randomIndexForCounterCapacitiesNonReserved);
                    int occupied_no_reserved = capacity_no_reserved / 2;
                    int available_no_reserved = capacity_no_reserved - occupied_no_reserved;

                    Counter counter_non_reserved = Counter.builder()
                            .category("Non_Reserved")
                            .capacity(capacity_no_reserved)
                            .occupied(occupied_no_reserved)
                            .available(available_no_reserved)
                            .facility(facility)
                            .build();

                    int randomIndexForCounterCapacitiesPreBooked = random.nextInt(counterCapacities.size());
                    int capacity_pre_booked = counterCapacities.get(randomIndexForCounterCapacitiesPreBooked);
                    int occupied_pre_booked = capacity_pre_booked / 2;
                    int available_pre_booked = capacity_pre_booked - occupied_pre_booked;

                    Counter counter_pre_booked = Counter.builder()
                            .category("Pre_Booked")
                            .capacity(capacity_pre_booked)
                            .occupied(occupied_pre_booked)
                            .available(available_pre_booked)
                            .facility(facility)
                            .build();

                    int capacity_physical =  capacity_reserved + capacity_no_reserved + capacity_pre_booked;
                    int occupied_physical = occupied_reserved + occupied_no_reserved + occupied_pre_booked;
                    int available_physical = occupied_reserved + occupied_no_reserved + occupied_pre_booked;

                    Counter counter_physical = Counter.builder()
                            .category("Physical")
                            .capacity(capacity_physical)
                            .occupied(occupied_physical)
                            .available(available_physical)
                            .facility(facility)
                            .build();

                    counterRepository.saveAll(List.of(counter_reserved,counter_non_reserved,counter_pre_booked,counter_physical));

                });

            });

        }

        TenantContextHolder.clear();

        TenantContextHolder.setTenantIdentifier("google");
        if(carParkRepository.count() == 0){

            IntStream.range(1,4).forEach(i -> {

                int randomIndexForCarParNames = random.nextInt(carParkNames.size());
                String carParkName = carParkNames.get(randomIndexForCarParNames);
                int randomIndexForCarParkNumbers = random.nextInt(carParkNumbers.size());
                String carParkNumber = carParkNumbers.get(randomIndexForCarParkNumbers);
                int randomIndexForTimezone = random.nextInt(timezones.size());
                String timezone = timezones.get(randomIndexForTimezone);

                CarPark carPark = CarPark.builder()
                        .timezone(timezone)
                        .carParkNumber(carParkNumber)
                        .carParkName(carParkName)
                        .build();

                carParkRepository.save(carPark);

                IntStream.range(1,3).forEach(j -> {

                    int randomIndexForLocationId = random.nextInt(locationsId.size());
                    String locationId = locationsId.get(randomIndexForLocationId);
                    int randomIndexForFacilityType = random.nextInt(facilityTypes.size());
                    FacilityType facilityType = facilityTypes.get(randomIndexForFacilityType);
                    int randomIndexForFacilityNumber = random.nextInt(facilityNumbers.size());
                    String facilityNumber = facilityNumbers.get(randomIndexForFacilityNumber);
                    String facilityName = "fa_ci_lity_" + facilityNumber;
                    String description = "This is facility: " + locationId;
                    boolean isDeleted = false;

                    Facility facility = Facility.builder()
                            .carPark(carPark)
                            .locationId(locationId)
                            .facilityType(facilityType)
                            .facilityNumber(facilityNumber)
                            .facilityName(facilityName)
                            .description(description)
                            .isDeleted(isDeleted)
                            .build();

                    facilityRepository.save(facility);

                    int randomIndexForCounterCapacitiesReserved = random.nextInt(counterCapacities.size());
                    int capacity_reserved = counterCapacities.get(randomIndexForCounterCapacitiesReserved);
                    int occupied_reserved = capacity_reserved / 2;
                    int available_reserved = capacity_reserved - occupied_reserved;

                    Counter counter_reserved = Counter.builder()
                            .category("Reserved")
                            .capacity(capacity_reserved)
                            .occupied(occupied_reserved)
                            .available(available_reserved)
                            .facility(facility)
                            .build();

                    int randomIndexForCounterCapacitiesNonReserved = random.nextInt(counterCapacities.size());
                    int capacity_no_reserved = counterCapacities.get(randomIndexForCounterCapacitiesNonReserved);
                    int occupied_no_reserved = capacity_no_reserved / 2;
                    int available_no_reserved = capacity_no_reserved - occupied_no_reserved;

                    Counter counter_non_reserved = Counter.builder()
                            .category("Non_Reserved")
                            .capacity(capacity_no_reserved)
                            .occupied(occupied_no_reserved)
                            .available(available_no_reserved)
                            .facility(facility)
                            .build();

                    int randomIndexForCounterCapacitiesPreBooked = random.nextInt(counterCapacities.size());
                    int capacity_pre_booked = counterCapacities.get(randomIndexForCounterCapacitiesPreBooked);
                    int occupied_pre_booked = capacity_pre_booked / 2;
                    int available_pre_booked = capacity_pre_booked - occupied_pre_booked;

                    Counter counter_pre_booked = Counter.builder()
                            .category("Pre_Booked")
                            .capacity(capacity_pre_booked)
                            .occupied(occupied_pre_booked)
                            .available(available_pre_booked)
                            .facility(facility)
                            .build();

                    int capacity_physical =  capacity_reserved + capacity_no_reserved + capacity_pre_booked;
                    int occupied_physical = occupied_reserved + occupied_no_reserved + occupied_pre_booked;
                    int available_physical = occupied_reserved + occupied_no_reserved + occupied_pre_booked;

                    Counter counter_physical = Counter.builder()
                            .category("Physical")
                            .capacity(capacity_physical)
                            .occupied(occupied_physical)
                            .available(available_physical)
                            .facility(facility)
                            .build();

                    counterRepository.saveAll(List.of(counter_reserved,counter_non_reserved,counter_pre_booked,counter_physical));

                });

            });
        }

        TenantContextHolder.clear();

    }

}