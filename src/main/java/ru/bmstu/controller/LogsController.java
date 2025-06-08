package ru.bmstu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.repository.LogsRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
public class LogsController {
    private final LogsRepository logsRepository;

    public LogsController(LogsRepository logsRepository) {
        this.logsRepository = logsRepository;
    }

    @GetMapping
    public ResponseEntity<List<String>> getLogs() {
        return ResponseEntity.ok(logsRepository.readLogsFromFile());
    }
}