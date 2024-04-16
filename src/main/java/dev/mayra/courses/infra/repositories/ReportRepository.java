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

  @Query("SELECT new dev.mayra.courses.entities.report.ReportNpsDTO(" +
      "c.code, c.name, " +
      "CAST(ROUND(((SUM(CASE WHEN f.rating >= 9 THEN 1 ELSE 0 END) - " +
      "SUM(CASE WHEN f.rating <= 6 THEN 1 ELSE 0 END)) * 100.0 / COUNT(*)), 2) AS java.lang.Double)) " +
      "FROM Feedback f " +
      "INNER JOIN Enrollment e ON f.enrollment.idEnrollment = e.idEnrollment " +
      "INNER JOIN Course c ON e.course.code = c.code " +
      "GROUP BY c.code, c.name")
  public List<ReportNpsDTO> listAllCoursesNps();

  @Query("select new dev.mayra.courses.entities.report.ReportNpsDTO(" +
      "c.code, c.name, " +
      "CAST(ROUND(((SUM(CASE WHEN f.rating >= 9 THEN 1 ELSE 0 END) - " +
      "SUM(CASE WHEN f.rating <= 6 THEN 1 ELSE 0 END)) * 100.0 / COUNT(*)), 2) AS java.lang.Double)) " +
      "from Feedback f inner join Enrollment e on f.enrollment.idEnrollment = e.idEnrollment " +
      "inner join Course c on e.course.code = c.code " +
      "where c.code = :courseCode")
  public ReportNpsDTO listCourseNpsByCode(@Param("courseCode") String courseCode);

  @Query("select ROUND(((SUM(CASE WHEN f.rating >= 9 THEN 1 ELSE 0 END) - " +
      "SUM(CASE WHEN f.rating <= 6 THEN 1 ELSE 0 END)) * 100.0 / COUNT(*)), 2) " +
      "from Feedback f inner join Enrollment e on f.enrollment.idEnrollment = e.idEnrollment " +
      "inner join Course c on e.course.code = c.code " +
      "where c.code = :courseCode")
  public Double findCourseNpsByCode(@Param("courseCode") String courseCode);

}
