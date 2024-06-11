package com.rafaa.job.service;

import com.rafaa.JobServiceApplication;
import com.rafaa.job.dto.History;
import com.rafaa.multitenancy.context.TenantContextHolder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobsListener implements JobListener{

    @Autowired
    public HistoryRepository historyRepository;



    @Override
    public String getName() {
        return "globalJob";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        //    JobExecutionContext:
        //    trigger: 'DEFAULT.meta_resetting_6dd6ec86-1734-44a2-9344-a5531b6dc785
        //    job: SampleGroup.meta_resetting_6dd6ec86-1734-44a2-9344-a5531b6dc785
        //    fireTime: 'Tue Jun 11 10:03:00 CET 2024
        //    scheduledFireTime: Tue Jun 11 10:03:00 CET 2024
        //    previousFireTime: 'Tue Jun 11 10:02:00 CET 2024
        //    nextFireTime: Tue Jun 11 10:04:00 CET 2024
        //    isRecovering: false
        //    refireCount: 0
//                scheduled_fire_time VARCHAR(255),
//                previous_fire_time VARCHAR(255),
//                next_fire_time VARCHAR(255),
        System.out.println("JobsListener.jobToBeExecuted()");
        System.out.println(context.getPreviousFireTime());
        System.out.println(context.getFireTime());
        System.out.println(context.getNextFireTime());
        System.out.println(context);
        TenantContextHolder.setTenantIdentifier(JobServiceApplication.tenant);
        System.out.println(TenantContextHolder.getTenantIdentifier());
        String jobName = context.getJobDetail().getKey().getName();
        History history = History.builder()
                .jobName(jobName)
                .previousFireTime(String.valueOf(context.getPreviousFireTime()))
                .scheduledFireTime(String.valueOf(context.getFireTime()))
                .nextFireTime(String.valueOf(context.getNextFireTime()))
                .build();
        System.out.println(history);
        historyRepository.save(history);
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("JobsListener.jobExecutionVetoed()");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("JobsListener.jobWasExecuted()");
        System.out.println("==========================================================");
        System.out.println(context.getJobDetail().getKey().getName());
    }

}