package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Measurement;
import nure.atrk.climate_control.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    @Autowired
    private MeasurementRepository measurementRepository;

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
        measurementRepository.save(measurement);
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
    @Operation(summary = "Get measurements by sensor ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Measurements not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<Measurement>> getMeasurementsBySensorId(@PathVariable int sensorId) {
        List<Measurement> measurements = measurementRepository.findAllBySensorId(sensorId);
        if (measurements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(measurements);
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get measurements by date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Measurements not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<Measurement>> getMeasurementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<Measurement> measurements = measurementRepository.findAllByCreatedAtBetween(startDate, endDate);
        if (measurements.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(measurements);
    }
}