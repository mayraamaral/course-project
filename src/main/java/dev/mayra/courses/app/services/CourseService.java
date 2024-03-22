package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.course.Course;
import dev.mayra.courses.entities.course.CourseResponseDTO;
import dev.mayra.courses.infra.repositories.CourseRepository;
import dev.mayra.courses.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
  private final CourseRepository courseRepository;
  private final Mapper mapper;

  public List<CourseResponseDTO> listAllCourses() {
    List<CourseResponseDTO> courses = courseRepository.findAllDto();
    return courses;
  }
}
