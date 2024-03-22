package dev.mayra.courses.infra.repositories;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
