package ru.bmstu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.model.Student;
import ru.bmstu.service.StudentService;
import ru.bmstu.service.TokenService;

@RestController
@RequestMapping("/api/v1/tokens")
public class TokenController {
    private final TokenService tokenService;
    private final StudentService studentService;

    public TokenController(TokenService tokenService, StudentService studentService) {
        this.tokenService = tokenService;
        this.studentService = studentService;
    }

    @PostMapping("/{firstName}/{lastName}/add/{amount}")
    public ResponseEntity<Student> addTokens(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @PathVariable int amount) {
        Student student = studentService.findStudent(firstName, lastName);
        if (student != null) {
            tokenService.addTokens(student, amount);
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{firstName}/{lastName}/subtract/{amount}")
    public ResponseEntity<Student> subtractTokens(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @PathVariable int amount) {
        Student student = studentService.findStudent(firstName, lastName);
        if (student != null) {
            tokenService.deleteTokens(student, amount);
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }
}