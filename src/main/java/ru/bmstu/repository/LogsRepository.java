package ru.bmstu.repository;

import ru.bmstu.model.MyLogger;

import java.util.List;

public interface LogsRepository {
    void logAction(String action, String details);
    List<String> readLogsFromFile();
}
