package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandRepository extends JpaRepository<Command, Integer>{
    List<Command> findAllByDeviceId(int deviceId);
}
