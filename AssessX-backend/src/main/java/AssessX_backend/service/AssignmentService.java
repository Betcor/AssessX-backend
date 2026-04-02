package AssessX_backend.service;

import AssessX_backend.dto.AssignmentResponseDto;
import AssessX_backend.dto.CreateAssignmentRequest;
import AssessX_backend.exception.AssignmentNotFoundException;
import AssessX_backend.exception.CodePracticeNotFoundException;
import AssessX_backend.exception.GroupNotFoundException;
import AssessX_backend.exception.InvalidAssignmentException;
import AssessX_backend.exception.TestNotFoundException;
import AssessX_backend.exception.UserNotFoundException;
import AssessX_backend.model.Assignment;
import AssessX_backend.model.CodePractice;
import AssessX_backend.model.Group;
import AssessX_backend.model.Test;
import AssessX_backend.model.User;
import AssessX_backend.repository.AssignmentRepository;
import AssessX_backend.repository.CodePracticeRepository;
import AssessX_backend.repository.GroupRepository;
import AssessX_backend.repository.TestRepository;
import AssessX_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final GroupRepository groupRepository;
    private final TestRepository testRepository;
    private final CodePracticeRepository practiceRepository;
    private final UserRepository userRepository;

    public AssignmentService(AssignmentRepository assignmentRepository,
                             GroupRepository groupRepository,
                             TestRepository testRepository,
                             CodePracticeRepository practiceRepository,
                             UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.groupRepository = groupRepository;
        this.testRepository = testRepository;
        this.practiceRepository = practiceRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<AssignmentResponseDto> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(AssignmentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AssignmentResponseDto> getMyAssignments(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        Set<Long> groupIds = user.getGroups().stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
        if (groupIds.isEmpty()) {
            return List.of();
        }
        return assignmentRepository.findByGroupIdIn(groupIds).stream()
                .map(AssignmentResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public AssignmentResponseDto createAssignment(CreateAssignmentRequest request, Long userId) {
        boolean hasTest = request.getTestId() != null;
        boolean hasPractice = request.getPracticeId() != null;
        if (hasTest == hasPractice) {
            throw new InvalidAssignmentException("Exactly one of testId or practiceId must be provided");
        }

        Group group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException(request.getGroupId()));
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Assignment assignment = new Assignment();
        assignment.setGroup(group);
        assignment.setCreatedBy(creator);
        assignment.setDeadline(request.getDeadline());

        if (hasTest) {
            Test test = testRepository.findById(request.getTestId())
                    .orElseThrow(() -> new TestNotFoundException(request.getTestId()));
            assignment.setTest(test);
        } else {
            CodePractice practice = practiceRepository.findById(request.getPracticeId())
                    .orElseThrow(() -> new CodePracticeNotFoundException(request.getPracticeId()));
            assignment.setPractice(practice);
        }

        return AssignmentResponseDto.from(assignmentRepository.save(assignment));
    }

    @Transactional
    public void deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new AssignmentNotFoundException(id);
        }
        assignmentRepository.deleteById(id);
    }
}
