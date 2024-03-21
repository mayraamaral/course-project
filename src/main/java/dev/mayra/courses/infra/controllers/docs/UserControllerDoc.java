package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.user.UserCreateDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import dev.mayra.courses.utils.ErrorHandlerDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface UserControllerDoc {

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Resource(s) listed"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorHandlerDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorHandlerDTO.class))),
  })
  public List<UserResponseDTO> listAllUsers();

  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Resource created"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorHandlerDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorHandlerDTO.class))),
  })
  public ResponseEntity<UserResponseDTO> createAUser(@RequestBody @Valid UserCreateDTO user);
}

