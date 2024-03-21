package dev.mayra.courses.infra.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {
  private final TokenService tokenService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String tokenFromHeader = getTokenFromHeader(request);

    UsernamePasswordAuthenticationToken user = tokenService.validateToken(tokenFromHeader);
    SecurityContextHolder.getContext().setAuthentication(user);

    filterChain.doFilter(request, response);
  }
  private String getTokenFromHeader(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token == null) {
      return null;
    }
    return token.replace("Bearer ", "");
  }

}
