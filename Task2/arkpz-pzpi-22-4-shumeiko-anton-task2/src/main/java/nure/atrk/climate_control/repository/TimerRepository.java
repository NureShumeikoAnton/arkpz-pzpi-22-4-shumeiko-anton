package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, Integer>{
}
