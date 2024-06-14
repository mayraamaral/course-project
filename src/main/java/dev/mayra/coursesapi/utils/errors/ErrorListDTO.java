package dev.mayra.coursesapi.utils.errors;

import java.util.Date;
import java.util.List;

public class ErrorListDTO {
    private Date timestamp;
    private int status;
    private List<String> errors;
    private String message;
    private String path;

    public ErrorListDTO(Date timestamp, int status, List<String> errors, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
        this.message = message;
        this.path = path;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }
}