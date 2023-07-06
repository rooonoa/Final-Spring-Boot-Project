package online.store.controller.error;

import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice // Indicates that this class handles exceptions globally for all controllers
@Slf4j // Lombok annotation for SLF4J logger

public class GlobalErrorHandler {

    @ExceptionHandler(NoSuchElementException.class) // Handles NoSuchElementException specifically
    @ResponseStatus(code = HttpStatus.NOT_FOUND) // Sets the HTTP response status to 404 (Not Found)
    public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("Exception: {}", ex.toString()); // Logs the error message using SLF4J logger
        return Map.of("message", ex.toString()); // Returns a map with a single entry containing the error message
    }
}
