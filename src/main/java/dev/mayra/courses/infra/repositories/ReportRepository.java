package dev.mayra.courses.infra.repositories;

import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.course.CourseStatus;
import dev.mayra.courses.entities.feedback.Feedback;
import dev.mayra.courses.entities.report.ReportNpsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends JpaRepository<Feedback, Integer> {

  @Query("select new dev.mayra.courses.entities.report.ReportNpsDTO(" +
      "c.code, c.name, avg(f.rating)) " +
      "from Feedback f inner join Enrollment e on f.enrollment.idEnrollment = e.idEnrollment " +
      "inner join Course c on e.course.code = c.code " +
      "group by c.code")
  public List<ReportNpsDTO> listAllCoursesNps();

  @Query("select new dev.mayra.courses.entities.report.ReportNpsDTO(" +
      "c.code, c.name, avg(f.rating)) " +
      "from Feedback f inner join Enrollment e on f.enrollment.idEnrollment = e.idEnrollment " +
      "inner join Course c on e.course.code = c.code " +
      "where c.code = :courseCode")
  public ReportNpsDTO listCourseNpsByCode(@Param("courseCode") String courseCode);

  @Query("select avg(f.rating)" +
      "from Feedback f inner join Enrollment e on f.enrollment.idEnrollment = e.idEnrollment " +
      "inner join Course c on e.course.code = c.code " +
      "where c.code = :courseCode")
  public Double findCourseNpsByCode(@Param("courseCode") String courseCode);

}
