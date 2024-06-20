package com.rafaa.contingent.controller;

import com.rafaa.contingent.entity.Contingent;
import com.rafaa.contingent.service.ContingentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contingents")
public class ContingentRestController {

    private static final Logger log = LoggerFactory.getLogger(ContingentRestController.class);
    private final ContingentService contingentService;

    public ContingentRestController(ContingentService contingentService) {
        this.contingentService = contingentService;
    }

//    @GetMapping("/counter/{counter_id}")
//    public ResponseEntity<List<Contingent>> getContingentsByCounterId(@PathVariable(name = "counter_id") UUID counter_id){
//        log.info("ContingentRestController.getContingentsByCounterId()");
//        return ResponseEntity.ok(contingentService.getContingentsByCounterId(counter_id));
//    }

    @GetMapping
    public List<Contingent> getAllContingents(){
        log.info("ContingentRestController.getAllContingents()");
        return contingentService.getAllContingent();
    }

    @RequestMapping("/add")
    public boolean addContingent(@RequestParam(name = "name") String name,
                              @RequestParam(name = "normalDate") String normalDate,@RequestParam(name = "normalValue") Integer normalValue,
                              @RequestParam(name = "weekendDate") String weekendDate,@RequestParam(name = "weekendValue") Integer weekendValue,
                              @RequestParam(name = "carParkId") UUID carParkId,@RequestParam(name = "facilityId") UUID facilityId){

        log.info("ContingentRestController.addContingent()");

        boolean checkContingentNameExistence = contingentService.checkContingentExist(name);

        if(!checkContingentNameExistence){
           return false;
        }

        String[] normalDateList = normalDate.split("-");
        String[] weekendDateList = weekendDate.split("-");

        System.out.println("++++++++++++_=============");

        for(String s: normalDateList){
            System.out.println(s);
        }

        for(String s: weekendDateList){
            System.out.println(s);
        }

        String StringStartDate = normalDateList[0];
        System.out.println(StringStartDate);
        String StringEndDate = normalDateList[1];

        String StringStartDayOfWeek = weekendDateList[0];
        String StringEndDayOfWeek = weekendDateList[1];

//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4)
                .appendLiteral('/')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral('/')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();

        LocalDate localStartDate = LocalDate.parse(StringStartDate,formatter);
        LocalDate localEndDate = LocalDate.parse(StringEndDate,formatter);

        LocalDate localStartDayOfWeek = LocalDate.parse(StringStartDayOfWeek,formatter);
        LocalDate localEndDayOfWeek = LocalDate.parse(StringEndDayOfWeek,formatter);

        Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date startDayOfWeek = Date.from(localStartDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDayOfWeek = Date.from(localEndDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // TODO: write condition for the date

        contingentService.addContingent(name,startDate,endDate,normalValue,
                                        startDayOfWeek,endDayOfWeek,weekendValue,
                                         carParkId,facilityId);

        return true;
    }

    @RequestMapping("/check")
    public boolean checkIfContingentExist(@RequestParam(name = "name") String name){
        log.info("ContingentRestController.checkIfContingentExist()");
        boolean check = contingentService.checkContingentExist(name);
        return check;
    }

    @RequestMapping("/delete/{id}")
    public void deleteContingent(@PathVariable(name = "id") UUID id){
        log.info("ContingentRestController.deleteContingent()");
        contingentService.deleteContingent(id);
    }

    @RequestMapping("/update/{id}")
    public boolean updateContingent(@PathVariable(name = "id") UUID id,@RequestParam(name = "name") String name,
                                 @RequestParam(name = "normalDate") String normalDate,@RequestParam(name = "normalValue") Integer normalValue,
                                 @RequestParam(name = "weekendDate") String weekendDate,@RequestParam(name = "weekendValue") Integer weekendValue,
                                 @RequestParam(name = "carParkId") UUID carParkId,@RequestParam(name = "facilityId") UUID facilityId){

        log.info("ContingentRestController.addContingent()");

        boolean checkContingentNameExistence = contingentService.checkContingentExist(name);

        String[] normalDateList = normalDate.split("-");
        String[] weekendDateList = weekendDate.split("-");

        System.out.println("++++++++++++_=============");

        for(String s: normalDateList){
            System.out.println(s);
        }

        for(String s: weekendDateList){
            System.out.println(s);
        }

        String StringStartDate = normalDateList[0];
        System.out.println(StringStartDate);
        String StringEndDate = normalDateList[1];

        String StringStartDayOfWeek = weekendDateList[0];
        String StringEndDayOfWeek = weekendDateList[1];

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4)
                .appendLiteral('/')
                .appendValue(ChronoField.MONTH_OF_YEAR)
                .appendLiteral('/')
                .appendValue(ChronoField.DAY_OF_MONTH)
                .toFormatter();

        LocalDate localStartDate = LocalDate.parse(StringStartDate,formatter);
        LocalDate localEndDate = LocalDate.parse(StringEndDate,formatter);

        LocalDate localStartDayOfWeek = LocalDate.parse(StringStartDayOfWeek,formatter);
        LocalDate localEndDayOfWeek = LocalDate.parse(StringEndDayOfWeek,formatter);

        Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date startDayOfWeek = Date.from(localStartDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDayOfWeek = Date.from(localEndDayOfWeek.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // TODO: write condition for the date

        contingentService.updateContingent(id,name,startDate,endDate,normalValue,
                startDayOfWeek,endDayOfWeek,weekendValue,
                carParkId,facilityId);

        return true;
    }

    @RequestMapping("/contingent/{id}")
    public Contingent getContingent(@PathVariable(name = "id") UUID id){
        log.info("ContingentRestController.getContingent()");
        return contingentService.getContingent(id);
    }

}