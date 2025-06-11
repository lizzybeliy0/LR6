package ru.bmstu.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied");
    }

    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}