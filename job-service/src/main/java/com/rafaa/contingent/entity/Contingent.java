package com.rafaa.contingent.entity;

import com.rafaa.counter.entity.Counter;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor @Builder
public class Contingent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
//    private Integer value;
    @Column(name = "normal_value")
    private Integer normalValue;
    @Column(name = "weekend_value")
    private Integer weekendValue;
    @Column(name = "start_date")
//    private LocalDate startDate;
    private Date startDate;
    @Column(name = "end_date")
//    private LocalDate endDate;
    private Date endDate;
//    @Column(name = "day_of_week")
//    private DayOfWeek dayOfWeek;
//    private Date dayOfWeek;
    @Column(name = "start_day_of_week")
    private Date startDayOfWeek;
    @Column(name = "end_day_of_week")
    private Date endDayOfWeek;
    @Column(name = "car_park_id")
    private UUID carParkId;
    @Column(name = "facility_id")
    private UUID facilityId;
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "counter_id")
//    private Counter counter;
}
