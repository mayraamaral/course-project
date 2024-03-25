package dev.mayra.courses.entities.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportNpsDTO {
  private String courseCode;
  private String courseName;
  private Double nps;
}
