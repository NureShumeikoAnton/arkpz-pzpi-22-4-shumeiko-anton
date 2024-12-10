package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.ClimateSystem;

import nure.atrk.climate_control.entity.SystemUser;
import nure.atrk.climate_control.entity.User;
import nure.atrk.climate_control.repository.SystemRepository;
import nure.atrk.climate_control.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class SystemController {
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private SystemUserService systemUserService;

    @GetMapping
    public List<ClimateSystem> getAllSystems() {
        return systemRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<ClimateSystem> addSystem(@RequestBody ClimateSystem system) {
        systemRepository.save(system);
        return ResponseEntity.ok(system);
    }

    @PutMapping
    public ResponseEntity<ClimateSystem> updateSystem(@RequestBody ClimateSystem system) {
        systemRepository.save(system);
        return ResponseEntity.ok(system);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSystem(@PathVariable int id) {
        if (systemRepository.existsById(id)) {
            systemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClimateSystem> getSystem(@PathVariable int id) {
        ClimateSystem system = systemRepository.findById(id).orElse(null);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(system);
    }

    @PostMapping("/{id}/users/{userId}")
    public ResponseEntity<ClimateSystem> addUser(@PathVariable int id, @PathVariable int userId, @RequestParam String role) {
        ClimateSystem system = systemRepository.findById(id).orElse(null);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        systemUserService.addUser(id, userId, role);
        return ResponseEntity.ok(system);
    }

    @GetMapping("/{id}/users")
    public ResponseEntity<List<User>> getUsers(@PathVariable int id) {
        ClimateSystem system = systemRepository.findById(id).orElse(null);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        List<User> users = systemUserService.getUsersBySystemId(id);
        return ResponseEntity.ok(users);
    }
}
