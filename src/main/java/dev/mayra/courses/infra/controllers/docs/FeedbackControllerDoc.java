package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.feedback.FeedbackCreateDTO;
import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import dev.mayra.courses.utils.errors.ErrorDTO;
import dev.mayra.courses.utils.errors.ErrorListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FeedbackControllerDoc {

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
  @Operation(summary = "Creates a new feedback for the logged user, if they are enrolled to the course")
  public ResponseEntity<FeedbackResponseDTO> createAFeedback(HttpServletRequest request, @RequestBody @Valid FeedbackCreateDTO feedback) throws Exception;

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "List the feedbacks of a course from its code if it exists, and if you are an admin or instructor")
  public ResponseEntity<List<FeedbackResponseDTO>> listAllFeedbacksByCourseCode(@PathVariable String courseCode);

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "List all feedbacks of all courses, and if you are an admin or instructor")
  public ResponseEntity<List<FeedbackResponseDTO>> listAllFeedbacks();
}
