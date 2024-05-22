package org.nghia.identityservice.controller;

import jakarta.validation.Valid;
import org.nghia.identityservice.constant.ResponseCode;
import org.nghia.identityservice.dto.request.LoginRequest;
import org.nghia.identityservice.dto.request.RegisterRequest;
import org.nghia.identityservice.dto.response.BaseResponse;
import org.nghia.identityservice.exception.BaseException;
import org.nghia.identityservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<?>> login(@RequestBody LoginRequest loginRequest) {
        try {

            return ResponseEntity.ok(BaseResponse.builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .message("Login successful")
                    .build());
        } catch (BaseException be) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .code(be.getCode())
                            .message(be.getMessage())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    BaseResponse.builder()
                            .code(ResponseCode.ERROR.getCode())
                            .message("Error while processing")
                            .build()
            );
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            BaseResponse<?> response = authService.register(request);
            return ResponseEntity.ok(response);
        } catch (BaseException be) {
            return ResponseEntity.badRequest().body(
                    BaseResponse.builder()
                            .code(be.getCode())
                            .message(be.getMessage())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    BaseResponse.builder()
                            .code(ResponseCode.ERROR.getCode())
                            .message("Error while processing")
                            .build()
            );
        }
    }

}
