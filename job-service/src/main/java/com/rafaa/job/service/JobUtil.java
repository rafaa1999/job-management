package com.rafaa.job.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import com.rafaa.JobServiceApplication;
import com.rafaa.carpark.entity.CarPark;
import com.rafaa.facility.entity.Facility;
import com.rafaa.facility.repository.FacilityRepository;
import com.rafaa.job.config.PersistableCronTriggerFactoryBean;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;


class JobUtil {

    /**
     * Create Quartz Job.
     *
     * @param jobClass Class whose executeInternal() method needs to be called.
     * @param isDurable Job needs to be persisted even after completion. if true, job will be persisted, not otherwise.
     * @param context Spring application context.
     * @param jobName Job name.
     * @param jobGroup Job group.
     *
     * @return JobDetail object
     */
    protected static JobDetail createJob(Class<? extends QuartzJobBean> jobClass, boolean isDurable,
                                         ApplicationContext context, String jobName, String jobGroup){
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(isDurable);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobName);
        factoryBean.setGroup(jobGroup);

        // set job data map
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("myKey", "myValue");
        factoryBean.setJobDataMap(jobDataMap);

        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    /**
     * Create cron trigger.
     *
     * @param triggerName Trigger name.
     * @param startTime Trigger start time.
     * @param cronExpression Cron expression.
     * @param misFireInstruction Misfire instruction (what to do in case of misfire happens).
     *
     * @return Trigger
     */
    protected static Trigger createCronTrigger(String triggerName, Date startTime, String cronExpression, int misFireInstruction,ZoneId zoneId){

        System.out.println("++_+_+#_$)#$+_#$)+#_%)+#_)+#%)#+_$)#+_)$+_@#+$_)+_)+_)#$+@#$+_)#@+$_)@#+_$");
        System.out.println(zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(startTime.toInstant(), zoneId);
        Date adjustedStartTime = Date.from(zonedDateTime.toInstant());
        System.out.println(zonedDateTime);
        System.out.println(adjustedStartTime);

        PersistableCronTriggerFactoryBean factoryBean = new PersistableCronTriggerFactoryBean();
        factoryBean.setName(triggerName);
//        factoryBean.setStartTime(startTime);
        factoryBean.setStartTime(adjustedStartTime);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(misFireInstruction);
        factoryBean.setTimeZone(TimeZone.getTimeZone(zoneId));
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return factoryBean.getObject();
    }

    /**
     * Create a Single trigger.
     *
     * @param triggerName Trigger name.
     * @param startTime Trigger start time.
     * @param misFireInstruction Misfire instruction (what to do in case of misfire happens).
     *
     * @return Trigger
     */
    protected static Trigger createSingleTrigger(String triggerName, Date startTime, int misFireInstruction, ZoneId zoneId){

        System.out.println("++_+_+#_$)#$+_#$)+#_%)+#_)+#%)#+_$)#+_)$+_@#+$_)+_)+_)#$+@#$+_)#@+$_)@#+_$");
        System.out.println(zoneId);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(startTime.toInstant(), zoneId);
        Date adjustedStartTime = Date.from(zonedDateTime.toInstant());
        System.out.println(zonedDateTime);
        System.out.println(adjustedStartTime);

        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setName(triggerName);
//        factoryBean.setStartTime(startTime);
        factoryBean.setStartTime(adjustedStartTime);
        factoryBean.setMisfireInstruction(misFireInstruction);
        factoryBean.setRepeatCount(0);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

}