package org.nghia.identityservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.nghia.identityservice.constant.ResponseCode;
import org.nghia.identityservice.dto.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class HandleGlobalException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception e) {
        log.error("EXCEPTION: ", e);
        return ResponseEntity.internalServerError().body(
                BaseResponse.builder()
                        .code(ResponseCode.ERROR.getCode())
                        .message("Error").build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("METHOD_ARGUMENT_NOT_VALID: ", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(
                BaseResponse.builder()
                        .code(ResponseCode.FAIL.getCode())
                        .message("Fail")
                        .data(errors)
                        .build()
        );
    }
}
