package com.jbryan98.authservice.service;

import com.jbryan98.authservice.model.User;
import com.jbryan98.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public Optional<User> findByEmail(String email) {
        log.info("findByEmail: {}", email);
        return repository.findByEmail(email);
    }
}
