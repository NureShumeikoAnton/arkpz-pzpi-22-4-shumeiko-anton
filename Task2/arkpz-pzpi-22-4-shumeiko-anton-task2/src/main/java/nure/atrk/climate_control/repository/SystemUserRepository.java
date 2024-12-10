package nure.atrk.climate_control.repository;

import nure.atrk.climate_control.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer>{
    List<SystemUser> findAllById_SystemId(int systemId);
}
