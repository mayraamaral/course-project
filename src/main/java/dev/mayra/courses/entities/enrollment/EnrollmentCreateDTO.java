package dev.mayra.courses.entities.enrollment;

import jakarta.validation.Valid;
import lombok.Data;

import java.time.LocalDate;

@Data
@Valid
public class EnrollmentCreateDTO {
  private String courseCode;
}
