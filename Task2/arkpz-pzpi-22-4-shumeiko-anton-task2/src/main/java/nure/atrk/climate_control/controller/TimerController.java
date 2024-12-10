package nure.atrk.climate_control.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import nure.atrk.climate_control.entity.Timer;
import nure.atrk.climate_control.repository.TimerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;

@RestController
@RequestMapping("/timers")
public class TimerController {
    @Autowired
    private TimerRepository timerRepository;

    @GetMapping
    public List<Timer> getAllTimers() {
        return timerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Timer> getTimerById(@PathVariable int id) {
        Timer timer = timerRepository.findById(id).orElse(null);
        if (timer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(timer);
    }

    @PostMapping
    public ResponseEntity<Timer> addTimer(@RequestBody Timer timer) {
        timerRepository.save(timer);
        return ResponseEntity.ok(timer);
    }

    @PutMapping
    public ResponseEntity<Timer> updateTimer(@RequestBody Timer timer) {
        timerRepository.save(timer);
        return ResponseEntity.ok(timer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTimer(@PathVariable int id) {
        if(timerRepository.existsById(id)) {
            timerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
