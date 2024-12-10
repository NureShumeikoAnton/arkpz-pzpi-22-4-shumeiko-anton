package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
}
