package nure.atrk.climate_control.service;

import nure.atrk.climate_control.entity.Measurement;
import nure.atrk.climate_control.repository.MeasurementRepository;
import nure.atrk.climate_control.statistics.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private MeasurementRepository measurementRepository;

    public Map<String, Double> getSummaryStatistics(int systemId, Date startDate, Date endDate) {
        List<Measurement> measurements = measurementRepository.findAllBySystemIdAndDateBetween(systemId, startDate, endDate);
        if (measurements.isEmpty()) {
            return Collections.emptyMap();
        }

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Measurement measurement : measurements) {
            stats.addValue(measurement.getValue());
        }

        Map<String, Double> summaryStats = new HashMap<>();
        summaryStats.put("mean", stats.getMean());
        summaryStats.put("median", stats.getMedian());
        summaryStats.put("standard_deviation", stats.getStandardDeviation());
        summaryStats.put("min", stats.getMin());
        summaryStats.put("max", stats.getMax());

        return summaryStats;
    }

    private double calculateThreshold(List<Measurement> measurements) {
        DescriptiveStatistics stats = new DescriptiveStatistics();
        for (Measurement measurement : measurements) {
            stats.addValue(measurement.getValue());
        }

        return stats.getPercentile(95);
    }

    public Map<String, Object> getPeakLoads(int systemId, Date startDate, Date endDate) {
        List<Measurement> measurements = measurementRepository.findAllBySystemIdAndDateBetween(systemId, startDate, endDate);
        if(measurements.isEmpty()) {
            return Collections.emptyMap();
        }

        double threshold = calculateThreshold(measurements);

        List<Measurement> peakLoads = measurements.stream()
                .filter(m -> m.getValue() >= threshold)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("start_date", peakLoads.getFirst().getCreatedAt());
        result.put("end_date", peakLoads.getLast().getCreatedAt());
        result.put("threshold", threshold);
        result.put("peakLoads", peakLoads);

        return result;
    }

    // Method for trend analysis (simple linear regression)
    public Map<String, Double> getTrend(int systemId, Date startDate, Date endDate) {
        List<Measurement> measurements = measurementRepository.findAllBySystemIdAndDateBetween(systemId, startDate, endDate);

        if (measurements.isEmpty()) {
            throw new IllegalArgumentException("No measurements found for the given date range.");
        }

        // Adjust start and end dates to match the first and last measurements
        Timestamp adjustedStartDate = measurements.get(0).getCreatedAt();
        Timestamp adjustedEndDate = measurements.get(measurements.size() - 1).getCreatedAt();

        double[] times = new double[measurements.size()];
        double[] values = new double[measurements.size()];

        for (int i = 0; i < measurements.size(); i++) {
            times[i] = measurements.get(i).getCreatedAt().getTime();
            values[i] = measurements.get(i).getValue();
        }

        // Linear regression calculation (y = mx + b)
        double meanX = Arrays.stream(times).average().orElse(0);
        double meanY = Arrays.stream(values).average().orElse(0);

        double numerator = 0;
        double denominator = 0;

        for (int i = 0; i < times.length; i++) {
            numerator += (times[i] - meanX) * (values[i] - meanY);
            denominator += Math.pow(times[i] - meanX, 2);
        }

        double slope = numerator / denominator;
        double intercept = meanY - (slope * meanX);

        Map<String, Double> trend = new HashMap<>();
        trend.put("slope", slope);
        trend.put("intercept", intercept);
        trend.put("adjusted_start_date", (double) adjustedStartDate.getTime());
        trend.put("adjusted_end_date", (double) adjustedEndDate.getTime());

        return trend;
    }


    public Map<String, Object> forecast(int systemId, Date startDate, Date endDate, int daysAhead) {
        Map<String, Double> trend = getTrend(systemId, startDate, endDate);
        double slope = trend.get("slope");
        double intercept = trend.get("intercept");
        double adjustedStartDate = trend.get("adjusted_start_date");
        double adjustedEndDate = trend.get("adjusted_end_date");

        // Forecast future value
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date((long) adjustedEndDate));
        calendar.add(Calendar.DAY_OF_YEAR, daysAhead);

        double futureTime = calendar.getTimeInMillis();
        double forecastedValue = slope * futureTime + intercept;

        Map<String, Object> forecast = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String forecastedDate = dateFormat.format(new Date((long) futureTime));
        String adjustedStartDateStr = dateFormat.format(new Date((long) adjustedStartDate));
        String adjustedEndDateStr = dateFormat.format(new Date((long) adjustedEndDate));

        forecast.put("forecasted_value", forecastedValue);
        forecast.put("forecasted_date", forecastedDate);
        forecast.put("adjusted_start_date", adjustedStartDateStr);
        forecast.put("adjusted_end_date", adjustedEndDateStr);

        return forecast;
    }
}
