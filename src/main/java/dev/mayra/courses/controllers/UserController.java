package dev.mayra.courses.controllers;

import dev.mayra.courses.domain.dtos.UserCreateDTO;
import dev.mayra.courses.domain.dtos.UserResponseDTO;
import dev.mayra.courses.domain.entities.User;
import dev.mayra.courses.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@RequiredArgsConstructor
@Tag(name = "User", description = "Operations related to the User entity")
public class UserController {
  private final UserService userService;

  @GetMapping
  public List<UserResponseDTO> listAllUsers() {
    return userService.listAllUsers();
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> createAUser(@RequestBody @Valid UserCreateDTO user) {
    UserResponseDTO userCreated = userService.create(user);

    return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
  }
}
