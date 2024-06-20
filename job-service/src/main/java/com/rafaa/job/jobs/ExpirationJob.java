package com.rafaa.job.jobs;

import com.rafaa.JobServiceApplication;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.job.service.JobService;
import com.rafaa.multitenancy.context.TenantContextHolder;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
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
    @SchedulerLock(name = "ExpirationJob", lockAtMostFor = "15s", lockAtLeastFor = "15s")
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        TenantContextHolder.setTenantIdentifier(JobServiceApplication.tenant);
        String expirationDate = context.getJobDetail().getKey().getName();
        String[] tabs = expirationDate.split("_");
        String jobName = tabs[0] + "_" + tabs[1] + "_" + tabs[2];
        jobService.pauseJob(jobName);
        jobService.deleteJob(expirationDate);
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        System.out.println("Stopping thread... ");
        toStopFlag = false;
    }

}
