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
        System.out.println("JobsListener.jobToBeExecuted()");
        TenantContextHolder.setTenantIdentifier(JobServiceApplication.tenant);
        System.out.println(TenantContextHolder.getTenantIdentifier());
        String jobName = context.getJobDetail().getKey().getName();
        History history = History.builder()
                .jobName(jobName)
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