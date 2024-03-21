package dev.mayra.courses.infra.config.exceptions;

import dev.mayra.courses.utils.ErrorMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatusCode status,
                                                                WebRequest request) {

    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(x -> x.getField() + ": " + x.getDefaultMessage())
        .collect(Collectors.toList());

    String msg = HttpStatus.BAD_REQUEST.getReasonPhrase();

    Map<String, Object> body = ErrorMap.get(errors, msg, status.value());

    return new ResponseEntity<>(body, headers, status);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleException(Exception exception,
                                                HttpServletRequest request) {
    String errorMsg = exception.getMessage();
    int statusCode = HttpStatus.BAD_REQUEST.value();
    String msg = HttpStatus.BAD_REQUEST.getReasonPhrase();

    Map<String, Object> body = ErrorMap.get(errorMsg, msg, statusCode);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<Object> handleException(AuthenticationException exception,
                                                            HttpServletRequest request) {
    String errorMsg = exception.getMessage();
    int statusCode = HttpStatus.FORBIDDEN.value();
    String msg = HttpStatus.FORBIDDEN.getReasonPhrase();

    Map<String, Object> body = ErrorMap.get(errorMsg, msg, statusCode);

    return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleException(ConstraintViolationException exception,
                                                HttpServletRequest request) {

    String errorMsg = exception.getMessage();
    int statusCode = HttpStatus.BAD_REQUEST.value();
    String msg = HttpStatus.BAD_REQUEST.getReasonPhrase();

    Map<String, Object> body = ErrorMap.get(errorMsg, msg, statusCode);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleException(DataIntegrityViolationException exception,
                                                HttpServletRequest request) {
    String errorMsg = exception.getMessage();
    int statusCode = HttpStatus.BAD_REQUEST.value();
    String msg = HttpStatus.BAD_REQUEST.getReasonPhrase();

    Map<String, Object> body = ErrorMap.get(errorMsg, msg, statusCode);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}