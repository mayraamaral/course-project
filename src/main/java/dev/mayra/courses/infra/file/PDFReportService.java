package dev.mayra.courses.infra.file;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import dev.mayra.courses.entities.feedback.FeedbackResponseDTO;
import dev.mayra.courses.entities.report.ReportNpsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PDFReportService {

  Font boldFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD);
  Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

  public File createNpsReport(List<ReportNpsDTO> npsList) throws IOException {
    String fileName = "src/main/resources/files/" + LocalDate.now() + "-nps-report.pdf";

    Document document = new Document();
    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
    PdfWriter pdfWriter = PdfWriter.getInstance(document, fileOutputStream);

    document.open();
    document.add(new Phrase("Courses NPS Report - " + LocalDate.now() + "\n\n", boldFont));

    for(ReportNpsDTO nps : npsList) {
      document.add(new Phrase("Course Name: ", boldFont));
      document.add(new Phrase(nps.getCourseName() + "\n", normalFont));

      document.add(new Phrase("Course Code: ", boldFont));
      document.add(new Phrase(nps.getCourseCode() + "\n", normalFont));

      document.add(new Phrase("Course NPS: ", boldFont));
      document.add(new Phrase(nps.getNps().toString() + "\n", normalFont));

      document.add(new Paragraph("\n"));
    }

    document.close();
    fileOutputStream.close();

    return new File(fileName);
  }

  public File createFeedbacksReport(List<FeedbackResponseDTO> feedbackList, Double courseNps) throws IOException {
    String fileName = "src/main/resources/files/" + LocalDate.now() + "-feedback-report.pdf";

    Document document = new Document();
    FileOutputStream fileOutputStream = new FileOutputStream(fileName);
    PdfWriter pdfWriter = PdfWriter.getInstance(document, fileOutputStream);

    document.open();
    document.add(new Phrase("Course Feedback Report - " + LocalDate.now() + "\n\n", boldFont));
    document.add(new Phrase("Course NPS: ", boldFont));
    document.add(new Phrase(courseNps.toString() + "\n\n"));
    document.add(new Phrase("Feedback list: \n\n"));

    for(FeedbackResponseDTO feedback : feedbackList) {
      document.add(new Phrase("Feedback Id: ", boldFont));
      document.add(new Phrase(feedback.getIdFeedback().toString() + "\n"));
      document.add(new Phrase("Enrollment Id: ", boldFont));
      document.add(new Phrase(feedback.getEnrollment().getIdEnrollment().toString() + "\n"));
      document.add(new Phrase("Course Code: ", boldFont));
      document.add(new Phrase(feedback.getEnrollment().getCourse().getCode() + "\n"));
      document.add(new Phrase("Course Instructor: ", boldFont));
      document.add(new Phrase(feedback.getEnrollment().getCourse().getInstructor().getUsername() + "\n"));
      document.add(new Phrase("Student: ", boldFont));
      document.add(new Phrase(feedback.getEnrollment().getUser().getUsername() + "\n"));
      document.add(new Phrase("Rating: ", boldFont));
      document.add(new Phrase(feedback.getRating().toString() + "\n"));
      document.add(new Phrase("Comment: ", boldFont));
      document.add(new Phrase(feedback.getComment() + "\n"));
      document.add(new Phrase("Feedback Date: ", boldFont));
      document.add(new Phrase(feedback.getFeedbackDate().toString() + "\n"));

      document.add(new Phrase("\n"));
    }

    document.close();
    fileOutputStream.close();

    return new File(fileName);
  }

}
