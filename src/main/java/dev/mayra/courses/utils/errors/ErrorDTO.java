package dev.mayra.courses.utils.errors;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ErrorDTO {
  private List<String> errors;
  private String message;
  private Date timestamp;
  private int status;
}
