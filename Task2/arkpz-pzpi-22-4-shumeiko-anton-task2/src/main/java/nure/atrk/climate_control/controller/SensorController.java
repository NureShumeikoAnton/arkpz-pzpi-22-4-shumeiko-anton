package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Sensor;
import nure.atrk.climate_control.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    @Autowired
    private SensorRepository sensorRepository;

    @GetMapping
    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable int id) {
        Sensor sensor = sensorRepository.findById(id).orElse(null);
        if (sensor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(sensor);
    }

    @PostMapping
    public ResponseEntity<Sensor> addSensor(@RequestBody Sensor sensor) {
        sensorRepository.save(sensor);
        return ResponseEntity.ok(sensor);
    }

    @PutMapping
    public ResponseEntity<Sensor> updateSensor(@RequestBody Sensor sensor) {
        sensorRepository.save(sensor);
        return ResponseEntity.ok(sensor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable int id) {
        if(sensorRepository.existsById(id)) {
            sensorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
