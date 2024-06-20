package com.rafaa.job.service;

import java.time.ZoneId;
import java.util.*;

import com.rafaa.JobServiceApplication;
import com.rafaa.carpark.entity.CarPark;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.job.dto.History;
import com.rafaa.job.jobs.ExpirationJob;
import com.rafaa.job.jobs.ResettingJob;
import com.rafaa.multitenancy.context.TenantContextHolder;
import org.quartz.*;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import static org.quartz.TriggerKey.triggerKey;


@Service
public class JobServiceImpl implements JobService{

    @Autowired
    @Lazy
    SchedulerFactoryBean schedulerFactoryBean;
    private final TriggerListner triggerListner;

    @Autowired
    private ApplicationContext context;

    @Autowired
    public JobsListener jobsListener;

    @Autowired
    public FacilityRepository facilityRepository;

    @Autowired
    public HistoryRepository historyRepository;

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    public JobServiceImpl(TriggerListner triggerListner) {
        this.triggerListner = triggerListner;
    }

    /**
     * Schedule a job by jobName at given date.
     */
    @Override
    public boolean scheduleOneTimeJob(String jobName, Class<? extends QuartzJobBean> jobClass, Date date) {
        log.info("Request received to scheduleJob");


//        String jobKey = TenantContextHolder.getTenantIdentifier() + "_" + jobName;
        String jobKey = jobName;
        String groupKey = "SampleGroup";
        String triggerKey = jobName;

        JobDetail jobDetail = JobUtil.createJob(jobClass, false, context, jobKey, groupKey);

        UUID facilityId = JobServiceApplication.facilityId;
        Facility facility = facilityRepository.findById(facilityId).get();
        CarPark carPark = facility.getCarPark();
        String timezone = carPark.getTimezone();
        ZoneId zoneId = ZoneId.of(timezone);

        log.info("creating trigger for key : {} at date : {}",jobKey,date);
//        Trigger cronTriggerBean = JobUtil.createSingleTrigger(triggerKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        Trigger cronTriggerBean = JobUtil.createSingleTrigger(triggerKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW,zoneId);
        //Trigger cronTriggerBean = JobUtil.createSingleTrigger(triggerKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
//            scheduler.getListenerManager().addTriggerListener(triggerListner,triggerKey("myTriggerName", "myTriggerGroup"));
            scheduler.getListenerManager().addJobListener(jobsListener);
            Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);
            log.info("Job with key jobKey : {} and group : {} scheduled successfully for date : {}",jobKey,groupKey,dt);
            return true;
        } catch (SchedulerException e) {
            log.error("SchedulerException while scheduling job with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Schedule a job by jobName at given date.
     */
    @Override
    public boolean scheduleCronJob(String jobName, Class<? extends QuartzJobBean> jobClass, Date date, String cronExpression) {
        log.info("Request received to scheduleJob");

        String jobKey = jobName;
        String groupKey = "SampleGroup";
        String triggerKey = jobName;

        JobDetail jobDetail = JobUtil.createJob(jobClass, false, context, jobKey, groupKey);

        JobDetail expirationJobDetail = JobBuilder.newJob(ExpirationJob.class)
                .withIdentity(jobName + "_" + "expiration","SampleGroup")
                .build();
        Trigger expirationTrigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "_" + "expiration")
                .startAt(JobServiceApplication.expirationDate)
                .build();

        UUID facilityId = JobServiceApplication.facilityId;
        Facility facility = facilityRepository.findById(facilityId).get();
        CarPark carPark = facility.getCarPark();
        String timezone = carPark.getTimezone();
        ZoneId zoneId = ZoneId.of(timezone);

        log.info("creating trigger for key : {} at date : {}",jobKey,date);
        Trigger cronTriggerBean = JobUtil.createCronTrigger(triggerKey, date, cronExpression, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW,zoneId);

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            Date dt = scheduler.scheduleJob(jobDetail, cronTriggerBean);
//            JobsListener jobsListener = new JobsListener();
            scheduler.getListenerManager().addJobListener(jobsListener);
            scheduler.scheduleJob(expirationJobDetail,expirationTrigger);
//            History history = History.builder()
//                    .jobName(jobName)
//                    .build();
//            historyRepository.save(history);
//            System.out.println(history);
            System.out.println("Job with key jobKey :"+jobKey+ " and group :"+groupKey+ " scheduled successfully for date :"+dt);
            log.info("Job with key jobKey : {} and group : {} scheduled successfully for date : {} ",jobKey,groupKey,dt);
            return true;
        } catch (SchedulerException e) {
            log.error("SchedulerException while scheduling job with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Update one time scheduled job.
     */
    @Override
    public boolean updateOneTimeJob(String jobName, Date date) {
        log.info("Request received for updating one time job.");

        String jobKey = jobName;

        UUID facilityId = JobServiceApplication.facilityId;
        Facility facility = facilityRepository.findById(facilityId).get();
        CarPark carPark = facility.getCarPark();
        String timezone = carPark.getTimezone();
        ZoneId zoneId = ZoneId.of(timezone);

        log.info("Parameters received for updating one time job : jobKey : {}, date: {}",jobKey,date);
        try {
            //Trigger newTrigger = JobUtil.createSingleTrigger(jobKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
//            Trigger newTrigger = JobUtil.createSingleTrigger(jobKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
            Trigger newTrigger = JobUtil.createSingleTrigger(jobKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW,zoneId);

            Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(triggerKey(jobKey), newTrigger);
            log.info("Trigger associated with jobKey : {} rescheduled successfully for date : {}",jobKey,dt);
            return true;
        } catch ( Exception e ) {
            log.error("SchedulerException while updating one time job with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Update scheduled cron job.
     */
    @Override
    public boolean updateCronJob(String jobName, Date date, String cronExpression) {
        log.info("Request received for updating cron job.");

        String jobKey = jobName;

        UUID facilityId = JobServiceApplication.facilityId;
        Facility facility = facilityRepository.findById(facilityId).get();
        CarPark carPark = facility.getCarPark();
        String timezone = carPark.getTimezone();
        ZoneId zoneId = ZoneId.of(timezone);

        log.info("Parameters received for updating cron job : jobKey : {}, date: {}",jobKey,date);
        try {
            //Trigger newTrigger = JobUtil.createSingleTrigger(jobKey, date, SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
            Trigger newTrigger = JobUtil.createCronTrigger(jobKey, date, cronExpression, SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW,zoneId);

            Date dt = schedulerFactoryBean.getScheduler().rescheduleJob(triggerKey(jobKey), newTrigger);
            log.info("Trigger associated with jobKey : {} rescheduled successfully for date : {}",jobKey,dt);
            return true;
        } catch ( Exception e ) {
            log.error("SchedulerException while updating cron job with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove the indicated Trigger from the scheduler.
     * If the related job does not have any other triggers, and the job is not durable, then the job will also be deleted.
     */
    @Override
    public boolean unScheduleJob(String jobName) {
        log.info("Request received for Unscheduleding job.");

        String jobKey = jobName;

        TriggerKey tkey = new TriggerKey(jobKey);
        log.info("Parameters received for unscheduling job : tkey : {}",jobKey);
        try {
            boolean status = schedulerFactoryBean.getScheduler().unscheduleJob(tkey);
            log.info("Trigger associated with jobKey : {} unscheduled with status : {}",jobKey,status);
            return status;
        } catch (SchedulerException e) {
            log.error("SchedulerException while unscheduling job with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete the identified Job from the Scheduler - and any associated Triggers.
     */
    @Override
    public boolean deleteJob(String jobName) {
        log.info("Request received for deleting job.");

        String jobKey = jobName;
        String groupKey = "SampleGroup";

        JobKey jkey = new JobKey(jobKey, groupKey);
        log.info("Parameters received for deleting job : jobKey : {}",jobKey);

        try {
            boolean status = schedulerFactoryBean.getScheduler().deleteJob(jkey);
            log.info("Job with jobKey : {} deleted with status : {}",jobKey,status);
            return status;
        } catch (SchedulerException e) {
            log.error("SchedulerException while deleting job with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Pause a job
     */
    @Override
    public boolean pauseJob(String jobName) {
        log.info("Request received for pausing job.");


        String jobKey = jobName;
        String groupKey = "SampleGroup";
        JobKey jkey = new JobKey(jobKey, groupKey);
        log.info("Parameters received for pausing job : jobKey : {}, groupKey : {}",jobKey,groupKey);

        try {
            schedulerFactoryBean.getScheduler().pauseJob(jkey);
            log.info("Job with jobKey : {} paused succesfully.",jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("SchedulerException while pausing job with key : {} message : {}",jobName,e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Resume paused job
     */
    @Override
    public boolean resumeJob(String jobName) {
        log.info("Request received for resuming job.");

        String jobKey = jobName;
        String groupKey = "SampleGroup";

        JobKey jKey = new JobKey(jobKey, groupKey);
        log.info("Parameters received for resuming job : jobKey : {}",jobKey);
        try {
            schedulerFactoryBean.getScheduler().resumeJob(jKey);
            log.info("Job with jobKey : {} resumed succesfully.",jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("SchedulerException while resuming job with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Start a job now
     */
    @Override
    public boolean startJobNow(String jobName) {
        log.info("Request received for starting job now.");

        String jobKey = jobName;
        String groupKey = "SampleGroup";

        JobKey jKey = new JobKey(jobKey, groupKey);
        log.info("Parameters received for starting job now : jobKey : {}",jobKey);
        try {
            schedulerFactoryBean.getScheduler().triggerJob(jKey);
            log.info("Job with jobKey : {} started now succesfully.",jobKey);
            return true;
        } catch (SchedulerException e) {
            log.error("SchedulerException while starting job now with key : {} message : {}",jobKey,e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if job is already running
     */
    @Override
    public boolean isJobRunning(String jobName) {
        log.info("Request received to check if job is running");

        String jobKey = jobName;
        String groupKey = "SampleGroup";

        log.info("Parameters received for checking job is running now : jobKey : {}",jobKey);
        try {

            List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
            if(currentJobs!=null){
                for (JobExecutionContext jobCtx : currentJobs) {
                    String jobNameDB = jobCtx.getJobDetail().getKey().getName();
                    String groupNameDB = jobCtx.getJobDetail().getKey().getGroup();
                    if (jobKey.equalsIgnoreCase(jobNameDB) && groupKey.equalsIgnoreCase(groupNameDB)) {
                        return true;
                    }
                }
            }
        } catch (SchedulerException e) {
            log.error("SchedulerException while checking job with key : {} is running. error message : {}",jobKey,e.getMessage());
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * Get all jobs
     */
    @Override
    public List<Map<String, Object>> getAllJobs(String tenantIdentifier) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

                    String jobName = jobKey.getName();
                    String jobGroup = jobKey.getGroup();

                    //get job's trigger
                    List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                    Date scheduleTime = triggers.get(0).getStartTime();
                    Date nextFireTime = triggers.get(0).getNextFireTime();
                    Date lastFiredTime = triggers.get(0).getPreviousFireTime();

                    Map<String, Object> map = new HashMap<String, Object>();

                    String[] tabs = jobName.split("_");
                    System.out.println("#$#($*#(*$#^$#^$#!@!&@^!&@^&^&^&^#$#$(*%(*");
                    System.out.println(tabs[0]);
                    System.out.println(tabs[1]);

                    if(tabs[0].equals(tenantIdentifier)){
                        map.put("jobName", jobName);
                        map.put("groupName", jobGroup);
                        map.put("scheduleTime", scheduleTime);
                        map.put("lastFiredTime", lastFiredTime);
                        map.put("nextFireTime", nextFireTime);

                        if(isJobRunning(jobName)){
                            map.put("jobStatus", "RUNNING");
                        }else{
                            String jobState = getJobState(jobName);
                            map.put("jobStatus", jobState);
                        }
                        list.add(map);

                    }

//                    map.put("jobName", jobName);
//                    map.put("groupName", jobGroup);
//                    map.put("scheduleTime", scheduleTime);
//                    map.put("lastFiredTime", lastFiredTime);
//                    map.put("nextFireTime", nextFireTime);
//
//                    if(isJobRunning(jobName)){
//                        map.put("jobStatus", "RUNNING");
//                    }else{
//                        String jobState = getJobState(jobName);
//                        map.put("jobStatus", jobState);
//                    }

					/*					Date currentDate = new Date();
					if (scheduleTime.compareTo(currentDate) > 0) {
						map.put("jobStatus", "scheduled");

					} else if (scheduleTime.compareTo(currentDate) < 0) {
						map.put("jobStatus", "Running");

					} else if (scheduleTime.compareTo(currentDate) == 0) {
						map.put("jobStatus", "Running");
					}*/

//                    list.add(map);
                    log.info("Job details:");
                    log.info("Job Name: {}, Group Name: {} + , Schedule Time: {}",jobName,groupName,scheduleTime);
                }

            }
        } catch (SchedulerException e) {
            log.error("SchedulerException while fetching all jobs. error message : {}",e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Check job exist with given name
     */
    @Override
    public boolean isJobWithNamePresent(String jobName) {
        try {
            String groupKey = "SampleGroup";
            JobKey jobKey = new JobKey(jobName, groupKey);
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            if (scheduler.checkExists(jobKey)){
                return true;
            }
        } catch (SchedulerException e) {
            log.error("SchedulerException while checking job with name and group exist: {}",e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the current state of job
     */
    public String getJobState(String jobName) {
        log.info("JobServiceImpl.getJobState()");

        try {
            String groupKey = "SampleGroup";
            JobKey jobKey = new JobKey(jobName, groupKey);

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
            if(triggers != null && triggers.size() > 0){
                for (Trigger trigger : triggers) {
                    TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

                    if (TriggerState.PAUSED.equals(triggerState)) {
                        return "PAUSED";
                    }else if (TriggerState.BLOCKED.equals(triggerState)) {
                        return "BLOCKED";
                    }else if (TriggerState.COMPLETE.equals(triggerState)) {
                        return "COMPLETE";
                    }else if (TriggerState.ERROR.equals(triggerState)) {
                        return "ERROR";
                    }else if (TriggerState.NONE.equals(triggerState)) {
                        return "NONE";
                    }else if (TriggerState.NORMAL.equals(triggerState)) {
                        return "SCHEDULED";
                    }

                }
            }
        } catch (SchedulerException e) {
            log.error("SchedulerException while checking job with name and group exist: {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Stop a job
     */
    @Override
    public boolean stopJob(String jobName) {
        log.info("JobServiceImpl.stopJob()");
        try{
            String jobKey = jobName;
            String groupKey = "SampleGroup";

            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jkey = new JobKey(jobKey, groupKey);

            return scheduler.interrupt(jkey);

        } catch (SchedulerException e) {
            log.error("SchedulerException while stopping job. error message : {}",e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}