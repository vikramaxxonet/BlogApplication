package com.example.BlogApplication.controller;

import com.example.BlogApplication.dto.UserRegistrationDTO;
import com.example.BlogApplication.entity.AppUser;
import com.example.BlogApplication.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {

        if (userRepository.existsByUsername(userRegistrationDTO.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }


        String encodedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());


        AppUser newUser = new AppUser();
        newUser.setUsername(userRegistrationDTO.getUsername());
        newUser.setPassword(encodedPassword);
        newUser.setRole(userRegistrationDTO.getRole() != null ? userRegistrationDTO.getRole() : "USER"); // Default role
        userRepository.save(newUser);

        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRegistrationDTO userLoginDTO) {

        AppUser user = userRepository.findByUsername(userLoginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}

