package com.rafaa.job.jobs;

import com.rafaa.counter.service.CounterService;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SimulationJob extends QuartzJobBean implements InterruptableJob {

    private final CounterService counterService;
    private volatile boolean toStopFlag = true;

    public SimulationJob(CounterService counterService) {
        this.counterService = counterService;
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
