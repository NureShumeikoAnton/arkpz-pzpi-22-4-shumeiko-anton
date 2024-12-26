package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SheduleRepository extends JpaRepository<Schedule, Integer>{
    List<Schedule> findAllByProfileId(int deviceId);
}
