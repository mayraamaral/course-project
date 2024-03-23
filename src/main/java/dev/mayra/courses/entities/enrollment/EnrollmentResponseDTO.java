package dev.mayra.courses.entities.enrollment;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.user.User;
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
public class EnrollmentResponseDTO {
  private Integer idEnrollment;
  private CourseResponseDTO course;
  private UserDTO user;
  private LocalDate enrolledAt;
}
