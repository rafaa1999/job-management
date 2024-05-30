package com.rafaa.job.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.rafaa.JobServiceApplication;
import com.rafaa.counter.entity.Counter;
import com.rafaa.counter.repository.CounterRepository;
import com.rafaa.counter.service.CounterService;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.service.FacilityService;
import com.rafaa.job.dto.ServerResponse;
import com.rafaa.job.jobs.CapacityJob;
import com.rafaa.job.jobs.CronJob;
import com.rafaa.job.jobs.ResettingJob;
import com.rafaa.job.jobs.SimpleJob;
import com.rafaa.job.service.GlobalObject;
import com.rafaa.job.service.JobService;
import com.rafaa.job.util.ServerResponseCode;
import com.rafaa.multitenancy.context.TenantContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scheduler/")
public class JobController {

    @Autowired
    @Lazy
    JobService jobService;

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    private final CounterService counterService;
    private final CounterRepository counterRepository;


    public JobController(CounterService counterService, CounterRepository counterRepository) {
        this.counterService = counterService;
        this.counterRepository = counterRepository;
    }

    @RequestMapping("schedule/{id}")
    public ServerResponse schedule(@PathVariable UUID id,
                                   @RequestParam("jobName") String jobName,
                                   @RequestParam("jobScheduleTime") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") Date jobScheduleTime,
                                   @RequestParam("cronExpression") String cronExpression,
                                   @RequestParam("tenantId") String tenantId
                                   ){
        log.info("JobController.schedule()");

        System.out.println(jobName);
        System.out.println(jobScheduleTime);
        System.out.println(cronExpression);
        System.out.println(tenantId);

        JobServiceApplication.tenant = tenantId;
        JobServiceApplication.facilityId = id;

//        System.out.println("======== : I want to get the id from the front end");
//        System.out.println(id);

//        GlobalObject globalObject = new GlobalObject();
//        globalObject.setId(id);
//        System.out.println("this is the global object id + " + globalObject.getId());

        JobServiceApplication.id = id;
        System.out.println("this is the global object id + " + JobServiceApplication.id);

        // TODO : ADD TENANT IDENTIFIER TO THE JOB NAME => JOB FOR EACH TENANT

        // Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.JOB_NAME_NOT_PRESENT, false);
        }

        // Check if job Name is unique;
        if(!jobService.isJobWithNamePresent(jobName)){

            // TODO : DELETE THIS AFTER TESTING
//            String tenantIdentifier = TenantContextHolder.getTenantIdentifier();
//            String jobName_tenant = jobName + "_" + tenantIdentifier;

            if(cronExpression == null || cronExpression.trim().equals("")){
                // Single Trigger
//                boolean status = jobService.scheduleOneTimeJob(jobName, SimpleJob.class, jobScheduleTime);

                // TODO: delete what is tagged
                    if(jobName.equals("resetting")){
                        boolean status = jobService.scheduleOneTimeJob(jobName, ResettingJob.class, jobScheduleTime);
                        if(status){
                            return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                        }else{
                            return getServerResponse(ServerResponseCode.ERROR, false);
                        }
                    }else if(jobName.equals("capacity")){
                        boolean status = jobService.scheduleOneTimeJob(jobName, CapacityJob.class, jobScheduleTime);
                        if(status){
                            return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                        }else{
                            return getServerResponse(ServerResponseCode.ERROR, false);
                        }
                    }
                // TODO: delete what is tagged

                boolean status = jobService.scheduleOneTimeJob(jobName, SimpleJob.class, jobScheduleTime);
//                boolean status = jobService.scheduleOneTimeJob(jobName, SimpleJob.class, jobScheduleTime);
                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                }else{
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }

            }else{
                // Cron Trigger

                // TODO: delete what is tagged
                    if(jobName.equals("resetting")){
                        boolean status = jobService.scheduleCronJob(jobName, ResettingJob.class, jobScheduleTime, cronExpression);
                        if(status){
                            return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                        }else{
                            return getServerResponse(ServerResponseCode.ERROR, false);
                        }
                    }else if(jobName.equals("capacity")){
                        boolean status = jobService.scheduleCronJob(jobName, CapacityJob.class, jobScheduleTime, cronExpression);
                        if(status){
                            return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                        }else{
                            return getServerResponse(ServerResponseCode.ERROR, false);
                        }
                    }
                // TODO: delete what is tagged

                boolean status = jobService.scheduleCronJob(jobName, CronJob.class, jobScheduleTime, cronExpression);
                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                }else{
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }
            }
        }else{
            return getServerResponse(ServerResponseCode.JOB_WITH_SAME_NAME_EXIST, false);
        }
    }

    @RequestMapping("unschedule")
    public void unschedule(@RequestParam("jobName") String jobName) {
        log.info("JobController.unschedule()");
        jobService.unScheduleJob(jobName);
    }

    @RequestMapping("delete")
    public ServerResponse delete(@RequestParam("jobName") String jobName) {
        log.info("JobController.delete()");

        System.out.println(jobName);

        if(jobService.isJobWithNamePresent(jobName)){
            boolean isJobRunning = jobService.isJobRunning(jobName);

            if(!isJobRunning){
                boolean status = jobService.deleteJob(jobName);
                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, true);
                }else{
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }
            }else{
                return getServerResponse(ServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE, false);
            }
        }else{
            // Job doesn't exist
            return getServerResponse(ServerResponseCode.JOB_DOESNT_EXIST, false);
        }
    }

    @RequestMapping("pause")
    public ServerResponse pause(@RequestParam("jobName") String jobName) {
        log.info("JobController.pause()");

        System.out.println(jobName);

        if(jobService.isJobWithNamePresent(jobName)){

            boolean isJobRunning = jobService.isJobRunning(jobName);

            if(!isJobRunning){
                boolean status = jobService.pauseJob(jobName);
                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, true);
                }else{
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }
            }else{
                return getServerResponse(ServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE, false);
            }

        }else{
            // Job doesn't exist
            return getServerResponse(ServerResponseCode.JOB_DOESNT_EXIST, false);
        }
    }

    @RequestMapping("resume")
    public ServerResponse resume(@RequestParam("jobName") String jobName) {
        log.info("JobController.resume()");

        System.out.println(jobName);

        if(jobService.isJobWithNamePresent(jobName)){
            String jobState = jobService.getJobState(jobName);

            if(jobState.equals("PAUSED")){
                log.info("Job current state is PAUSED, Resuming job...");
                boolean status = jobService.resumeJob(jobName);

                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, true);
                }else{
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }
            }else{
                return getServerResponse(ServerResponseCode.JOB_NOT_IN_PAUSED_STATE, false);
            }

        }else{
            // Job doesn't exist
            return getServerResponse(ServerResponseCode.JOB_DOESNT_EXIST, false);
        }
    }

    @RequestMapping("update")
    public ServerResponse updateJob(@RequestParam("jobName") String jobName,
                                    @RequestParam("jobScheduleTime") @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm") Date jobScheduleTime,
                                    @RequestParam("cronExpression") String cronExpression){
        log.info("JobController.updateJob()");

        System.out.println(jobName);

        System.out.println(jobScheduleTime);

        System.out.println(cronExpression);

        // Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.JOB_NAME_NOT_PRESENT, false);
        }

        // Edit Job
        if(jobService.isJobWithNamePresent(jobName)){

            if(cronExpression == null || cronExpression.trim().equals("")){
                // Single Trigger
                boolean status = jobService.updateOneTimeJob(jobName, jobScheduleTime);
                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                }else{
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }

            }else{
                // Cron Trigger
                boolean status = jobService.updateCronJob(jobName, jobScheduleTime, cronExpression);
                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, jobService.getAllJobs());
                }else{
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }
            }


        }else{
            return getServerResponse(ServerResponseCode.JOB_DOESNT_EXIST, false);
        }
    }

    @RequestMapping("jobs")
    public ServerResponse getAllJobs(){
        log.info("JobController.getAllJobs()");

        List<Map<String, Object>> list = jobService.getAllJobs();
        return getServerResponse(ServerResponseCode.SUCCESS, list);
    }

    @RequestMapping("checkJobName")
    public ServerResponse checkJobName(@RequestParam("jobName") String jobName){
        log.info("JobController.checkJobName()");

        System.out.println(jobName);

//Job Name is mandatory
        if(jobName == null || jobName.trim().equals("")){
            return getServerResponse(ServerResponseCode.JOB_NAME_NOT_PRESENT, false);
        }

//        String tenantIdentifier = TenantContextHolder.getTenantIdentifier();
//        String jobName_tenant = jobName + "_" + tenantIdentifier;

//        System.out.println(jobName_tenant);

        boolean status = jobService.isJobWithNamePresent(jobName);
        return getServerResponse(ServerResponseCode.SUCCESS, status);
    }

    @RequestMapping("isJobRunning")
    public ServerResponse isJobRunning(@RequestParam("jobName") String jobName) {
        log.info("JobController.isJobRunning()");

        System.out.println(jobName);

        boolean status = jobService.isJobRunning(jobName);
        return getServerResponse(ServerResponseCode.SUCCESS, status);
    }

    @RequestMapping("jobState")
    public ServerResponse getJobState(@RequestParam("jobName") String jobName) {
        log.info("JobController.getJobState()");

        System.out.println(jobName);

        String jobState = jobService.getJobState(jobName);
        return getServerResponse(ServerResponseCode.SUCCESS, jobState);
    }

    @RequestMapping("stop")
    public ServerResponse stopJob(@RequestParam("jobName") String jobName) {
        log.info("JobController.stopJob()");

        System.out.println(jobName);

        if(jobService.isJobWithNamePresent(jobName)){

            if(jobService.isJobRunning(jobName)){
                boolean status = jobService.stopJob(jobName);
                if(status){
                    return getServerResponse(ServerResponseCode.SUCCESS, true);
                }else{
                    // Server error
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }

            }else{
                // Job not in running state
                return getServerResponse(ServerResponseCode.JOB_NOT_IN_RUNNING_STATE, false);
            }

        }else{
            // Job doesn't exist
            return getServerResponse(ServerResponseCode.JOB_DOESNT_EXIST, false);
        }
    }

    @RequestMapping("start")
    public ServerResponse startJobNow(@RequestParam("jobName") String jobName) {
        log.info("JobController.startJobNow()");

        System.out.println(jobName);

        if(jobService.isJobWithNamePresent(jobName)){

            if(!jobService.isJobRunning(jobName)){
                boolean status = jobService.startJobNow(jobName);

                if(status){
                    // Success
                    return getServerResponse(ServerResponseCode.SUCCESS, true);

                }else{
                    // Server error
                    return getServerResponse(ServerResponseCode.ERROR, false);
                }

            }else{
                // Job already running
                return getServerResponse(ServerResponseCode.JOB_ALREADY_IN_RUNNING_STATE, false);
            }

        }else{
            // Job doesn't exist
            return getServerResponse(ServerResponseCode.JOB_DOESNT_EXIST, false);
        }
    }

    public ServerResponse getServerResponse(int responseCode, Object data){
        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setStatusCode(responseCode);
        serverResponse.setData(data);
        return serverResponse;
    }

//     class Counter {
//        private UUID id;
//        private String category;
//        private Integer available;
//        private Integer capacity;
//        private Integer occupied;
//    }

    @RequestMapping("/simulation/add/{id}")
    public ServerResponse simulationAdd(@PathVariable UUID id){
        Counter counter = counterRepository.findById(id).get();
        Facility facility = counter.getFacility();
        counter.setOccupied(counter.getOccupied() + 1);
        counter.setAvailable(counter.getAvailable() - 1);
        Counter phyCounter = new Counter();
        for(Counter c : counterService.getAllCounterByFacilityId(facility.getId())){
           if(c.getCategory().equals("Physic")){
              phyCounter = c;
              phyCounter.setOccupied(phyCounter.getOccupied() +1);
              phyCounter.setAvailable(phyCounter.getAvailable() -1);
           }
        }
        counterRepository.save(counter);
        counterRepository.save(phyCounter);
        return getServerResponse(ServerResponseCode.SUCCESS, true);
    }

    @RequestMapping("/simulation/delete/{id}")
    public ServerResponse simulationDelete(@PathVariable UUID id){
        Counter counter = counterRepository.findById(id).get();
        Facility facility = counter.getFacility();
        counter.setAvailable(counter.getAvailable() + 1);
        counter.setOccupied(counter.getOccupied() - 1);
        Counter phyCounter = new Counter();
        for(Counter c : counterService.getAllCounterByFacilityId(facility.getId())){
            if(c.getCategory().equals("Physic")){
                phyCounter = c;
                phyCounter.setAvailable(phyCounter.getAvailable() + 1);
                phyCounter.setOccupied(phyCounter.getOccupied() - 1);
            }
        }
        counterRepository.save(counter);
        counterRepository.save(phyCounter);
        return getServerResponse(ServerResponseCode.SUCCESS, true);
    }

}