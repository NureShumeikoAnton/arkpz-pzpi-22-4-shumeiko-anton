package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/admin/backup")
public class BackupController {

    @Autowired
    private BackupService backupService;

    @GetMapping("/create")
    public String createBackup() {
        try {
            return backupService.createBackup();
        } catch (IOException e) {
            return "Error creating backup: " + e.getMessage();
        }
    }

    @GetMapping("/restore")
    public String restoreBackup(@RequestParam("fileName") String fileName) {
        try {
            return backupService.restoreBackup(fileName);
        } catch (IOException e) {
            return "Error restoring backup: " + e.getMessage();
        }
    }
}
