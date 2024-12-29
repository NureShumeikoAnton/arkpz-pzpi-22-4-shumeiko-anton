package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SensorRepository extends JpaRepository<Sensor, Integer>{
    List<Sensor> findAllBySystemId(int systemId);
}
