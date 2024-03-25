package dev.mayra.courses.infra.repositories;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.enrollment.Enrollment;
import dev.mayra.courses.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
  public Optional<Enrollment> findByCourseAndUser(Course course, User user);

  public List<Enrollment> findAllByCourse(Course course);
}
