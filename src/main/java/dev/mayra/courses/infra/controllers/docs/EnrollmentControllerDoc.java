package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.enrollment.EnrollmentResponseDTO;
import dev.mayra.courses.utils.errors.ErrorDTO;
import dev.mayra.courses.utils.errors.ErrorListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface EnrollmentControllerDoc {

  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorListDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "Creates a new enrollment for the logged user, if they are not enrolled to the course yet")
  public ResponseEntity<EnrollmentResponseDTO> create(HttpServletRequest request, @PathVariable String courseCode) throws Exception;

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "Lists all enrollments, if you are an admin or instructor")
  public ResponseEntity<List<EnrollmentResponseDTO>> listAllEnrollments();

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "Lists all enrollments of a course, if they are not enrolled to the course yet")
  public ResponseEntity<List<EnrollmentResponseDTO>> listAllEnrollmentsByCourseCode(@PathVariable String courseCode) throws Exception;
}
