package dev.mayra.courses.entities.enrollment;

import dev.mayra.courses.entities.course.CourseMinifiedDTO;
import dev.mayra.courses.entities.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentMinifiedDTO {
  private Integer idEnrollment;
  private CourseMinifiedDTO course;
  private UserDTO user;
  private LocalDate enrolledAt;
}
