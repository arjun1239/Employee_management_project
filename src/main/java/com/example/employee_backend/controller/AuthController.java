/*
package com.example.employee_backend.controller;

import com.example.employee_backend.model.User;
import com.example.employee_backend.security.JwtUtil;
import com.example.employee_backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService service;

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        var userDetails = service.loadUserByUsername(user.getUsername());

        if (!user.getPassword().equals(userDetails.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}*/


package com.example.employee_backend.controller;

import com.example.employee_backend.model.User;
import com.example.employee_backend.security.JwtUtil;
import com.example.employee_backend.service.CustomUserDetailsService;
import com.example.employee_backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// ✅ ADD THESE IMPORTS
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService service;

    // ✅ ADD THIS
    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ ADD THIS (for register)
    @Autowired
    private UserRepository userRepository;

    // =========================
    // ✅ REGISTER API
    // =========================
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        // 🔐 Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }

    // =========================
    // ✅ LOGIN API (FIXED)
    // =========================

//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//
//        UserDetails userDetails = service.loadUserByUsername(user.getUsername());
//
//        // 🔥 IMPORTANT FIX: use matches()
//        if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        return jwtUtil.generateToken(user.getUsername());
//    }


    @PostMapping("/login")
    public String login(@RequestBody User user) {

        System.out.println("Login API called");
        System.out.println("Username: " + user.getUsername());

        UserDetails userDetails = service.loadUserByUsername(user.getUsername());

        System.out.println("User found in DB");

        if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            System.out.println("Password mismatch");
            throw new RuntimeException("Invalid credentials");
        }

        System.out.println("Password matched");


        System.out.println(jwtUtil.generateToken(user.getUsername()));
        return jwtUtil.generateToken(user.getUsername());
    }


}
