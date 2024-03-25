package dev.mayra.courses.infra.controllers.docs;

import dev.mayra.courses.entities.course.CourseCreateDTO;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.course.CourseStatus;
import dev.mayra.courses.utils.errors.ErrorDTO;
import dev.mayra.courses.utils.errors.ErrorListDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CourseControllerDoc {

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),

  })
  @Operation(summary = "Lists all courses or list by status if it is present, and if you are an admin")
  public ResponseEntity<Page<List<CourseResponseDTO>>> listAllOrByStatus(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "createdAt") String sort,
      @RequestParam(required = false) String status);

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
  @Operation(summary = "Lists all courses from an instructor, and if you are an admin")
  public ResponseEntity<List<CourseResponseDTO>> listAllByInstructor(@PathVariable String username) throws Exception;

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
  @Operation(summary = "Lists the requested course from its code if it exists, and if you are an admin")
  public ResponseEntity<CourseResponseDTO> listByCode(@PathVariable String code);

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
  @Operation(summary = "Creates a new course")
  public ResponseEntity<CourseResponseDTO> create(@RequestBody @Valid CourseCreateDTO course) throws Exception;

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorListDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "Inactivates a course if the user exists, and if you are an admin")
  public ResponseEntity<CourseResponseDTO> inactivateAnCourse(@PathVariable String code) throws Exception;

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK"),
      @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
      @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ErrorDTO.class))),
  })
  @Operation(summary = "Deletes a course if the course exists, and if you are an admin")
  public ResponseEntity deleteAnCourse(@PathVariable String code);
}
