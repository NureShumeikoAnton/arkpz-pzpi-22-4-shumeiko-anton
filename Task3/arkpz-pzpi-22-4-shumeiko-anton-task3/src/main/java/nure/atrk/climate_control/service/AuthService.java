package nure.atrk.climate_control.service;

import nure.atrk.climate_control.entity.User;
import nure.atrk.climate_control.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import nure.atrk.climate_control.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public boolean register(User user){
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public String login(String email, String password){
        Optional<User> existingUser = userRepository.findByEmail(email);
        if(existingUser.isPresent()){
            User user = existingUser.get();
            if(bCryptPasswordEncoder.matches(password, user.getPassword())){
                return jwtUtil.generateToken(email);
            }
        }
        return null;
    }
}
