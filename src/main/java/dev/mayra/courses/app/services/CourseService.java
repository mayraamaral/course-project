package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.course.CourseCreateDTO;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.entities.course.CourseStatus;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.repositories.CourseRepository;
import dev.mayra.courses.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
  private final CourseRepository courseRepository;
  private final UserService userService;
  private final Mapper mapper;

  public List<CourseResponseDTO> listAllCourses() {
    List<CourseResponseDTO> courses = courseRepository.findAllDto();
    return courses;
  }

  public Page<List<CourseResponseDTO>> listAllOrByStatus(String status, Pageable pageable) {
      try {
        CourseStatus statusEnum = null;

        if(status != null) {
          statusEnum = CourseStatus.valueOf(status.toUpperCase());
        }

        Page<List<CourseResponseDTO>> courses = courseRepository.findAllCoursesOrByStatus(statusEnum, pageable);

        return courses;
      } catch (IllegalArgumentException ex) {
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
      }
  }

  public List<CourseResponseDTO> listAllByInstructorUsername(String username) throws Exception {

    User user = userService.findUserByUsername(username);

    if(!userService.isUserAnInstructor(user.getIdUser())) {
      throw new Exception("Username must be from an instructor");
    }

    List<Course> coursesFound = courseRepository.findAllByInstructorUsername(username);

    List<CourseResponseDTO> coursesDto = coursesFound.stream()
        .map(course -> mapper.convertToDTO(course, CourseResponseDTO.class))
        .collect(Collectors.toList());

    return coursesDto;
  }

  public CourseResponseDTO listByCode(String courseCode) {
    Course course = findByCode(courseCode);

    return mapper.convertToDTO(course, CourseResponseDTO.class);
  }

  public CourseResponseDTO create(CourseCreateDTO course) throws Exception {
    User user = userService.findUserByUsername(course.getInstructorUsername());

    Integer idUser = user.getIdUser();

    checkIfCourseExistsByCode(course.getCode());

    if(!userService.isUserAnInstructor(idUser)) {
      throw new Exception("Username must be from an instructor");
    }

    Course courseToBeCreated = mapper.convertToEntity(course, Course.class);
    courseToBeCreated.setInstructor(user);
    courseToBeCreated.setCreatedAt(LocalDate.now());
    courseToBeCreated.setStatus(getDefaultCourseStatus());

    Course courseCreated = courseRepository.save(courseToBeCreated);

    return mapper.convertToDTO(courseCreated, CourseResponseDTO.class);
  }

  public CourseResponseDTO inactivate(String courseCode) throws Exception {

    checkIfCourseIsInactive(courseCode);

    Course course = findByCode(courseCode);

    course.setInactivatedAt(LocalDate.now());
    course.setStatus(CourseStatus.INACTIVE);

    Course courseInactivated = courseRepository.save(course);

    return mapper.convertToDTO(courseInactivated, CourseResponseDTO.class);
  }

  public Course findByCode(String courseCode) throws NotFoundException {
    Optional<Course> courseOpt = courseRepository.findByCode(courseCode);

    if(courseOpt.isEmpty()) {
      throw new NotFoundException("Code not found");
    }

    return courseOpt.get();
  }

  public boolean isTheGivenCourseInactive(Course course) throws Exception {
    return course.getStatus().equals(CourseStatus.INACTIVE);
  }

  public void checkIfCourseIsInactive(String courseCode) throws Exception {
    Course course = findByCode(courseCode);

    if(course.getStatus().equals(CourseStatus.INACTIVE)) {
      throw new Exception("Course is already inactive");
    }
  }

  public boolean doesCourseExists(String courseCode) {
    return courseRepository.findByCode(courseCode).isPresent();
  }

  public void checkIfCourseExistsByCode(String courseCode) throws Exception {
    Optional<Course> courseOpt = courseRepository.findByCode(courseCode);
    if(courseOpt.isPresent()) {
      throw new Exception("The code you provided already is associated with a course");
    }
  }

  public CourseStatus getDefaultCourseStatus() {
    return CourseStatus.ACTIVE;
  }
}
