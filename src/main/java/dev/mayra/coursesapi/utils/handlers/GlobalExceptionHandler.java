package dev.mayra.coursesapi.utils.handlers;

import dev.mayra.coursesapi.utils.errors.ErrorListDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorListDTO> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream().map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());

        ErrorListDTO errorBody = new ErrorListDTO(
            new Date(),
            HttpStatus.BAD_REQUEST.value(),
            errors,
            "There are errors in your submission",
            request.getServletPath());

        return new ResponseEntity<>(errorBody, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
