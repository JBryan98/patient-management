package com.jbryan98.authservice.service;

import com.jbryan98.authservice.dto.LoginRequestDto;
import com.jbryan98.authservice.dto.LoginResponseDto;
import com.jbryan98.authservice.exception.InvalidCredentialsException;
import com.jbryan98.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponseDto authenticate(LoginRequestDto request) {
        log.info("authenticate");
        var token = userService.findByEmail(request.email())
                .filter(u -> passwordEncoder.matches(request.password(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        if (token.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        } else {
            return new LoginResponseDto(token.get());
        }
    }

    public boolean validateToken(String token) {
        log.info("validateToken");
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
