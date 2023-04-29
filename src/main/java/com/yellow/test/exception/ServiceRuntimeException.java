package com.yellow.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ServiceRuntimeException extends RuntimeException {

    @Getter
    private int status;

    public ServiceRuntimeException(String message) {
        super(message);
    }

    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceRuntimeException(String message, int status) {
        super(message);
        this.status = status;
    }

    public ServiceRuntimeException(String s, Throwable cause, int status) {
        super(s, cause);
        this.status = status;
    }
}
