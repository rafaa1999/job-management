package com.rafaa.contingent.entity;

import com.rafaa.counter.entity.Counter;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

//@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class Contingent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Integer value;
//    @Column(name = "start_date")
    private LocalDate startDate;
//    @Column(name = "end_date")
    private LocalDate endDate;
//    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;
    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "counter_id")
    @JoinColumn(name = "counterId")
    private Counter counter;
}
