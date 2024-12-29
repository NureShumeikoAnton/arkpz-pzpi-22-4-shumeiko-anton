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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/timers")
public class TimerController {
    @Autowired
    private TimerRepository timerRepository;

    @GetMapping
    @Operation(summary = "Get all timers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<Timer> getAllTimers() {
        return timerRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a timer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Timer not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Timer> getTimerById(@PathVariable int id) {
        Timer timer = timerRepository.findById(id).orElse(null);
        if (timer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(timer);
    }

    @PostMapping
    @Operation(summary = "Add a new timer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Timer added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Timer> addTimer(@RequestBody Timer timer) {
        timerRepository.save(timer);
        return ResponseEntity.ok(timer);
    }

    @PutMapping
    @Operation(summary = "Update an existing timer")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Timer updated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Timer> updateTimer(@RequestBody Timer timer) {
        timerRepository.save(timer);
        return ResponseEntity.ok(timer);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a timer by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Timer deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Timer not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteTimer(@PathVariable int id) {
        if(timerRepository.existsById(id)) {
            timerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}