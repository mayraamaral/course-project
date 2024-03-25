package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.report.ReportNpsDTO;
import dev.mayra.courses.utils.errors.ErrorDTO;
import dev.mayra.courses.utils.errors.ErrorListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

public interface ReportControllerDoc {

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "List all courses and its NPS, if you are an admin or instructor")
  public ResponseEntity<List<ReportNpsDTO>> listAllCoursesNps();

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),

  })
  @Operation(summary = "List course NPS by code, if you are an admin or instructor")
  public ResponseEntity<ReportNpsDTO> listCourseNpsByCode(@PathVariable String courseCode);

  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Created"),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "Sends a NPS report to your email, if you are an admin or instructor")
  public ResponseEntity<String> sendCoursesNpsReport(HttpServletRequest request) throws MessagingException, IOException;

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
  @Operation(summary = "Sends a feedback report to your email of the course you request, if you are an admin or instructor")
  public ResponseEntity<String> sendCourseFeedbacksReport(HttpServletRequest request, @PathVariable String courseCode) throws Exception;
}
