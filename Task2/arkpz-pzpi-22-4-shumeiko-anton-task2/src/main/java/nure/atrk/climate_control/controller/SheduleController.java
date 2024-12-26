package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Schedule;
import nure.atrk.climate_control.repository.SheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/shedules")
public class SheduleController {
    @Autowired
    private SheduleRepository sheduleRepository;

    @GetMapping
    @Operation(summary = "Get all shedules")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<Schedule> getAllShedules() {
        return sheduleRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a shedule by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Shedule not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Schedule> getSheduleById(@PathVariable int id) {
        Schedule schedule = sheduleRepository.findById(id).orElse(null);
        if (schedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    @Operation(summary = "Add a new shedule")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shedule added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Schedule> addShedule(@RequestBody Schedule schedule) {
        sheduleRepository.save(schedule);
        return ResponseEntity.ok(schedule);
    }

    @PutMapping
    @Operation(summary = "Update an existing shedule")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Shedule updated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Schedule> updateShedule(@RequestBody Schedule schedule) {
        sheduleRepository.save(schedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a shedule by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Shedule deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Shedule not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteShedule(@PathVariable int id) {
        if(sheduleRepository.existsById(id)) {
            sheduleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/profile/{profileId}")
    @Operation(summary = "Get schedules by profile ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Schedules not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<Schedule>> getSchedulesByProfileId(@PathVariable int profileId) {
        List<Schedule> schedules = sheduleRepository.findAllByProfileId(profileId);
        if (schedules.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schedules);
    }
}