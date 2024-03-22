package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.app.services.UserService;
import dev.mayra.courses.infra.controllers.docs.UserControllerDoc;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
public class UserController implements UserControllerDoc {
  private final UserService userService;

  @GetMapping("/all")
  public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
    return new ResponseEntity<>(userService.listAllUsers(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> listById(@PathVariable Integer id) throws Exception {
    return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> createAUser(@RequestBody @Valid UserCreateDTO user) throws Exception {
    UserResponseDTO userCreated = userService.create(user);

    return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
  }
}
