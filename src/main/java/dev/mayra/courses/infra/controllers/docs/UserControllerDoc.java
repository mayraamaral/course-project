package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface UserControllerDoc {

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Data successfully listed"),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(hidden = true)))})
  public List<UserResponseDTO> listAllUsers();
}

