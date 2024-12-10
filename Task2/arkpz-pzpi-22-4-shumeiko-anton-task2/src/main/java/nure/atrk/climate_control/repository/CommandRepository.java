package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandRepository extends JpaRepository<Command, Integer>{
}
