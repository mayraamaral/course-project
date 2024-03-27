package dev.mayra.courses.app.services;

import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import dev.mayra.courses.entities.report.ReportNpsDTO;
import dev.mayra.courses.entities.user.User;
import dev.mayra.courses.infra.email.EmailService;
import dev.mayra.courses.infra.file.PDFReportService;
import dev.mayra.courses.infra.repositories.ReportRepository;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static dev.mayra.courses.app.services.UserServiceTest.getUserMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Report Service Tests")
class ReportServiceTest {

  @Mock
  private ReportRepository reportRepository;
  @Mock
  private CourseService courseService;
  @Mock
  private FeedbackService feedbackService;
  @Mock
  private PDFReportService pdfReportService;
  @Mock
  private EmailService emailService;
  @Mock
  private AuthService authService;
  @Mock
  private File report;
  @Mock
  private HttpServletRequest requestMock;

  @InjectMocks
  private ReportService reportService;

  @Test
  void testShouldSendCourseNpsWithSuccess() throws IOException, MessagingException {
    User userMock = getUserMock();

    when(authService.getLoggedUser(any())).thenReturn(userMock);

    List<ReportNpsDTO> list = Collections.singletonList(new ReportNpsDTO());

    when(reportRepository.listAllCoursesNps()).thenReturn(list);

    when(pdfReportService.createNpsReport(list)).thenReturn(report);

    String result = reportService.sendCoursesNpsReport(requestMock);

    verify(emailService).sendUserNpsCoursesReport(userMock, report);

    assertEquals("The report was sent to your email!", result);
  }

  @Test
  void testSendCourseFeedbacksReportSuccess() throws Exception {
    User userMock = getUserMock();

    when(authService.getLoggedUser(any())).thenReturn(userMock);

    when(courseService.doesCourseExists(any())).thenReturn(true);

    List<FeedbackResponseDTO> feedbackList = Collections.singletonList(new FeedbackResponseDTO());
    when(feedbackService.listAllFeedbacksByCourseCode(any())).thenReturn(feedbackList);

    Double courseNps = 8.5;
    when(reportRepository.findCourseNpsByCode(any())).thenReturn(courseNps);

    when(pdfReportService.createFeedbacksReport(any(), any())).thenReturn(report);

    String result = reportService.sendCourseFeedbacksReport(requestMock, "courseCode");

    verify(emailService).sendUserFeedbacksCourseReport(userMock, report);

    assertEquals("The report was sent to your email!", result);
  }

}