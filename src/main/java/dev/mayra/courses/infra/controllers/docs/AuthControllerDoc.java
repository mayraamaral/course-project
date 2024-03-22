package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.user.UserLoginDTO;
import dev.mayra.courses.utils.errors.ErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthControllerDoc {

  @Operation(summary = "Authenticates a user if credentials are correct")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
  })
  public ResponseEntity<String> auth(@RequestBody @Valid UserLoginDTO user);
}
