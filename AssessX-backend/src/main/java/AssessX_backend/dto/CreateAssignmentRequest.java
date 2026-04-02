package AssessX_backend.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateAssignmentRequest {

    @NotNull
    private Long groupId;

    private Long testId;

    private Long practiceId;

    private LocalDateTime deadline;

    public Long getGroupId() { return groupId; }
    public void setGroupId(Long groupId) { this.groupId = groupId; }

    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }

    public Long getPracticeId() { return practiceId; }
    public void setPracticeId(Long practiceId) { this.practiceId = practiceId; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
}
