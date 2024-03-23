package dev.mayra.courses.entities.enrollment;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enrollment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "enrollment_id")
  private Integer idEnrollment;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "course_code", referencedColumnName = "code")
  private Course course;

  @Column(name = "enrolled_at")
  private LocalDate enrolledAt;
}
