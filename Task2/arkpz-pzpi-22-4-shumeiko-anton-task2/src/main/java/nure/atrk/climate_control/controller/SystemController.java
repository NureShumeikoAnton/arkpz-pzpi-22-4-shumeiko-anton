package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.ClimateSystem;
import nure.atrk.climate_control.entity.User;
import nure.atrk.climate_control.repository.SystemRepository;
import nure.atrk.climate_control.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/systems")
public class SystemController {
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private SystemUserService systemUserService;

    @GetMapping
    @Operation(summary = "Get all systems")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<ClimateSystem> getAllSystems() {
        return systemRepository.findAll();
    }

    @PostMapping
    @Operation(summary = "Add a new system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "System added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ClimateSystem> addSystem(@RequestBody ClimateSystem system) {
        systemRepository.save(system);
        return ResponseEntity.ok(system);
    }

    @PutMapping
    @Operation(summary = "Update an existing system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "System updated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ClimateSystem> updateSystem(@RequestBody ClimateSystem system) {
        ClimateSystem existingSystem = systemRepository.findById(system.getSystemId()).orElse(null);
        if (existingSystem == null) {
            return ResponseEntity.notFound().build();
        }
        system.setCreatedAt(existingSystem.getCreatedAt());
        systemRepository.save(system);
        return ResponseEntity.ok(system);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a system by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "System deleted successfully"),
        @ApiResponse(responseCode = "404", description = "System not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteSystem(@PathVariable int id) {
        if (systemRepository.existsById(id)) {
            systemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a system by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "System not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ClimateSystem> getSystem(@PathVariable int id) {
        ClimateSystem system = systemRepository.findById(id).orElse(null);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(system);
    }

    @PostMapping("/{id}/users/{userId}")
    @Operation(summary = "Add a user to a system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User added to system successfully"),
        @ApiResponse(responseCode = "404", description = "System not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<ClimateSystem> addUser(@PathVariable int id, @PathVariable int userId, @RequestParam String role) {
        ClimateSystem system = systemRepository.findById(id).orElse(null);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        systemUserService.addUser(id, userId, role);
        return ResponseEntity.ok(system);
    }

    @GetMapping("/{id}/users")
    @Operation(summary = "Get users of a system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "System not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<User>> getUsers(@PathVariable int id) {
        ClimateSystem system = systemRepository.findById(id).orElse(null);
        if (system == null) {
            return ResponseEntity.notFound().build();
        }
        List<User> users = systemUserService.getUsersBySystemId(id);
        return ResponseEntity.ok(users);
    }
}