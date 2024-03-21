package dev.mayra.courses.utils;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorMap {
  public static Map<String, Object> get(Map<String, Object> errors,
                                         HttpStatus status) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", new Date());
    errorResponse.put("status", status.value());
    errorResponse.put("errors", errors);
    return errorResponse;
  }

  public static Map<String, Object> get(List<String> errors, String message,
                                        int status) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", new Date());
    errorResponse.put("message", message);
    errorResponse.put("status", status);
    errorResponse.put("errors", errors);
    return errorResponse;
  }

  public static Map<String, Object> get(String error, String message,
                                        int status) {
    Map<String, Object> errorResponse = new HashMap<>();
    errorResponse.put("timestamp", new Date());
    errorResponse.put("status", status);
    errorResponse.put("message", message);
    errorResponse.put("error", error);
    return errorResponse;
  }
}
