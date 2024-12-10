package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Measurement;
import nure.atrk.climate_control.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping
    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Measurement> getMeasurementById(@PathVariable int id) {
        Measurement measurement = measurementRepository.findById(id).orElse(null);
        if (measurement == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(measurement);
    }

    @PostMapping
    public ResponseEntity<Measurement> addMeasurement(@RequestBody Measurement measurement) {
        measurementRepository.save(measurement);
        return ResponseEntity.ok(measurement);
    }

    @PutMapping
    public ResponseEntity<Measurement> updateMeasurement(@RequestBody Measurement measurement) {
        measurementRepository.save(measurement);
        return ResponseEntity.ok(measurement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable int id) {
        if(measurementRepository.existsById(id)) {
            measurementRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
