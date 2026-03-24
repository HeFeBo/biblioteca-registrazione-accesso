package com.hefebo.library.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hefebo.library.dto.request.LoginRequest;
import com.hefebo.library.dto.request.RegisterRequest;
import com.hefebo.library.dto.response.AuthResponse;
import com.hefebo.library.exception.EmailAlreadyExistsException;
import com.hefebo.library.model.Role;
import com.hefebo.library.model.User;
import com.hefebo.library.repository.UserRepository;
import com.hefebo.library.security.JwtService;
import com.hefebo.library.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("L'mail " + request.getEmail() + " é giá registrata");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
