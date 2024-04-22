package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.course.CourseCreateDTO;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.course.CourseStatus;
import dev.mayra.courses.entities.role.Role;
import dev.mayra.courses.entities.user.InstructorDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.CourseRepository;
import dev.mayra.courses.utils.Mapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Course Service Tests")
class CourseServiceTest {
  @Mock
  private CourseRepository courseRepository;

  @Mock
  private UserService userService;

  @Mock
  private Mapper mapper;

  @Spy
  @InjectMocks
  private CourseService courseService;

  private static LocalDate currentDate = LocalDate.now();

  @Test
  void testShouldListCoursesWithSuccess() {
    List<CourseResponseDTO> coursesList = getCoursesActivesDTOListMock();

    when(courseRepository.findAllDto()).thenReturn(coursesList);

    List<CourseResponseDTO> responseList = courseService.listAllCourses();

    assertEquals(coursesList, responseList);
  }

  @Test
  void testShouldListAllActiveByStatusWithSuccess() {

    String status = "ACTIVE";
    Pageable pageable = Pageable.unpaged();

    List<CourseResponseDTO> courseList = getCoursesActivesDTOListMock();
    Page<List<CourseResponseDTO>> mockedPage = new PageImpl<>(Collections.singletonList(courseList), pageable, 1);
    when(courseRepository.findAllCoursesOrByStatus(any(), any())).thenReturn(mockedPage);

    Page<List<CourseResponseDTO>> result = courseService.listAllOrByStatus(status, pageable);

    assertEquals(mockedPage, result);
  }

  @Test
  void testShouldListAllInactiveByStatusWithSuccess() {

    String status = "INACTIVE";
    Pageable pageable = Pageable.unpaged();

    List<CourseResponseDTO> courseList = getCoursesInactivesDTOListMock();
    Page<List<CourseResponseDTO>> mockedPage = new PageImpl<>(Collections.singletonList(courseList), pageable, 1);
    when(courseRepository.findAllCoursesOrByStatus(any(), any())).thenReturn(mockedPage);

    Page<List<CourseResponseDTO>> result = courseService.listAllOrByStatus(status, pageable);

    assertEquals(mockedPage, result);
  }

  @Test
  void testShouldReturnEmptyPageWithInvalidParameter() {

    String status = UUID.randomUUID().toString();
    Pageable pageable = Pageable.unpaged();

    Page<List<CourseResponseDTO>> result = courseService.listAllOrByStatus(status, pageable);

    assertEquals(0, result.getTotalElements());
  }

  @Test
  void testShouldListAllFromInstructorUsernameWithSuccess() throws Exception {

    User instructorMock = getInstructorUserMock();

    when(userService.findUserByUsername(any())).thenReturn(instructorMock);

    when(userService.isUserAnInstructor(any())).thenReturn(true);

    List<Course> coursesList = getCoursesActivesListMock();

    when(courseRepository.findAllByInstructorUsername(any())).thenReturn(coursesList);

    List<CourseResponseDTO> mockList = getCoursesActivesDTOListMock();

    when(mapper.convertToDTO(any(Course.class), eq(CourseResponseDTO.class))).thenReturn(mockList.get(0), mockList.get(1), mockList.get(2));

    List<CourseResponseDTO> responseList = courseService.listAllByInstructorUsername(any());

    assertEquals(mockList, responseList);
  }

  @Test
  void testShouldThrowExceptionWithInvalidUser() throws Exception {

    User adminMock = getAdminUserMock();

    when(userService.findUserByUsername(any())).thenReturn(adminMock);

    when(userService.isUserAnInstructor(any())).thenReturn(false);

    assertThrows(Exception.class, () -> courseService.listAllByInstructorUsername(any()));

  }

  @Test
  void testShouldListByCodeWithSuccess() {
    Course courseMock = getCourseActiveMock();

    doReturn(courseMock).when(courseService).findByCode(any());

    CourseResponseDTO courseResponseMock = getCourseActiveResponseDTOMock();

    when(mapper.convertToDTO(any(Course.class), eq(CourseResponseDTO.class))).thenReturn(courseResponseMock);

    CourseResponseDTO responseCourse = courseService.listByCode(any());

    assertEquals(courseResponseMock, responseCourse);
  }

  @Test
  void testShouldCreateWithSuccess() throws Exception {
    User userMock = getInstructorUserMock();
    CourseCreateDTO courseCreateMock = getCourseCreateDTOMock();

    when(userService.findUserByUsername(any())).thenReturn(userMock);
    when(userService.isUserAnInstructor(any())).thenReturn(true);

    Course courseMock = getCourseActiveMock();
    CourseResponseDTO courseResponseMock = getCourseActiveResponseDTOMock();

    when(mapper.convertToEntity(any(CourseCreateDTO.class), eq(Course.class))).thenReturn(courseMock);

    when(courseRepository.save(any())).thenReturn(courseMock);

    when(mapper.convertToDTO(any(Course.class), eq(CourseResponseDTO.class))).thenReturn(courseResponseMock);

    CourseResponseDTO responseCourse = courseService.create(courseCreateMock);

    assertEquals(courseResponseMock, responseCourse);
  }

  @Test
  void testShouldThrowExceptionWithUserNotBeingInstructor() throws Exception {
    User userMock = getAdminUserMock();
    CourseCreateDTO courseCreateMock = getCourseCreateDTOMock();

    when(userService.findUserByUsername(any())).thenReturn(userMock);
    when(userService.isUserAnInstructor(any())).thenReturn(false);

    assertThrows(Exception.class, () -> courseService.create(courseCreateMock));
  }

  @Test
  void testShouldInactivateCourseWithSuccess() throws Exception {
    Course courseMock = getCourseActiveMock();

    doReturn(courseMock).when(courseService).findByCode(any());

    Course courseInactiveMock = getCourseInactiveMock();

    when(courseRepository.save(any())).thenReturn(courseInactiveMock);

    CourseResponseDTO courseInactiveResponseMock = getCourseInactiveResponseDTOMock();

    when(mapper.convertToDTO(any(Course.class), eq(CourseResponseDTO.class))).thenReturn(courseInactiveResponseMock);

    CourseResponseDTO responseCourse = courseService.inactivate(any());

    assertEquals(courseInactiveResponseMock, responseCourse);
  }

  public static List<CourseResponseDTO> getCoursesActivesDTOListMock() {
    return List.of(getCourseActiveResponseDTOMock(), getCourseActiveResponseDTOMock(), getCourseActiveResponseDTOMock());
  }

  public static List<CourseResponseDTO> getCoursesInactivesDTOListMock() {
    return List.of(getCourseActiveResponseDTOMock(), getCourseActiveResponseDTOMock(), getCourseActiveResponseDTOMock());
  }

  public static List<Course> getCoursesActivesListMock() {
    return List.of(getCourseActiveMock(), getCourseActiveMock(), getCourseActiveMock());
  }

  public static Course getCourseActiveMock() {
    Course course = new Course();
    course.setName("Course");
    course.setCode("course");
    course.setInstructor(getInstructorUserMock());
    course.setDescription("Course description");
    course.setStatus(CourseStatus.ACTIVE);
    course.setCreatedAt(currentDate);

    return course;
  }

  public static Course getCourseInactiveMock() {
    Course course = new Course();
    course.setName("Course");
    course.setCode("course");
    course.setInstructor(getInstructorUserMock());
    course.setDescription("Course description");
    course.setStatus(CourseStatus.INACTIVE);
    course.setCreatedAt(currentDate);
    course.setInactivatedAt(currentDate);

    return course;
  }

  public static CourseCreateDTO getCourseCreateDTOMock() {
    CourseCreateDTO course = new CourseCreateDTO();
    course.setName("Course");
    course.setCode("course");
    course.setDescription("Course description");
    course.setInstructorUsername("instructor");

    return course;
  }

  public static CourseResponseDTO getCourseActiveResponseDTOMock() {
    CourseResponseDTO course = new CourseResponseDTO();
    course.setName("Course");
    course.setCode("course");
    course.setInstructor(getInstructorDTOMock());
    course.setDescription("Course description");
    course.setStatus(CourseStatus.ACTIVE);
    course.setCreatedAt(currentDate);

    return course;
  }

  public static CourseResponseDTO getCourseInactiveResponseDTOMock() {
    CourseResponseDTO course = new CourseResponseDTO();
    course.setName("Course");
    course.setCode("course");
    course.setInstructor(getInstructorDTOMock());
    course.setDescription("Course description");
    course.setStatus(CourseStatus.INACTIVE);
    course.setCreatedAt(currentDate);
    course.setInactivatedAt(currentDate);

    return course;
  }

  public static User getInstructorUserMock() {
    User user = new User();

    return user;
  }

  public static User getAdminUserMock() {
    User user = new User();

    return user;
  }

  public static Role getAdminRoleMock() {
    Role role = new Role();
    role.setIdRole(1);
    role.setName("ROLE_ADMIN");

    return role;
  }

  public static Role getInstructorRoleMock() {
    Role role = new Role();
    role.setIdRole(1);
    role.setName("ROLE_INSTRUCTOR");

    return role;
  }

  public static InstructorDTO getInstructorDTOMock() {
    InstructorDTO instructor = new InstructorDTO();
    instructor.setIdUser(2);
    instructor.setName("Instructor");
    instructor.setUsername("instructor");

    return instructor;
  }
}