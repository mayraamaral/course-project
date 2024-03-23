package dev.mayra.courses.entities.course;

import dev.mayra.courses.entities.user.InstructorDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Valid
public class CourseCreateDTO {

  @NotBlank(message = "The course name can't be blank")
  @Schema(description = "Fill the course name", required = true, example = "Spring Boot Advanced")
  private String name;

  @NotBlank(message = "The course code can't be blank")
  @Schema(description = "Fill the course code", required = true, example = "spring-boot")
  @Pattern(regexp = "^[A-Za-z]$|^[A-Za-z][A-Za-z\\-]*[A-Za-z]$",
      message = "The course code must be alphanumeric, without spaces or special characters, " +
          "but it can " +
          "be separated by hyphens, for example: spring-boot-advanced.")
  @Size(max = 20, message = "The course code can have a maximum of 20 characters")
  private String code;

  @NotBlank(message = "The course instructor can't be blank")
  @Schema(description = "Fill the instructor username", required = true, example = "namelastname")
  private String instructorUsername;

  @NotBlank(message = "The course description can't be blank")
  @Schema(description = "Fill the course description", required = true, example = "Course about " +
      "design patterns")
  private String description;
}
