package dev.mayra.courses.infra.repositories;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.course.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, String> {

  @Query("select new dev.mayra.courses.entities.course.CourseResponseDTO(" +
      "c.name, c.code, new dev.mayra.courses.entities.user.InstructorDTO(u.idUser, u.name, u.username), " +
      "c.description, c.status, c.createdAt, c.inactivatedAt) " +
      "from Course c inner join User u on c.instructor.idUser = u.idUser")
  public List<CourseResponseDTO> findAllDto();

  @Query(value = "select new dev.mayra.courses.entities.course.CourseResponseDTO(" +
      "c.name, c.code, new dev.mayra.courses.entities.user.InstructorDTO(u.idUser, u.name, u.username), " +
      "c.description, c.status, c.createdAt, c.inactivatedAt) " +
      "from Course c inner join User u on c.instructor.idUser = u.idUser " +
      "where (:status is null or c.status = :status)",
  countQuery = "select count(*)" +
      "from Course c inner join User u on c.instructor.idUser = u.idUser " +
      "where (:status is null or c.status = :status)")
  public Page<List<CourseResponseDTO>> findAllCoursesOrByStatus(@Param("status") CourseStatus status, Pageable pageable);

  public Optional<Course> findByCode(String code);

  public List<Course> findAllByInstructorUsername(String username);

}
