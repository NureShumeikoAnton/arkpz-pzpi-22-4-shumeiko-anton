package nure.atrk.climate_control.service;

import nure.atrk.climate_control.entity.*;
import nure.atrk.climate_control.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;


@Service
public class MeasurementService {
    @Autowired
    private CommandService commandService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private ProfileRepository profileRepository;

    public void processMeasurement(Measurement measurement) {
        System.out.println("Processing measurement: " + measurement);
        Sensor sensor = sensorRepository.findById(measurement.getSensorId()).orElse(null);
        if (sensor == null) {
            return;
        }
        ClimateSystem system = systemRepository.findById(sensor.getSystemId()).orElse(null);
        if (system == null) {
            return;
        }
        if(system.getProfileId() == null) {
            return;
        }
        Profile activeProfile = profileRepository.findById(system.getProfileId()).orElse(null);
        if (activeProfile == null) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        Date currentDate = Date.valueOf(now.toLocalDate());
        Time currentTime = Time.valueOf(now.toLocalTime());

        Schedule activeSchedule = scheduleRepository.findActiveSchedule(
                activeProfile.getProfileId(),
                currentDate,
                currentTime
        );
        if (activeSchedule == null) {
            return;
        }
        System.out.println("Fetched");

        String type = sensor.getType();
        double targetValue = -1;
        double currentValue = measurement.getValue();
        System.out.println("Current value: " + currentValue);

        switch (type) {
            case "temperature":
                targetValue = activeSchedule.getTemperature();
                break;
            case "humidity":
                targetValue = activeSchedule.getHumidity();
                break;
            default:
                break;
        }

        if (targetValue == -1) {
            return;
        }
        double difference = targetValue - currentValue;
        System.out.println("Difference: " + difference);

        commandService.adjustDevice(system, type, difference);
    }
}
