package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.enrollment.Enrollment;
import dev.mayra.courses.entities.feedback.Feedback;
import dev.mayra.courses.entities.feedback.FeedbackCreateDTO;
import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.FeedbackRepository;
import dev.mayra.courses.utils.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
  private final FeedbackRepository feedbackRepository;
  private final AuthService authService;
  private final EnrollmentService enrollmentService;
  private final Mapper mapper;

  public List<FeedbackResponseDTO> listAllFeedbacks() {
    List<Feedback> feedbacks = feedbackRepository.findAll();
    List<FeedbackResponseDTO> feedbacksDto = feedbacks.stream()
        .map(entity -> mapper.convertToDTO(entity, FeedbackResponseDTO.class))
        .collect(Collectors.toList());

    return feedbacksDto;
  }

  public List<FeedbackResponseDTO> listAllFeedbacksByCourseCode(String courseCode) {
    return feedbackRepository.findAllByCourseCode(courseCode);
  }

  public FeedbackResponseDTO create(HttpServletRequest request, FeedbackCreateDTO feedbackToBeCreated) throws Exception {

    Enrollment enrollment = enrollmentService.findById(feedbackToBeCreated.getIdEnrollment());

    checkIfLoggedUserIsEnrolledToCourse(request, enrollment);

    Feedback feedback = mapper.convertToEntity(feedbackToBeCreated, Feedback.class);
    feedback.setEnrollment(enrollment);
    feedback.setFeedbackDate(LocalDate.now());

    Feedback feedbackCreated = feedbackRepository.save(feedback);

    return mapper.convertToDTO(feedbackCreated, FeedbackResponseDTO.class);
  }

  public void checkIfLoggedUserIsEnrolledToCourse(HttpServletRequest request, Enrollment enrollment) throws Exception {
    String token = request.getHeader("Authorization");

    User user = authService.getLoggedUser(token);

    if(!isUserEnrolledToTheFeedbackCourse(user, enrollment)) {
      throw new Exception("You can only give feedback to courses you are enrolled to");
    }
  }

  public boolean isUserEnrolledToTheFeedbackCourse(User user, Enrollment enrollment) {
    return user.getIdUser() == enrollment.getUser().getIdUser();
  }
}
