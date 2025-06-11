package ru.bmstu.exception;

import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("Student not found");
    }

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}