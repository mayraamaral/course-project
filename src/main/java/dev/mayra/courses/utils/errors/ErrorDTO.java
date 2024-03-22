package dev.mayra.courses.utils.errors;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDTO {

  private String error;
  private String message;
  private Date timestamp;
  private int status;
}
