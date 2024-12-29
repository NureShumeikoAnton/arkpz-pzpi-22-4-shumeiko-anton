package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Sensor;
import nure.atrk.climate_control.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    @Autowired
    private SensorRepository sensorRepository;

    @GetMapping
    @Operation(summary = "Get all sensors")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a sensor by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Sensor not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Sensor> getSensorById(@PathVariable int id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        if (sensor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sensor);
    }

    @PostMapping
    @Operation(summary = "Add a new sensor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sensor added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Sensor> addSensor(@RequestBody Sensor sensor) {
        sensorRepository.save(sensor);
        return ResponseEntity.ok(sensor);
    }

    @PutMapping
    @Operation(summary = "Update an existing sensor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sensor updated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Sensor> updateSensor(@RequestBody Sensor sensor) {
        sensorRepository.save(sensor);
        return ResponseEntity.ok(sensor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sensor by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Sensor deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Sensor not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteSensor(@PathVariable int id) {
        if(sensorRepository.existsById(id)) {
            sensorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}