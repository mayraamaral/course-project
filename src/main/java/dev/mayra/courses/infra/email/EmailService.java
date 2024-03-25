package dev.mayra.courses.infra.email;

import dev.mayra.courses.entities.enrollment.Enrollment;
import dev.mayra.courses.entities.feedback.Feedback;
import dev.mayra.courses.entities.report.ReportNpsDTO;
import dev.mayra.courses.entities.user.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmailService {
  private final JavaMailSender mailSender;
  private String from = "Mayra Amaral <mayra@mayra.dev>";

  public void sendUserFeedbackToInstructor(Feedback feedback, Enrollment enrollment) {
    SimpleMailMessage email = new SimpleMailMessage();
    String instructorEmail = enrollment.getCourse().getInstructor().getEmail();
    String courseName = enrollment.getCourse().getName();

    email.setFrom(from);
    email.setTo(instructorEmail);
    email.setSubject("Your course \"" + courseName + "\" received a new feedback with rating lower than 6");

    String message = "Hello, " + enrollment.getCourse().getInstructor().getName() + "!\n\n" +
        "The user " + enrollment.getUser().getUsername() + " gave your course a new feedback.\n\n" +
        "Check the details: \n" +
        "\n" +
        "Course Code: " + enrollment.getCourse().getCode() + "\n" +
        "Course Name: " + enrollment.getCourse().getName() + "\n" +
        "Course Creation Date: " + enrollment.getCourse().getCreatedAt() + "\n" +
        "\n" +
        "Feedback Id: " + feedback.getIdFeedback() + "\n" +
        "Feedback Rating: " + feedback.getRating() + "\n" +
        "Feedback Comment: " + feedback.getComment() + "\n" +
        "\nIn case you want more details, you can check our application.\n" +
        "Best Regards,\n" +
        "Mayra Amaral.";

    email.setText(message);

    mailSender.send(email);
  }

  public void sendUserNpsCoursesReport(User userToSent, File report) throws MessagingException {
    MimeMessage email = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(email, true);

    helper.setFrom(from);
    helper.setTo(userToSent.getEmail());
    helper.setSubject("The Courses Nps Report You Requested Is Here!");

    String message = "Hello, " + userToSent.getName() + "\n" +
        "The report you requested is attached to this email.\n\n" +
        "Best Regards, \n" +
        "Mayra Amaral.";

    helper.setText(message);

    helper.addAttachment(report.getName(), report);

    mailSender.send(email);
  }

  public void sendUserFeedbacksCourseReport(User userToSent, File report) throws MessagingException {
    MimeMessage email = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(email, true);

    helper.setFrom(from);
    helper.setTo(userToSent.getEmail());
    helper.setSubject("The Feedbacks Report You Requested Is Here!");

    String message = "Hello, " + userToSent.getName() + "\n" +
        "The report you requested is attached to this email.\n\n" +
        "Best Regards, \n" +
        "Mayra Amaral.";

    helper.setText(message);

    helper.addAttachment(report.getName(), report);

    mailSender.send(email);
  }
}
