package AssessX_backend.controller;

import AssessX_backend.dto.CodePracticeResponseDto;
import AssessX_backend.dto.CodeSubmissionResultDto;
import AssessX_backend.dto.CreateCodePracticeRequest;
import AssessX_backend.dto.SubmitCodeRequest;
import AssessX_backend.service.CodePracticeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/practices")
public class CodePracticeController {

    private final CodePracticeService practiceService;

    public CodePracticeController(CodePracticeService practiceService) {
        this.practiceService = practiceService;
    }

    @GetMapping
    public ResponseEntity<List<CodePracticeResponseDto>> getAllPractices() {
        return ResponseEntity.ok(practiceService.getAllPractices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CodePracticeResponseDto> getPracticeById(@PathVariable Long id) {
        return ResponseEntity.ok(practiceService.getPracticeById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CodePracticeResponseDto> createPractice(
            @Valid @RequestBody CreateCodePracticeRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        return ResponseEntity.status(HttpStatus.CREATED).body(practiceService.createPractice(request, userId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CodePracticeResponseDto> updatePractice(
            @PathVariable Long id,
            @Valid @RequestBody CreateCodePracticeRequest request) {
        return ResponseEntity.ok(practiceService.updatePractice(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> deletePractice(@PathVariable Long id) {
        practiceService.deletePractice(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<CodeSubmissionResultDto> submitPractice(
            @PathVariable Long id,
            @Valid @RequestBody SubmitCodeRequest request) {
        return ResponseEntity.ok(practiceService.submitPractice(id, request));
    }
}
