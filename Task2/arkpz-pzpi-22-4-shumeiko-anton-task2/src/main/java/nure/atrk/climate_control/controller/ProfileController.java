package nure.atrk.climate_control.controller;

import nure.atrk.climate_control.entity.Profile;
import nure.atrk.climate_control.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable int id) {
        Profile profile = profileRepository.findById(id).orElse(null);
        if (profile == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profile);
    }

    @PostMapping
    public ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        profileRepository.save(profile);
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
        profileRepository.save(profile);
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable int id) {
        if(profileRepository.existsById(id)) {
            profileRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
