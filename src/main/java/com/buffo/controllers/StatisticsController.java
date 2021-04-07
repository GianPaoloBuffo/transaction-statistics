package com.buffo.controllers;

import com.buffo.domain.Statistics;
import com.buffo.services.StatisticsService;
import com.buffo.services.impl.StatisticsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsServiceImpl statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * Calculates and returns statistical data for all transactions from the last 60 seconds
     *
     * @return HTTP Status Code 200 with a response body of {@link Statistics}
     */
    @GetMapping
    public ResponseEntity<Statistics> getStatistics() {
        Statistics statistics = statisticsService.getStatistics();
        return ResponseEntity.ok(statistics);
    }
}
