package ru.bmstu.repository;

import ru.bmstu.model.MyLogger;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManageLogRepository implements LogsRepository {
    private final String logFilePath = "logs.txt";

    @Override
    public void logAction(String action, String details) {
        MyLogger logEntry = new MyLogger(LocalDateTime.now(), action, details);
        writeLogToFile(logEntry);
    }

    private void writeLogToFile(MyLogger logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            String logLine = String.format("%s - Action: %s - Description: %s%n",
                    logEntry.getTimestamp(),
                    logEntry.getAction(),
                    logEntry.getDetails());
            writer.write(logLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> readLogsFromFile() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
