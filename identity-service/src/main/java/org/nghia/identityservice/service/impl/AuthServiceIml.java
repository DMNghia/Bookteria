package org.nghia.identityservice.service.impl;

import org.nghia.identityservice.constant.ResponseCode;
import org.nghia.identityservice.dto.request.LoginRequest;
import org.nghia.identityservice.dto.request.RegisterRequest;
import org.nghia.identityservice.dto.response.BaseResponse;
import org.nghia.identityservice.dto.response.LoginResponse;
import org.nghia.identityservice.entity.User;
import org.nghia.identityservice.exception.BaseException;
import org.nghia.identityservice.repository.UserRepository;
import org.nghia.identityservice.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceIml implements AuthService {

    @Value("${application.security.password-salt}")
    private String passwordSalt;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceIml(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseResponse<LoginResponse> login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
        User user = userOptional.orElseThrow(() -> new BaseException(ResponseCode.FAIL.getCode(), "Username or password incorrect"));
        if (!passwordEncoder.matches(loginRequest.getPassword() + passwordSalt, user.getPassword())) {
            throw new BaseException(ResponseCode.FAIL.getCode(), "Username or password incorrect");
        }
        if (user.isLocked()) {
            throw new BaseException(ResponseCode.FAIL.getCode(), "User is locked");
        }
        if (!user.isEnabled()) {
            throw new BaseException(ResponseCode.FAIL.getCode(), "User is not enabled");
        }
        return BaseResponse.<LoginResponse>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message("Login successful")
                .data(LoginResponse.builder()

                        .build())
                .build();
    }

    @Override
    public BaseResponse<?> register(RegisterRequest registerRequest) {
        Optional<User> userOptional = userRepository.findByUsername(registerRequest.getUsername());
        if (userOptional.isPresent()) {
            throw new BaseException(ResponseCode.FAIL.getCode(), "Username already exist");
        }
        String hashPassword = passwordEncoder.encode(registerRequest.getPassword() + passwordSalt);
        userRepository.save(User.builder()
                .username(registerRequest.getUsername())
                .password(hashPassword)
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .birthDate(registerRequest.getBirthDate())
                .build());
        return BaseResponse.builder()
                .code(ResponseCode.SUCCESS.getCode())
                .message("Register successfully!")
                .build();
    }
}
