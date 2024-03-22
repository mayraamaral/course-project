package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.utils.errors.ErrorDTO;
import dev.mayra.courses.utils.errors.ErrorHandlerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserControllerDoc {

  @Operation(summary = "List all users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorHandlerDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorHandlerDTO.class))),
  })
  public ResponseEntity<List<UserResponseDTO>> listAllUsers();

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorHandlerDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorHandlerDTO.class))),
  })
  @Operation(summary = "List the requested user if it exists")
  public ResponseEntity<UserResponseDTO> listById(@PathVariable Integer id) throws Exception;

  @Operation(summary = "Creates a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorHandlerDTO.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorHandlerDTO.class))),
  })
  public ResponseEntity<UserResponseDTO> createAUser(@RequestBody @Valid UserCreateDTO user) throws Exception;
}

