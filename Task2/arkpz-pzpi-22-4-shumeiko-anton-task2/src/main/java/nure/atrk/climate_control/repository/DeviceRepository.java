package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Integer>{
}
