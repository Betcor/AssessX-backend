package AssessX_backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidAssignmentException extends AppException {

    public InvalidAssignmentException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
