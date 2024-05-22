package org.nghia.identityservice.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private int code;
    private String message;

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
