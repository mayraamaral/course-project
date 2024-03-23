package dev.mayra.courses.entities.feedback;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Valid
public class FeedbackCreateDTO {

  @NotNull(message = "idEnrollment can't be null")
  private Integer idEnrollment;

  @NotNull(message = "Feedback rating can't be null")
  @Range(min = 0, max = 10, message = "Feedback rating must be between 0 and 10")
  private Integer rating;

  @NotBlank(message = "Feedback comment can't be blank")
  private String comment;

}
