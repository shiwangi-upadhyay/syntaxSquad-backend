package com.Hcl.Authentication.service;

import com.Hcl.Authentication.dto.*;
import com.Hcl.Authentication.entity.Role;
import com.Hcl.Authentication.entity.RoleName;
import com.Hcl.Authentication.entity.User;
import com.Hcl.Authentication.repository.RoleRepository;
import com.Hcl.Authentication.repository.UserRepository;
import com.Hcl.Authentication.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String register(@Valid RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String roleInput = request.getRole().toUpperCase();
        if (!roleInput.equals("USER") && !roleInput.equals("SELLER")) {
            throw new RuntimeException("Invalid role. Only USER and SELLER roles are allowed for registration.");
        }

        Role role = roleRepository
                .findByName(RoleName.valueOf("ROLE_" + roleInput))
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);
        user.setRoles(Set.of(role));

        userRepository.save(user);

        return "User Registered Successfully";
    }

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).get();

        String token = jwtService.generateToken(user);

        List<String> roles = user.getRoles().stream()
                .map(r -> r.getName().name())
                .collect(Collectors.toList());

        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), roles);
    }

    public TokenValidationResponse validateToken(String token) {
        var claims = jwtService.extractClaimsIfValid(token);
        if (claims == null) {
            return new TokenValidationResponse(false, "Invalid or expired token");
        }

        String email = claims.getSubject();
        @SuppressWarnings("unchecked")
        List<String> roles = claims.get("roles", List.class);

        return new TokenValidationResponse(true, email, roles, "Token is valid");
    }

    public UserDetailsResponse getUserDetails(String authHeader) {
        var claims = jwtService.extractClaimsIfValid(authHeader);
        if (claims == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        String email = claims.getSubject();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.toDetailsResponse();
    }
}
