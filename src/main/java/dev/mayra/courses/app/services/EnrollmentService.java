package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.enrollment.Enrollment;
import dev.mayra.courses.entities.enrollment.EnrollmentResponseDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.EnrollmentRepository;
import dev.mayra.courses.utils.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
  private final EnrollmentRepository enrollmentRepository;

  private final AuthService authService;
  private final CourseService courseService;
  private final Mapper mapper;

  public EnrollmentResponseDTO create(HttpServletRequest request, String courseCode) throws Exception {
    String token = request.getHeader("Authorization");

    User user = authService.getLoggedUser(token);
    Course course = courseService.findByCode(courseCode);

    checkIfUserIsEnrolledToCourse(course, user);
    checkIfCourseIsActive(course);

    Enrollment enrollment = new Enrollment();
    enrollment.setUser(user);
    enrollment.setCourse(course);
    enrollment.setEnrolledAt(LocalDate.now());

    Enrollment enrollmentSaved = enrollmentRepository.save(enrollment);

    return mapper.convertToDTO(enrollmentSaved, EnrollmentResponseDTO.class);
  }

  public Enrollment findById(Integer id) {
    Optional<Enrollment> enrollmentOpt = enrollmentRepository.findById(id);

    if(enrollmentOpt.isEmpty()) {
      throw new NotFoundException("Enrollment not found");
    }

    return enrollmentOpt.get();
  }

  public void checkIfCourseIsActive(Course course) throws Exception {
    if(courseService.isTheGivenCourseInactive(course)) {
      throw new Exception("You can only enroll active courses");
    }
  }

  public void checkIfUserIsEnrolledToCourse(Course course, User user) throws Exception {
    Optional<Enrollment> enrollmentOpt = enrollmentRepository.findByCourseAndUser(course, user);

    if(enrollmentOpt.isPresent()) {
      throw new Exception("The user is already enrolled in the course");
    }
  }
}
