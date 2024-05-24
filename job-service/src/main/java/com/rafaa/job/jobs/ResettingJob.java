package com.rafaa.job.jobs;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.service.CarParkService;
import com.rafaa.counter.service.CounterService;
import com.rafaa.facility.service.FacilityService;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class ResettingJob extends QuartzJobBean implements InterruptableJob {

    private final CarParkService carParkService;
    private final FacilityService facilityService;
    private final CounterService counterService;

    public ResettingJob(CarParkService carParkService, FacilityService facilityService, CounterService counterService) {
        this.carParkService = carParkService;
        this.facilityService = facilityService;
        this.counterService = counterService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        final List<CarPark> carParks = carParkService.getAllCarParks(null,null);
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
    }

}
