package com.rafaa.job.jobs;

import com.rafaa.JobServiceApplication;
import com.rafaa.carpark.entity.CarPark;
import com.rafaa.carpark.repository.CarParkRepository;
import com.rafaa.carpark.service.CarParkService;
import com.rafaa.contingent.entity.Contingent;
import com.rafaa.contingent.service.ContingentService;
import com.rafaa.counter.entity.Counter;
import com.rafaa.counter.repository.CounterRepository;
import com.rafaa.counter.service.CounterService;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.facility.service.FacilityService;
import com.rafaa.multitenancy.context.TenantContextHolder;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CapacityJob extends QuartzJobBean implements InterruptableJob{

    private volatile boolean toStopFlag = true;

    private final CarParkService carParkService;
    private final FacilityService facilityService;
    private final CounterService counterService;
    private final FacilityRepository facilityRepository;
    private final CounterRepository counterRepository;
    private final CarParkRepository carParkRepository;
    private final ContingentService contingentService;

    public CapacityJob(CarParkService carParkService, FacilityService facilityService, CounterService counterService, FacilityRepository facilityRepository, CounterRepository counterRepository, CarParkRepository carParkRepository, ContingentService contingentService) {
        this.carParkService = carParkService;
        this.facilityService = facilityService;
        this.counterService = counterService;
        this.facilityRepository = facilityRepository;
        this.counterRepository = counterRepository;
        this.carParkRepository = carParkRepository;
        this.contingentService = contingentService;
    }

    @Override
    @SchedulerLock(name = "CapacityJob", lockAtMostFor = "15s", lockAtLeastFor = "15s")
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("this is a capacity job is running !!!");
        System.out.println("this is the resetting job is running");
        System.out.println("================= : this the facility id");
        TenantContextHolder.setTenantIdentifier(JobServiceApplication.tenant);
        System.out.println(JobServiceApplication.id);
//        System.out.println("==================");
//        System.out.println(JobServiceApplication.facilityId);

        List<CarPark> carParks = carParkRepository.findAll();
//        System.out.println(carParks);
        List<CarPark> carParksWithSameTimezone = new ArrayList<>();
        CarPark carPark = facilityRepository.findById(JobServiceApplication.facilityId).get().getCarPark();
//        System.out.println(carPark);

//        System.out.println(carParksWithSameTimezone);

//        System.out.println(carPark);
//        Facility facility = facilityRepository.findById(JobServiceApplication.id).get();
//
//        System.out.println(facility);
//        System.out.println(facility.getFacilityName());
        List<Counter> counters = counterService.getAllCounterByFacilityId(JobServiceApplication.id);
        List<Contingent> contingents = contingentService.getContingentsByCounterId(counters.get(0).getId());
        Contingent contingent = contingents.get(0);

        System.out.println(counters);
        System.out.println(contingent);


        for(Counter c: counters){
            System.out.println(c);
            c.setCapacity(contingent.getValue());
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
        System.out.println("Stopping thread... ");
        toStopFlag = false;
    }

}
