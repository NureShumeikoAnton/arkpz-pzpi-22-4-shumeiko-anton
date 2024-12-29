package nure.atrk.climate_control.service;

import nure.atrk.climate_control.entity.ClimateSystem;
import nure.atrk.climate_control.entity.SystemUser;
import nure.atrk.climate_control.entity.SystemUserId;
import nure.atrk.climate_control.entity.User;
import nure.atrk.climate_control.repository.SystemRepository;
import nure.atrk.climate_control.repository.SystemUserRepository;
import nure.atrk.climate_control.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemUserService {
    @Autowired
    private SystemUserRepository systemUserRepository;
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsersBySystemId(int systemId) {
        List<SystemUser> systemUsers = systemUserRepository.findAllById_SystemId(systemId);
        return systemUsers.stream()
                .map(SystemUser::getUser)
                .collect(Collectors.toList());
    }

    public void addUser(int systemId, int userId, String role) {
        ClimateSystem system = systemRepository.findById(systemId).orElse(null);
        if (system == null) {
            return;
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        System.out.println("role = " + role);
        SystemUser systemUser = new SystemUser();
        SystemUserId systemUserId = new SystemUserId();
        systemUserId.setSystemId(systemId);
        systemUserId.setUserId(userId);
        systemUser.setId(systemUserId);
        systemUser.setRole(role);
        systemUser.setSystem(system);
        systemUser.setUser(user);
        systemUserRepository.save(systemUser);
    }
}
