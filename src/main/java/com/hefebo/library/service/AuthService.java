package com.hefebo.library.service;

import com.hefebo.library.dto.request.LoginRequest;
import com.hefebo.library.dto.request.RegisterRequest;
import com.hefebo.library.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}