package dev.mayra.courses.entities.course;

import dev.mayra.courses.app.services.CourseService;
import dev.mayra.courses.entities.user.InstructorDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDTO {
  private String name;
  private String code;
  private InstructorDTO instructor;
  private String description;
  private CourseStatus status;
  private LocalDate createdAt;
}
