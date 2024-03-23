package dev.mayra.courses.infra.repositories;

import dev.mayra.courses.entities.feedback.Feedback;
import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
  @Query("select new dev.mayra.courses.entities.feedback.FeedbackResponseDTO(" +
      "f.idFeedback, " +
      "new dev.mayra.courses.entities.enrollment.EnrollmentMinifiedDTO(f.enrollment.idEnrollment, " +
      "new dev.mayra.courses.entities.course.CourseMinifiedDTO(c.code, " +
      "new dev.mayra.courses.entities.user.InstructorDTO(c.instructor.idUser, c.instructor.name, c.instructor.username), " +
      "c.status, c.createdAt), " +
      "new dev.mayra.courses.entities.user.UserDTO(e.user.idUser, e.user.name, e.user.username), " +
      "e.enrolledAt), f.rating, f.comment, f.feedbackDate) " +
      "from Feedback f inner join Enrollment e on f.enrollment.idEnrollment = e.idEnrollment " +
      "inner join Course c on e.course.code = c.code " +
      "where c.code = :courseCode")
  public List<FeedbackResponseDTO> findAllByCourseCode(@Param("courseCode") String courseCode);
}
