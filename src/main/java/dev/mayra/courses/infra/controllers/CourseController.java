package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.app.services.CourseService;
import dev.mayra.courses.entities.course.CourseCreateDTO;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.infra.controllers.docs.CourseControllerDoc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/course")
@Validated
@RequiredArgsConstructor
@Tag(name = "Course", description = "Operations related to the Course entity")
public class CourseController implements CourseControllerDoc {
  private final CourseService courseService;

  @GetMapping
  public ResponseEntity<List<CourseResponseDTO>> listAllOrByStatus(@RequestParam(required = false) String status) {
    return new ResponseEntity<>(courseService.listAllOrByStatus(status), HttpStatus.OK);
  }

  @PutMapping("/inactivate/{code}")
  public ResponseEntity<CourseResponseDTO> inactivateAnCourse(@PathVariable String code) throws Exception {
    return new ResponseEntity<>(courseService.inactivate(code), HttpStatus.OK);
  }

  @DeleteMapping("/{code}")
  public ResponseEntity deleteAnCourse(@PathVariable String code) {
    courseService.delete(code);

    return ResponseEntity.ok().build();
  }

  @GetMapping("/{code}")
  public ResponseEntity<CourseResponseDTO> listByCode(@PathVariable String code) {
    return new ResponseEntity<>(courseService.listByCode(code), HttpStatus.OK);
  }

  @GetMapping("/by-instructor/{username}")
  public ResponseEntity<List<CourseResponseDTO>> listAllByInstructor(@PathVariable String username) throws Exception {
    List<CourseResponseDTO> coursesFound = courseService.listAllByInstructorUsername(username);

    return new ResponseEntity<>(coursesFound, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CourseResponseDTO> create(@RequestBody @Valid CourseCreateDTO course) throws Exception {
    CourseResponseDTO courseCreated = courseService.create(course);

    return new ResponseEntity<>(courseCreated, HttpStatus.CREATED);
  }
}
