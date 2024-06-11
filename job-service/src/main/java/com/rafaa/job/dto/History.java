package com.rafaa.job.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "history")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
//    @Column(name = "car_park_id")
//    private UUID carParkId;
    @Column(name = "job_name")
    private String jobName;
    private String status;
}
