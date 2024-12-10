package nure.atrk.climate_control.controller;


import nure.atrk.climate_control.entity.Command;
import nure.atrk.climate_control.repository.CommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commands")
public class CommandController {
    @Autowired
    private CommandRepository commandRepository;

    @GetMapping
    public List<Command> getAllCommands() {
        return commandRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable int id) {
        Command command = commandRepository.findById(id).orElse(null);
        if (command == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(command);
    }

    @PostMapping
    public ResponseEntity<Command> addCommand(@RequestBody Command command) {
        commandRepository.save(command);
        return ResponseEntity.ok(command);
    }

    @PutMapping
    public ResponseEntity<Command> updateCommand(@RequestBody Command command) {
        commandRepository.save(command);
        return ResponseEntity.ok(command);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommand(@PathVariable int id) {
        if(commandRepository.existsById(id)) {
            commandRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
