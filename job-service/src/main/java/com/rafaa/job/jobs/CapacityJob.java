package com.rafaa.job.jobs;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CapacityJob extends QuartzJobBean implements InterruptableJob{

    private volatile boolean toStopFlag = true;

    @Override
    @SchedulerLock(name = "CapacityJob", lockAtMostFor = "15s", lockAtLeastFor = "15s")
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("this is a capacity job is running !!!");
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        System.out.println("Stopping thread... ");
        toStopFlag = false;
    }

}
