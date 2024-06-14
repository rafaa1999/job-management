package com.rafaa.facility.entity;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.facility.enums.FacilityType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_park_id")
    private CarPark carPark;
//    @Column(name = "location_id",unique = true)
    private String locationId;
    @Column(name = "facility_type")
    private FacilityType facilityType;
//    @Column(name = "facility_number",unique = true)
    private String facilityNumber;
    @Column(name = "facility_name")
    private String facilityName;
    private String description;
    @Column(name = "is_deleted",nullable = false,columnDefinition = "boolean default false")
    private boolean isDeleted;
}
