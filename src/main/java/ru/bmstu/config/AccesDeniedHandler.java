/*package ru.bmstu.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice//глобальный обработчик исключений
public class AccesDeniedHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleAccessDenied(ResponseStatusException ex) {
        Map<String, String> response = Collections.singletonMap("message", "Access denied");
        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}

// exeption - обработчик (Handler), все кастомные exeption (ResponseStatusException(HttpStatus.FORBIDDEN, message); - сделать кастомные, не дефолт)

 */