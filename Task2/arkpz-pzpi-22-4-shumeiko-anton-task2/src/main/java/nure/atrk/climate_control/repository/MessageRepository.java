package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer>{
}
