package dev.mayra.courses.entities.feedback;

import dev.mayra.courses.entities.enrollment.EnrollmentMinifiedDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackResponseDTO {
  private Integer idFeedback;
  private EnrollmentMinifiedDTO enrollment;
  private Integer rating;
  private String comment;
  private LocalDate feedbackDate;
}
