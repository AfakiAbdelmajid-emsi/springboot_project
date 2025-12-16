package com.vente.web;

import com.vente.entities.User;
import com.vente.repositories.UserRepository;
import com.vente.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String pass = body.get("pass");

        User u = userRepo.findByLogin(login).orElse(null);

        if (u == null || !passwordEncoder.matches(pass, u.getPass())) {
            return Map.of("error", "Invalid credentials");
        }

        String token = jwtUtil.generateToken(login);

        return Map.of("token", token);
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Map<String, String> body) {
        String login = body.get("login");
        String pass = body.get("pass");

        if (userRepo.findByLogin(login).isPresent()) {
            return Map.of("error", "User already exists");
        }

        String encoded = passwordEncoder.encode(pass);

        User newUser = new User(login, encoded);
        userRepo.save(newUser);

        return Map.of("message", "User registered successfully");
    }
}
