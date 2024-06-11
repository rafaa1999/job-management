package com.rafaa.job.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

//    JobExecutionContext:
//    trigger: 'DEFAULT.meta_resetting_6dd6ec86-1734-44a2-9344-a5531b6dc785
//    job: SampleGroup.meta_resetting_6dd6ec86-1734-44a2-9344-a5531b6dc785
//    fireTime: 'Tue Jun 11 10:03:00 CET 2024
//    scheduledFireTime: Tue Jun 11 10:03:00 CET 2024
//    previousFireTime: 'Tue Jun 11 10:02:00 CET 2024
//    nextFireTime: Tue Jun 11 10:04:00 CET 2024
//    isRecovering: false
//    refireCount: 0

@Entity
@Table(name = "history")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "job_name")
    private String jobName;
    private String status;
    @Column(name = "scheduled_fire_time")
    private String scheduledFireTime;
    @Column(name = "previous_fire_time")
    private String previousFireTime;
    @Column(name = "next_fire_time")
    private String nextFireTime;
}
