package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.enrollment.Enrollment;
import dev.mayra.courses.entities.enrollment.EnrollmentResponseDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.entities.user.UserDTO;
import dev.mayra.courses.infra.repositories.EnrollmentRepository;
import dev.mayra.courses.utils.Mapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static dev.mayra.courses.app.services.CourseServiceTest.getCourseActiveMock;
import static dev.mayra.courses.app.services.CourseServiceTest.getCourseActiveResponseDTOMock;
import static dev.mayra.courses.app.services.UserServiceTest.getUserMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Enrollment Service Tests")
class EnrollmentServiceTest {
  @Mock
  private EnrollmentRepository enrollmentRepository;
  @Mock
  private AuthService authService;
  @Mock
  private CourseService courseService;
  @Mock
  private Mapper mapper;
  @Mock
  private HttpServletRequest requestMock;

  @InjectMocks
  private EnrollmentService enrollmentService;

  private static LocalDate currentDate = LocalDate.now();

  private String courseCode = "COURSE_CODE";

  @Test
  void testShouldCreateWithSuccess() throws Exception {
    String authorizationHeader = "Bearer TOKEN";
    User user = getUserMock();
    Course course = getCourseActiveMock();
    Enrollment enrollment = getEnrollmentMock();

    when(requestMock.getHeader("Authorization")).thenReturn(authorizationHeader);
    when(authService.getLoggedUser(authorizationHeader)).thenReturn(user);
    when(courseService.findByCode(courseCode)).thenReturn(course);
    when(enrollmentRepository.save(any(Enrollment.class))).thenReturn(enrollment);

    EnrollmentResponseDTO enrollmentResponseMock = getEnrollmentResponseDTOMock();
    when(mapper.convertToDTO(enrollment, EnrollmentResponseDTO.class))
        .thenReturn(enrollmentResponseMock);

    EnrollmentResponseDTO responseDTO = enrollmentService.create(requestMock, courseCode);

    assertNotNull(responseDTO);
    assertEquals(enrollmentResponseMock, responseDTO);
  }

  @Test
  void testShouldListAllWithSuccess() {
    List<Enrollment> enrollmentsListMock = getEnrollmentListMock();
    List<EnrollmentResponseDTO> enrrolmentsDTOListMock = getEnrollmentResponseListDTOMock();

    when(enrollmentRepository.findAll()).thenReturn(enrollmentsListMock);
    when(mapper.convertToDTO(any(Enrollment.class), eq(EnrollmentResponseDTO.class)))
        .thenReturn(enrrolmentsDTOListMock.get(0), enrrolmentsDTOListMock.get(1),
            enrrolmentsDTOListMock.get(2));

    List<EnrollmentResponseDTO> responseList = enrollmentService.listAllEnrollments();

    assertEquals(enrrolmentsDTOListMock, responseList);
  }

  @Test
  void testShouldListAllByCourseCodeWithSuccess() throws Exception {
    List<Enrollment> enrollmentsListMock = getEnrollmentListMock();
    List<EnrollmentResponseDTO> enrrolmentsDTOListMock = getEnrollmentResponseListDTOMock();

    Course courseMock = getCourseActiveMock();

    when(courseService.findByCode(any())).thenReturn(courseMock);
    when(enrollmentRepository.findAllByCourse(courseMock)).thenReturn(enrollmentsListMock);

    when(mapper.convertToDTO(any(Enrollment.class), eq(EnrollmentResponseDTO.class)))
        .thenReturn(enrrolmentsDTOListMock.get(0),enrrolmentsDTOListMock.get(1),
            enrrolmentsDTOListMock.get(2));

    List<EnrollmentResponseDTO> responseList = enrollmentService.listAllByCourseCode(any());

    assertEquals(enrrolmentsDTOListMock, responseList);
  }

  private static List<EnrollmentResponseDTO> getEnrollmentResponseListDTOMock() {
    return List.of(getEnrollmentResponseDTOMock(), getEnrollmentResponseDTOMock(),
        getEnrollmentResponseDTOMock());
  }

  private static List<Enrollment> getEnrollmentListMock() {
    return List.of(getEnrollmentMock(), getEnrollmentMock(), getEnrollmentMock());
  }

  public static Enrollment getEnrollmentMock() {
    User userMock = getUserMock();
    Course courseMock = getCourseActiveMock();

    Enrollment enrollment = new Enrollment();
    enrollment.setIdEnrollment(1);
    enrollment.setUser(userMock);
    enrollment.setCourse(courseMock);
    enrollment.setEnrolledAt(currentDate);

    return enrollment;
  }

  public static EnrollmentResponseDTO getEnrollmentResponseDTOMock() {
    CourseResponseDTO courseMock = getCourseActiveResponseDTOMock();
    UserDTO userMock = new UserDTO();
    userMock.setIdUser(1);
    userMock.setName("User");
    userMock.setUsername("user");

    EnrollmentResponseDTO enrollment = new EnrollmentResponseDTO();
    enrollment.setIdEnrollment(1);
    enrollment.setCourse(courseMock);
    enrollment.setUser(userMock);
    enrollment.setEnrolledAt(currentDate);

    return enrollment;
  }
}