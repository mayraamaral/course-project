package dev.mayra.courses.infra.controllers;

import dev.mayra.courses.app.services.ReportService;
import dev.mayra.courses.entities.report.ReportNpsDTO;
import dev.mayra.courses.infra.controllers.docs.ReportControllerDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

  @PostMapping("/nps/send-to-email")
  public ResponseEntity<String> sendCoursesNpsReport(HttpServletRequest request) throws MessagingException, IOException {
    return new ResponseEntity<>(reportService.sendCoursesNpsReport(request), HttpStatus.CREATED);
  }

  @PostMapping("/feedback/send-to-email/{courseCode}")
  public ResponseEntity<String> sendCourseFeedbacksReport(HttpServletRequest request, @PathVariable String courseCode) throws Exception {
    return new ResponseEntity<>(reportService.sendCourseFeedbacksReport(request, courseCode), HttpStatus.CREATED);
  }
}
