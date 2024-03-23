package dev.mayra.courses.entities.course;

import dev.mayra.courses.entities.user.InstructorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseMinifiedDTO {
  private String code;
  private InstructorDTO instructor;
  private CourseStatus status;
  private LocalDate createdAt;
}
