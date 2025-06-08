package ru.bmstu.model;

import java.time.LocalDateTime;

public class MyLogger {
    private LocalDateTime timestamp;
    private String action;
    private String details;

    public MyLogger(LocalDateTime timestamp, String action, String details) {
        this.timestamp = timestamp;
        this.action = action;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
