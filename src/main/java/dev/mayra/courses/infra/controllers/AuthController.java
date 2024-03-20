package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.app.services.AuthService;
import dev.mayra.courses.entities.user.UserLoginDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Operations related to login")
public class AuthController {
  private final AuthService authService;

  @PostMapping
  public ResponseEntity<String> auth(@RequestBody @Valid UserLoginDTO user) {
    String token = authService.auth(user);

    return new ResponseEntity<>(token, HttpStatus.OK);
  }
}
