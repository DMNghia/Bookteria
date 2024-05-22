package org.nghia.identityservice.service;

import org.nghia.identityservice.dto.request.RegisterRequest;
import org.nghia.identityservice.dto.response.BaseResponse;
import org.nghia.identityservice.dto.response.LoginResponse;
import org.nghia.identityservice.dto.request.LoginRequest;

public interface AuthService {

    BaseResponse<LoginResponse> login(LoginRequest loginRequest);
    BaseResponse<?> register(RegisterRequest registerRequest);
}
