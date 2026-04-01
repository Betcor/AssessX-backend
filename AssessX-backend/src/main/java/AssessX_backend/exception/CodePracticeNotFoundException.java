package AssessX_backend.exception;

import org.springframework.http.HttpStatus;

public class CodePracticeNotFoundException extends AppException {

    public CodePracticeNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Code practice not found: " + id);
    }
}
