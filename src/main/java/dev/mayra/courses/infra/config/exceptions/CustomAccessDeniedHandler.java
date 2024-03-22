package dev.mayra.courses.infra.config.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayra.courses.utils.errors.ErrorMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, IOException {
    String errorMsg = HttpStatus.FORBIDDEN.getReasonPhrase();
    String message = "You do not have access to this resource";
    int statusCode = HttpServletResponse.SC_FORBIDDEN;

    Map<String, Object> body = ErrorMap.get(errorMsg, message, statusCode);

    String jsonBody = objectMapper.writeValueAsString(body);

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json");
    response.getWriter().write(jsonBody);
  }
}