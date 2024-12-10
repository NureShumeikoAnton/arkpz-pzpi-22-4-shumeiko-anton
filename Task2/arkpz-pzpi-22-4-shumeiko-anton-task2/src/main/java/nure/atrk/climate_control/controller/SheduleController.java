package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Shedule;
import nure.atrk.climate_control.repository.SheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shedules")
public class SheduleController {
    @Autowired
    private SheduleRepository sheduleRepository;

    @GetMapping
    public List<Shedule> getAllShedules() {
        return sheduleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shedule> getSheduleById(@PathVariable int id) {
        Shedule shedule = sheduleRepository.findById(id).orElse(null);
        if (shedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(shedule);
    }

    @PostMapping
    public ResponseEntity<Shedule> addShedule(@RequestBody Shedule shedule) {
        sheduleRepository.save(shedule);
        return ResponseEntity.ok(shedule);
    }

    @PutMapping
    public ResponseEntity<Shedule> updateShedule(@RequestBody Shedule shedule) {
        sheduleRepository.save(shedule);
        return ResponseEntity.ok(shedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShedule(@PathVariable int id) {
        if(sheduleRepository.existsById(id)) {
            sheduleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
