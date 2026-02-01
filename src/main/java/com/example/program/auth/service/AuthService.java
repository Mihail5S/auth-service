package com.example.program.auth.service;

import com.example.program.auth.dto.AuthResponse;
import com.example.program.auth.dto.LoginRequest;
import com.example.program.auth.dto.RegisterRequest;
import com.example.program.domain.entity.Role;
import com.example.program.domain.entity.User;
import com.example.program.domain.repository.UserRepository;
import com.example.program.exception.InvalidCredentialsException;
import com.example.program.exception.UserAlreadyExistsException;
import com.example.program.security.jwt.JwtService;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setRoles(Set.of(Role.USER));

        User saved = userRepository.save(user);
        String token = jwtService.generateAccessToken(saved.getId(), saved.getEmail(), saved.getRoles());

        return new AuthResponse(saved.getId(), saved.getEmail(), token);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateAccessToken(user.getId(), user.getEmail(), user.getRoles());
        return new AuthResponse(user.getId(), user.getEmail(), token);
    }
}
