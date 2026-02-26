package com.Hcl.Authentication.controller;

import com.Hcl.Authentication.dto.*;
import com.Hcl.Authentication.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/auth/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/auth/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/api/validate-token")
    public TokenValidationResponse validateToken(@RequestParam String token) {
        return authService.validateToken(token);
    }

    @GetMapping("/api/user-details")
    public UserDetailsResponse getUserDetails(@RequestHeader("Authorization") String authHeader) {
        return authService.getUserDetails(authHeader);
    }
}
