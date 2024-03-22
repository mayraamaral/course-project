package dev.mayra.courses.infra.repositories;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, String> {

  @Query("select new dev.mayra.courses.entities.course.CourseResponseDTO(" +
      "c.name, c.code, new dev.mayra.courses.entities.user.InstructorDTO(u.idUser, u.name), " +
      "c.description, c.status, c.createdAt) " +
      "from Course c inner join User u on c.instructor.idUser = u.idUser")
  public List<CourseResponseDTO> findAllDto();

}
