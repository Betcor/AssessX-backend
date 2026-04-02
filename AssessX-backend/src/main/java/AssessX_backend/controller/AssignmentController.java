package AssessX_backend.controller;

import AssessX_backend.dto.AssignmentResponseDto;
import AssessX_backend.dto.CreateAssignmentRequest;
import AssessX_backend.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<AssignmentResponseDto>> getAllAssignments() {
        return ResponseEntity.ok(assignmentService.getAllAssignments());
    }

    @GetMapping("/my")
    public ResponseEntity<List<AssignmentResponseDto>> getMyAssignments(
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        return ResponseEntity.ok(assignmentService.getMyAssignments(userId));
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<AssignmentResponseDto> createAssignment(
            @Valid @RequestBody CreateAssignmentRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(assignmentService.createAssignment(request, userId));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
}
