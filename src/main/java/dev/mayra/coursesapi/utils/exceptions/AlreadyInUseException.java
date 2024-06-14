package dev.mayra.coursesapi.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyInUseException extends ResponseStatusException {
    public AlreadyInUseException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
