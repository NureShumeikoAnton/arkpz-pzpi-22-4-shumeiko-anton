package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer>{
}
