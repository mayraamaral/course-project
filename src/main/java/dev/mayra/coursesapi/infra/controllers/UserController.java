package dev.mayra.coursesapi.infra.controllers;

import dev.mayra.coursesapi.model.dtos.user.UserCreateDTO;
import dev.mayra.coursesapi.model.dtos.user.UserResponseDTO;
import dev.mayra.coursesapi.services.UserService;
import dev.mayra.coursesapi.utils.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Operations related to the User entity")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAllUsers() {
        return ResponseEntity.ok().body(userService.listAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserResponseDTO> listByUsername(@PathVariable String username) throws NotFoundException {
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createAnUser(@RequestBody @Valid UserCreateDTO user) throws Exception {
        UserResponseDTO userCreated = userService.create(user);

        return new ResponseEntity<>(userCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateAnUser(@PathVariable Long id, @RequestBody @Valid UserCreateDTO user) throws Exception {
        UserResponseDTO userCreated = userService.update(id, user);

        return ResponseEntity.ok().body(userCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnUser(@PathVariable Long id) throws NotFoundException {
        userService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
