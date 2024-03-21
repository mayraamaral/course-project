package dev.mayra.courses.utils;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorHandlerDTO {

  private String error;
  private String message;
  private Date timestamp;
  private int status;
}
