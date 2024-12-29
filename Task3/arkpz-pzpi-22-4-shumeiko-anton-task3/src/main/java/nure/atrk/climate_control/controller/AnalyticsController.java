package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/summary")
    public Map<String, Double> getSummaryStatistics(
            @RequestParam int systemId,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return analyticsService.getSummaryStatistics(systemId, startDate, endDate);
    }

    @GetMapping("/peak-loads")
    public Map<String, Object> getPeakLoads(
            @RequestParam int systemId,
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        return analyticsService.getPeakLoads(systemId, startDate, endDate);
    }

    @GetMapping("/forecast")
    public Map<String, Object> forecast(
            @RequestParam int systemId,
            @RequestParam Date startDate,
            @RequestParam Date endDate,
            @RequestParam int daysAhead) {
        return analyticsService.forecast(systemId, startDate, endDate, daysAhead);
    }
}
