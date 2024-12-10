package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Command;
import nure.atrk.climate_control.entity.Device;
import nure.atrk.climate_control.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable int id) {
        Device device = deviceRepository.findById(id).orElse(null);
        if (device == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(device);
    }

    @PostMapping
    public ResponseEntity<Device> addDevice(@RequestBody Device device) {
        deviceRepository.save(device);
        return ResponseEntity.ok(device);
    }

    @PutMapping
    public ResponseEntity<Device> updateDevice(@RequestBody Device device) {
        deviceRepository.save(device);
        return ResponseEntity.ok(device);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable int id) {
        if(deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
