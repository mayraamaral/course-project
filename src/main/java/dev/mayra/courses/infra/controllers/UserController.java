package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserMinifiedDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.app.services.UserService;
import dev.mayra.courses.infra.controllers.docs.UserControllerDoc;
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

  @GetMapping("/by-id/{id}")
  public ResponseEntity<UserResponseDTO> listById(@PathVariable Integer id) throws Exception {
    return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/by-username/{username}")
  public ResponseEntity<UserMinifiedDTO> listByUsername(@PathVariable String username) throws Exception {
    return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserResponseDTO> createAnUser(@RequestBody @Valid UserCreateDTO user) throws Exception {
    UserResponseDTO userCreated = userService.create(user);

    return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> updateAnUser(@PathVariable Integer id, @RequestBody @Valid UserCreateDTO user) throws Exception {
    UserResponseDTO userCreated = userService.update(id, user);

    return new ResponseEntity<>(userCreated, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteAnUser(@PathVariable Integer id) throws Exception {
    userService.delete(id);;

    return ResponseEntity.ok().build();
  }

}
