package dev.mayra.courses.entities.course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
  private Integer idCourse;
  private String name;
  private String code;
  private String instructor;
  private String description;
  private CourseStatus status;
  private LocalDate createdAt;
  private LocalDate inactivatedAt;
}