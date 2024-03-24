package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.report.ReportNpsDTO;
import dev.mayra.courses.infra.repositories.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
  private final ReportRepository reportRepository;
  private final CourseService courseService;

  public List<ReportNpsDTO> listAllCoursesNps() {
    return reportRepository.listAllCoursesNps();
  }

  public ReportNpsDTO listCourseNpsByCode(String courseCode) {

    courseService.findByCode(courseCode);

    return reportRepository.listCourseNpsByCode(courseCode);
  }

}
