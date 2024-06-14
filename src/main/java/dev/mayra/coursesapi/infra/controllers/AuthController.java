package dev.mayra.coursesapi.infra.controllers;

import dev.mayra.coursesapi.infra.security.AuthService;
import dev.mayra.coursesapi.model.dtos.user.UserLoginDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operations related to login")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody @Valid UserLoginDTO user) {
        return ResponseEntity.ok().body(authService.auth(user));
    }
}
