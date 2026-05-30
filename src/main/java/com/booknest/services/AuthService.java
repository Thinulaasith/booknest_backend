package com.booknest.services;

import com.booknest.entities.Admin;
import com.booknest.model.LoginRequest;
import com.booknest.model.LoginResponse;
import com.booknest.repositories.AdminRepository;
import com.booknest.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {

        Admin admin = adminRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                admin.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        String token = jwtUtil.generateToken(admin.getUsername());

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .username(admin.getUsername())
                .build();
    }
}