package com.rafaa.counter.entity;

import com.rafaa.facility.entity.Facility;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String category;
    private Integer available;
    private Integer capacity;
    private Integer occupied;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facility_id")
    private Facility facility;
}
