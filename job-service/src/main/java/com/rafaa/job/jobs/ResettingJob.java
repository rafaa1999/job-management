package com.rafaa.job.jobs;

import com.rafaa.JobServiceApplication;
import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.service.CarParkService;
import com.rafaa.counter.entity.Counter;
import com.rafaa.counter.repository.CounterRepository;
import com.rafaa.counter.service.CounterService;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.facility.service.FacilityService;
import com.rafaa.job.service.GlobalObject;
import com.rafaa.multitenancy.context.TenantContextHolder;
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
    private final FacilityRepository facilityRepository;
    private final CounterRepository counterRepository;

    public ResettingJob(CarParkService carParkService, FacilityService facilityService, CounterService counterService, FacilityRepository facilityRepository, CounterRepository counterRepository) {
        this.carParkService = carParkService;
        this.facilityService = facilityService;
        this.counterService = counterService;
        this.facilityRepository = facilityRepository;
        this.counterRepository = counterRepository;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("this is the resetting job is running");
        System.out.println("================= : this the facility id");
        TenantContextHolder.setTenantIdentifier(JobServiceApplication.tenant);
//        System.out.println(JobServiceApplication.id);
        System.out.println("==================");
        System.out.println(JobServiceApplication.facilityId);
//        Facility facility = facilityRepository.findById(JobServiceApplication.id).get();
//
//        System.out.println(facility);
//        System.out.println(facility.getFacilityName());
        List<Counter> counters = counterService.getAllCounterByFacilityId(JobServiceApplication.id);
        for(Counter c: counters){
           c.setAvailable(c.getCapacity());
           c.setOccupied(0);
           counterRepository.save(c);
        }

//        for(Counter c: counters){
//            System.out.println(c);
//        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
    }

}
