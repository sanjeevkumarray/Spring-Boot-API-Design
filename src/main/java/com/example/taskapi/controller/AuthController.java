package com.example.taskapi.controller;

import com.example.taskapi.entity.User;
import com.example.taskapi.service.UserService;
import com.example.taskapi.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        User user = userService.registerUser(body.get("email"), body.get("password"), body.get("name"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.get("email"), body.get("password"))
            );
            String token = jwtUtil.generateToken(body.get("email"));
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid email or password"));
        }
    }
}
