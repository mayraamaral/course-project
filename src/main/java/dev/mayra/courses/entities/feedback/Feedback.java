package dev.mayra.courses.entities.feedback;

import dev.mayra.courses.entities.enrollment.Enrollment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "feedback_id")
  private Integer idFeedback;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "enrollment_id", referencedColumnName = "enrollment_id")
  private Enrollment enrollment;

  @Column(name = "rating")
  private Integer rating;

  @Column(name = "comment")
  private String comment;

  @Column(name = "feedback_date")
  private LocalDate feedbackDate;
}
