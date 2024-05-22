package org.nghia.identityservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {
    SUCCESS(0),
    ERROR(-1),
    FAIL(1);
    private final int code;
}
