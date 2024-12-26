package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer>{
    List<Measurement> findAllBySensorId(int sensorId);
    List<Measurement> findAllByCreatedAtBetween(Date startDate, Date endDate);
}
