package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{
}
