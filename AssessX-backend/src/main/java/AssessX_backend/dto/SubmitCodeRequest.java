package AssessX_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class SubmitCodeRequest {

    private Long assignmentId;

    @NotBlank
    private String code;

    public Long getAssignmentId() { return assignmentId; }
    public void setAssignmentId(Long assignmentId) { this.assignmentId = assignmentId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
}
