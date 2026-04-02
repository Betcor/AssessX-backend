package AssessX_backend.exception;

import org.springframework.http.HttpStatus;

public class AssignmentNotFoundException extends AppException {

    public AssignmentNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Assignment not found: " + id);
    }
}
