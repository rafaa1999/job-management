package com.rafaa.contingent.service;

import com.rafaa.contingent.entity.Contingent;
import com.rafaa.contingent.repository.ContingentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContingentService {

    private static final Logger log = LoggerFactory.getLogger(ContingentService.class);
    private final ContingentRepository contingentRepository;

    public ContingentService(ContingentRepository contingentRepository) {
        this.contingentRepository = contingentRepository;
    }

//    public List<Contingent> getContingentsByCounterId(UUID counterId) {
//        List<Contingent> contingents = contingentRepository.getContingentsByCounterId(counterId);
//        log.info("Returning all contingents: {}");
//        return contingents;
//    }

    public List<Contingent> getAllContingent(){
       log.info("ContingentService.getAllContingent()");
       List<Contingent> contingents = contingentRepository.findAll();
       log.info("Returning all contingents: {}",contingents);
       return contingents;
    }

    public void addContingent(String name, Date startDate, Date endDate, Integer normalValue,
                              Date startDayOfWeek, Date endDayOfWeek, Integer weekendValue,
                              UUID carParkId, UUID facilityId) {
        log.info("ContingentService.addContingent()");
        Contingent contingent = Contingent.builder()
                .name(name)
                .startDate(startDate)
                .endDate(endDate)
                .normalValue(normalValue)
                .startDayOfWeek(startDayOfWeek)
                .endDayOfWeek(endDayOfWeek)
                .weekendValue(weekendValue)
                .carParkId(carParkId)
                .facilityId(facilityId)
                .build();
        contingentRepository.save(contingent);
        log.info("Contingent with the name: {} has been successfully saved",contingent.getName());
    }

    public boolean checkContingentExist(String name){
        log.info("ContingentService.checkContingentExist()");
        List<Contingent> contingents = contingentRepository.findByName(name);
        return contingents.size() > 0 ? false : true;
    }

    public void deleteContingent(UUID id) {
        log.info("ContingentService.deleteContingent()");
        contingentRepository.deleteById(id);
    }

    public void updateContingent(UUID id, String name,
                                 Date startDate, Date endDate, Integer normalValue,
                                 Date startDayOfWeek, Date endDayOfWeek, Integer weekendValue,
                                 UUID carParkId, UUID facilityId) {
        log.info("ContingentService.updateContingent()");
        Contingent contingent = contingentRepository.findById(id).get();
        contingent.setName(name);
        contingent.setStartDate(startDate);
        contingent.setEndDate(endDate);
        contingent.setNormalValue(normalValue);
        contingent.setStartDayOfWeek(startDayOfWeek);
        contingent.setEndDayOfWeek(endDayOfWeek);
        contingent.setWeekendValue(weekendValue);
        contingent.setCarParkId(carParkId);
        contingent.setFacilityId(facilityId);
        contingentRepository.save(contingent);
    }

    public Contingent getContingent(UUID id) {
        log.info("ContingentService.getContingent()");
        return contingentRepository.findById(id).get();
    }
}
