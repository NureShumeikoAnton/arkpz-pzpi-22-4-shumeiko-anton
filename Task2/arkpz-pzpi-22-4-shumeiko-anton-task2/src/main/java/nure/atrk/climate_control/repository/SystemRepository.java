package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.ClimateSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRepository extends JpaRepository<ClimateSystem, Integer> {

}
