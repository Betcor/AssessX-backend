package AssessX_backend.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class SubmitTestRequest {

    private Long assignmentId;

    @NotNull
    private Map<String, String> answers;

    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }

    public Map<String, String> getAnswers() { return answers; }
    public void setAnswers(Map<String, String> answers) { this.answers = answers; }
}
