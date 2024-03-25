package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import dev.mayra.courses.entities.report.ReportNpsDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.email.EmailService;
import dev.mayra.courses.infra.file.PDFReportService;
import dev.mayra.courses.infra.repositories.ReportRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
  private final ReportRepository reportRepository;
  private final CourseService courseService;
  private final FeedbackService feedbackService;
  private final PDFReportService pdfReportService;
  private final EmailService emailService;
  private final AuthService authService;

  public List<ReportNpsDTO> listAllCoursesNps() {
    return reportRepository.listAllCoursesNps();
  }

  public ReportNpsDTO listCourseNpsByCode(String courseCode) {

    courseService.findByCode(courseCode);

    return reportRepository.listCourseNpsByCode(courseCode);
  }

  public String sendCoursesNpsReport(HttpServletRequest request) throws IOException, MessagingException {
    String token = request.getHeader("Authorization");

    User user = authService.getLoggedUser(token);

    List<ReportNpsDTO> list = listAllCoursesNps();

    File report = pdfReportService.createNpsReport(list);

    emailService.sendUserNpsCoursesReport(user, report);

    return "The report was sent to your email!";
  }

  public String sendCourseFeedbacksReport(HttpServletRequest request, String courseCode) throws Exception {
    String token = request.getHeader("Authorization");

    User user = authService.getLoggedUser(token);

    if(!courseService.doesCourseExists(courseCode)) {
      throw new NotFoundException("Invalid course code");
    }

    List<FeedbackResponseDTO> feedbackList = feedbackService.listAllFeedbacksByCourseCode(courseCode);
    Double courseNps = reportRepository.findCourseNpsByCode(courseCode);

    File report = pdfReportService.createFeedbacksReport(feedbackList, courseNps);

    emailService.sendUserFeedbacksCourseReport(user, report);

    return "The report was sent to your email!";
  }
}
