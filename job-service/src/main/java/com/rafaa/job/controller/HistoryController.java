package com.rafaa.job.controller;

import com.rafaa.job.dto.History;
import com.rafaa.job.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    private static final Logger log = LoggerFactory.getLogger(HistoryController.class);
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public List<History> getAllHistories(){
        log.info("HistoryController.getAllHistories()");
        return historyService.getAllHistories();
    }

    @GetMapping("/{jobName}")
    public History getHistoryByJobName(@PathVariable(name = "jobName") String jobName){
        log.info("HistoryController.getHistoryByJobName()");
        return historyService.getHistoryByJobName(jobName);
    }

}
