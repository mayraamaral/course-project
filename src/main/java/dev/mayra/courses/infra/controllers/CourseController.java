package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.app.services.CourseService;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
@Validated
@RequiredArgsConstructor
@Tag(name = "Course", description = "Operations related to the Course entity")
public class CourseController {
  private final CourseService courseService;

  @GetMapping("/all")
  public ResponseEntity<List<CourseResponseDTO>> listAllUsers() {
    return new ResponseEntity<>(courseService.listAllCourses(), HttpStatus.OK);
  }
}
