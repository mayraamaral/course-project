package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.app.services.ReportService;
import dev.mayra.courses.entities.report.ReportNpsDTO;
import dev.mayra.courses.infra.controllers.docs.ReportControllerDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
@Validated
@RequiredArgsConstructor
@Tag(name = "Report", description = "Operations related to Reports")
public class ReportController implements ReportControllerDoc {
  private final ReportService reportService;

  @GetMapping("/nps")
  public ResponseEntity<List<ReportNpsDTO>> listAllCoursesNps() {
    return new ResponseEntity<>(reportService.listAllCoursesNps(), HttpStatus.OK);
  }

  @GetMapping("/nps/{courseCode}")
  public ResponseEntity<ReportNpsDTO> listCourseNpsByCode(@PathVariable String courseCode) {
    return new ResponseEntity<>(reportService.listCourseNpsByCode(courseCode), HttpStatus.OK);
  }
}
