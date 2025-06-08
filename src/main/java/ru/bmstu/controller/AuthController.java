package ru.bmstu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.aspect.RoleCheck;
import ru.bmstu.model.Role;
import ru.bmstu.repository.LogsRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final RoleCheck roleCheckAspect;
    private final LogsRepository logsRepository;

    public AuthController(RoleCheck roleCheckAspect, LogsRepository logsRepository) {
        this.roleCheckAspect = roleCheckAspect;
        this.logsRepository = logsRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestHeader String firstName,
            @RequestHeader String lastName,
            @RequestHeader String role) {
        try {
            Role userRole = Role.valueOf(role.toUpperCase());
            roleCheckAspect.setCurrentUserRole(userRole);
            logsRepository.logAction("Authorization",
                    firstName + " " + lastName + " as " + userRole);
            return ResponseEntity.ok("Logged in as " + userRole);
        } catch (IllegalArgumentException e) {
            roleCheckAspect.setCurrentUserRole(Role.STUDENT);
            logsRepository.logAction("Authorization",
                    firstName + " " + lastName + " as " + Role.STUDENT);
            return ResponseEntity.ok("Invalid role. Logged in as STUDENT.");
        }
    }
}