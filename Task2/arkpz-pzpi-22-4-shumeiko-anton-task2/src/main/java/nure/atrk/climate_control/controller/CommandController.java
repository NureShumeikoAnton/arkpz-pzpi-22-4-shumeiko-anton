package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Command;
import nure.atrk.climate_control.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/commands")
public class CommandController {
    @Autowired
    private CommandRepository commandRepository;

    @GetMapping
    @Operation(summary = "Get all commands")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a command by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Command not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Command> getCommandById(@PathVariable int id) {
        Command command = commandRepository.findById(id).orElse(null);
        if (command == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(command);
    }

    @PostMapping
    @Operation(summary = "Add a new command")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Command added successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Command> addCommand(@RequestBody Command command) {
        commandRepository.save(command);
        return ResponseEntity.ok(command);
    }

    @PutMapping
    @Operation(summary = "Update an existing command")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Command updated successfully"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Command> updateCommand(@RequestBody Command command) {
        commandRepository.save(command);
        return ResponseEntity.ok(command);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a command by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Command deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Command not found"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteCommand(@PathVariable int id) {
        if(commandRepository.existsById(id)) {
            commandRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/device/{deviceId}")
    @Operation(summary = "Get commands by device ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Commands not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<Command>> getCommandsByDeviceId(@PathVariable int deviceId) {
        List<Command> commands = commandRepository.findAllByDeviceId(deviceId);
        if (commands.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commands);
    }
}