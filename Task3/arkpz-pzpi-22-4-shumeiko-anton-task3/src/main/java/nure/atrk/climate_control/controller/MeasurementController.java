package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Measurement;
import nure.atrk.climate_control.repository.MeasurementRepository;
import nure.atrk.climate_control.service.MeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private MeasurementService measurementService;

    @GetMapping
    @Operation(summary = "Get all measurements")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a measurement by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Measurement not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Measurement> getMeasurementById(@PathVariable int id) {
        Measurement measurement = measurementRepository.findById(id).orElse(null);
        if (measurement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(measurement);
    }

    @PostMapping
    @Operation(summary = "Add a new measurement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Measurement added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Measurement> addMeasurement(@RequestBody Measurement measurement) {
        Measurement newMeasurement = measurementRepository.save(measurement);
        System.out.println("New measurement: " + newMeasurement);
        measurementService.processMeasurement(newMeasurement);
        return ResponseEntity.ok(measurement);
    }

    @PutMapping
    @Operation(summary = "Update an existing measurement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Measurement updated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Measurement> updateMeasurement(@RequestBody Measurement measurement) {
        measurementRepository.save(measurement);
        return ResponseEntity.ok(measurement);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a measurement by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Measurement deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Measurement not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteMeasurement(@PathVariable int id) {
        if(measurementRepository.existsById(id)) {
            measurementRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/sensor/{sensorId}")
    @Operation(summary = "Get all measurements for a system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<Measurement>> getMeasurementsBySystemId(@PathVariable int sensorId) {
        return ResponseEntity.ok(measurementRepository.findAllBySensorId(sensorId));
    }
}