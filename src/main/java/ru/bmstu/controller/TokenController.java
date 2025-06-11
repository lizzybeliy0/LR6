package ru.bmstu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.annotation.OnlyTeacher;
import ru.bmstu.exception.StudentNotFoundException;
import ru.bmstu.model.Student;
import ru.bmstu.service.StudentService;
import ru.bmstu.service.TokenServiceInt;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/tokens")
public class TokenController {
    private final TokenServiceInt tokenService;
    private final StudentService studentService;

    public TokenController(TokenServiceInt tokenService, StudentService studentService) {
        this.tokenService = tokenService;
        this.studentService = studentService;
    }

    @PutMapping("/{firstName}/{lastName}")
    @OnlyTeacher
    public ResponseEntity<Object> updateTokens(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody UpdateTokenRequest request) {

        Student student = studentService.findStudent(firstName, lastName);
        if (student == null) {
            throw new StudentNotFoundException();
        }

        int amount = request.getTokens();
        if (amount > 0) {
            tokenService.addTokens(student, amount);
        } else if (amount < 0) {
            tokenService.deleteTokens(student, -amount);
        }

        return ResponseEntity.ok(student);
    }

    private static class UpdateTokenRequest {//преобразование в tokens, что бы не все тело отправлять
        private int tokens;

        public int getTokens() {
            return tokens;
        }
        public void setTokens(int tokens) {
            this.tokens = tokens;
        }
    }
}