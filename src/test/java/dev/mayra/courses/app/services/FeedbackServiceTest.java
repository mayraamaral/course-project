package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.course.CourseMinifiedDTO;
import dev.mayra.courses.entities.course.CourseStatus;
import dev.mayra.courses.entities.enrollment.Enrollment;
import dev.mayra.courses.entities.enrollment.EnrollmentMinifiedDTO;
import dev.mayra.courses.entities.feedback.Feedback;
import dev.mayra.courses.entities.feedback.FeedbackCreateDTO;
import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.entities.user.UserDTO;
import dev.mayra.courses.infra.email.EmailService;
import dev.mayra.courses.infra.repositories.FeedbackRepository;
import dev.mayra.courses.utils.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static dev.mayra.courses.app.services.CourseServiceTest.getInstructorDTOMock;
import static dev.mayra.courses.app.services.EnrollmentServiceTest.getEnrollmentMock;
import static dev.mayra.courses.app.services.UserServiceTest.getUserMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Feedback Service Tests")
class FeedbackServiceTest {
  @Mock
  private FeedbackRepository feedbackRepository;
  @Mock
  private AuthService authService;
  @Mock
  private EnrollmentService enrollmentService;
  @Mock
  private HttpServletRequest requestMock;
  @Mock
  private Mapper mapper;

  @Spy
  @InjectMocks
  private FeedbackService feedbackService;

  private static LocalDate currentDate = LocalDate.now();

  @Test
  void testShouldListAllWithSuccess() {
    List<Feedback> feedbacksList = getFeedbackListMock();

    when(feedbackRepository.findAll()).thenReturn(feedbacksList);

    List<FeedbackResponseDTO> feedbacksResponseMock = getFeedbackResponseDTOListMock();

    when(mapper.convertToDTO(any(Feedback.class), eq(FeedbackResponseDTO.class)))
        .thenReturn(feedbacksResponseMock.get(0), feedbacksResponseMock.get(1),
            feedbacksResponseMock.get(2));

    List<FeedbackResponseDTO> responseList = feedbackService.listAllFeedbacks();

    assertEquals(feedbacksResponseMock, responseList);
  }

  @Test
  void testShouldListByCourseCodeWithSuccess() {
    List<FeedbackResponseDTO> feedbacksResponseMock = getFeedbackResponseDTOListMock();

    when(feedbackRepository.findAllByCourseCode(any())).thenReturn(feedbacksResponseMock);

    List<FeedbackResponseDTO> responseList = feedbackService.listAllFeedbacksByCourseCode(any());

    assertEquals(feedbacksResponseMock, responseList);
  }

  @Test
  void testShouldCreateFeedbackWithSuccess() throws Exception {
    FeedbackCreateDTO feedbackCreateMock = getFeedbackCreateDTOMock();

    Enrollment enrollmentMock = getEnrollmentMock();

    when(enrollmentService.findById(any())).thenReturn(enrollmentMock);

    Feedback feedbackMock = getFeedbackMock();

    when(mapper.convertToEntity(any(FeedbackCreateDTO.class), eq(Feedback.class)))
        .thenReturn(feedbackMock);
    when(feedbackRepository.save(any())).thenReturn(feedbackMock);

    FeedbackResponseDTO feedbackResponseMock = getFeedbackResponseDTOMock();

    when(mapper.convertToDTO(any(Feedback.class), eq(FeedbackResponseDTO.class)))
        .thenReturn(feedbackResponseMock);

    doNothing().when(feedbackService)
        .checkIfLoggedUserIsEnrolledToCourse(any(HttpServletRequest.class),
            any(Enrollment.class));

    FeedbackResponseDTO responseFeedback = feedbackService.create(requestMock, feedbackCreateMock);

    assertEquals(feedbackResponseMock, responseFeedback);
  }

  @Test
  void testShouldReturnIfLoggedUserIsEnrolled() {
    when(requestMock.getHeader("Authorization")).thenReturn("Bearer mockToken");
    User userMock = getUserMock();

    when(authService.getLoggedUser(any())).thenReturn(userMock);

    Enrollment enrollmentMock = getEnrollmentMock();

    assertDoesNotThrow(() -> feedbackService.checkIfLoggedUserIsEnrolledToCourse(requestMock, enrollmentMock));
  }

  public List<Feedback> getFeedbackListMock() {
    return List.of(getFeedbackMock(), getFeedbackMock(), getFeedbackMock());
  }

  public List<FeedbackResponseDTO> getFeedbackResponseDTOListMock() {
    return List.of(getFeedbackResponseDTOMock(), getFeedbackResponseDTOMock(),
        getFeedbackResponseDTOMock());
  }

  public static FeedbackResponseDTO getFeedbackResponseDTOMock() {
    FeedbackResponseDTO feedback = new FeedbackResponseDTO();
    feedback.setIdFeedback(1);
    feedback.setEnrollment(getEnrollmentMinifiedDTOMock());
    feedback.setRating(10);
    feedback.setComment("comment");
    feedback.setFeedbackDate(currentDate);

    return feedback;
  }

  public static EnrollmentMinifiedDTO getEnrollmentMinifiedDTOMock() {
    EnrollmentMinifiedDTO enrollment = new EnrollmentMinifiedDTO();
    enrollment.setIdEnrollment(1);

    UserDTO userMock = new UserDTO();
    userMock.setIdUser(1);
    userMock.setName("User");
    userMock.setUsername("user");

    enrollment.setUser(userMock);

    CourseMinifiedDTO course = new CourseMinifiedDTO();
    course.setCode("course");
    course.setInstructor(getInstructorDTOMock());
    course.setStatus(CourseStatus.ACTIVE);
    course.setCreatedAt(currentDate);

    enrollment.setCourse(course);
    enrollment.setEnrolledAt(currentDate);

    return enrollment;
  }

  public static FeedbackCreateDTO getFeedbackCreateDTOMock() {
    FeedbackCreateDTO feedback = new FeedbackCreateDTO();
    feedback.setIdEnrollment(1);
    feedback.setRating(10);
    feedback.setComment("comment");

    return feedback;
  }

  public static Feedback getFeedbackMock() {
    Feedback feedback = new Feedback();
    feedback.setIdFeedback(1);
    feedback.setEnrollment(getEnrollmentMock());
    feedback.setRating(10);
    feedback.setComment("comment");
    feedback.setFeedbackDate(currentDate);

    return feedback;
  }

}