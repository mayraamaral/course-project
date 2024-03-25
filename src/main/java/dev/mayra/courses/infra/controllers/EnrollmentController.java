package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.app.services.EnrollmentService;
import dev.mayra.courses.entities.enrollment.EnrollmentResponseDTO;
import dev.mayra.courses.infra.controllers.docs.EnrollmentControllerDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
@Validated
@RequiredArgsConstructor
@Tag(name = "Enrollment", description = "Operations related to the Enrollment entity")
public class EnrollmentController implements EnrollmentControllerDoc {
  private final EnrollmentService enrollmentService;

  @PostMapping("/{courseCode}")
  public ResponseEntity<EnrollmentResponseDTO> create(HttpServletRequest request, @PathVariable String courseCode) throws Exception {
    EnrollmentResponseDTO enrollment = enrollmentService.create(request, courseCode);

    return new ResponseEntity<>(enrollment, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<EnrollmentResponseDTO>> listAllEnrollments() {
    return new ResponseEntity<>(enrollmentService.listAllEnrollments(), HttpStatus.OK);
  }

  @GetMapping("/{courseCode}")
  public ResponseEntity<List<EnrollmentResponseDTO>> listAllEnrollmentsByCourseCode(@PathVariable String courseCode) throws Exception {
    return new ResponseEntity<>(enrollmentService.listAllByCourseCode(courseCode), HttpStatus.OK);
  }
}
