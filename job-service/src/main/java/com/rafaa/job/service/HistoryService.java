package com.rafaa.job.service;

import com.rafaa.carpark.entity.CarPark;
import com.rafaa.job.dto.History;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;
    private static final Logger log = LoggerFactory.getLogger(HistoryService.class);

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public List<History> getAllHistories(){
        log.info("Request received to get all histories");
        final List<History> histories = historyRepository.findAll();
        log.info("Returning histories : {}", histories);
        return histories;
    }

    public List<History> getHistoryByJobName(String jobName){
        log.info("Request received to get history by jobName");
        final List<History> histories = historyRepository.getHistoryByName(jobName);
        log.info("Returning history : {}", histories);
        return histories;
    }

    public void addHistory(){

    }

}
