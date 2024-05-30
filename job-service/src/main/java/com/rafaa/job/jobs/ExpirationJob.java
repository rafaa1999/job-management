package com.rafaa.job.jobs;

import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.job.service.JobService;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ExpirationJob extends QuartzJobBean implements InterruptableJob{

    private volatile boolean toStopFlag = true;
    private final JobService jobService;

    public ExpirationJob(JobService jobService) {
        this.jobService = jobService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        System.out.println("Stopping thread... ");
        toStopFlag = false;
    }

}
