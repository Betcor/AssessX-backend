package AssessX_backend.dto;

import AssessX_backend.model.Assignment;
import java.time.LocalDateTime;

public class AssignmentResponseDto {

    private Long id;
    private Long groupId;
    private Long testId;
    private Long practiceId;
    private LocalDateTime deadline;
    private Long createdById;
    private LocalDateTime createdAt;

    public static AssignmentResponseDto from(Assignment a) {
        AssignmentResponseDto dto = new AssignmentResponseDto();
        dto.id = a.getId();
        dto.groupId = a.getGroup() != null ? a.getGroup().getId() : null;
        dto.testId = a.getTest() != null ? a.getTest().getId() : null;
        dto.practiceId = a.getPractice() != null ? a.getPractice().getId() : null;
        dto.deadline = a.getDeadline();
        dto.createdById = a.getCreatedBy() != null ? a.getCreatedBy().getId() : null;
        dto.createdAt = a.getCreatedAt();
        return dto;
    }

    public Long getId() { return id; }
    public Long getGroupId() { return groupId; }
    public Long getTestId() { return testId; }
    public Long getPracticeId() { return practiceId; }
    public LocalDateTime getDeadline() { return deadline; }
    public Long getCreatedById() { return createdById; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
