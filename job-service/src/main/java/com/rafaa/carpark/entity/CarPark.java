package com.rafaa.carpark.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "car_park")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class CarPark {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "car_park_number",unique = true, nullable = false)
    private String carParkNumber;
    @Column(name = "timezone")
    private String timezone;
    @Column(name = "car_park_name")
    private String carParkName;
}
