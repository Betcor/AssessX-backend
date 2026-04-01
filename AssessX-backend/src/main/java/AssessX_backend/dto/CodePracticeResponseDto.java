package AssessX_backend.dto;

import AssessX_backend.model.CodePractice;

import java.time.LocalDateTime;

public class CodePracticeResponseDto {

    private Long id;
    private String title;
    private String description;
    private int unitTestCount;
    private Integer points;
    private Integer timeLimitSec;
    private Long createdById;
    private LocalDateTime createdAt;

    public static CodePracticeResponseDto from(CodePractice practice) {
        CodePracticeResponseDto dto = new CodePracticeResponseDto();
        dto.id = practice.getId();
        dto.title = practice.getTitle();
        dto.description = practice.getDescription();
        dto.unitTestCount = practice.getUnitTests().size();
        dto.points = practice.getPoints();
        dto.timeLimitSec = practice.getTimeLimitSec();
        dto.createdById = practice.getCreatedBy() != null ? practice.getCreatedBy().getId() : null;
        dto.createdAt = practice.getCreatedAt();
        return dto;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getUnitTestCount() { return unitTestCount; }
    public Integer getPoints() { return points; }
    public Integer getTimeLimitSec() { return timeLimitSec; }
    public Long getCreatedById() { return createdById; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
