package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Message;
import nure.atrk.climate_control.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        Message message = messageRepository.findById(id).orElse(null);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        messageRepository.save(message);
        return ResponseEntity.ok(message);
    }

    @PutMapping
    public ResponseEntity<Message> updateMessage(@RequestBody Message message) {
        messageRepository.save(message);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
        if(messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
