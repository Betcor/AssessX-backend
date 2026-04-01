package AssessX_backend.service;

import AssessX_backend.dto.CodePracticeResponseDto;
import AssessX_backend.dto.CodeSubmissionResultDto;
import AssessX_backend.dto.CreateCodePracticeRequest;
import AssessX_backend.dto.SubmitCodeRequest;
import AssessX_backend.exception.CodePracticeNotFoundException;
import AssessX_backend.exception.UserNotFoundException;
import AssessX_backend.model.CodePractice;
import AssessX_backend.model.PracticeUnitTest;
import AssessX_backend.model.User;
import AssessX_backend.repository.CodePracticeRepository;
import AssessX_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CodePracticeService {

    private final CodePracticeRepository practiceRepository;
    private final UserRepository userRepository;
    private final CodeExecutionService codeExecutionService;

    public CodePracticeService(CodePracticeRepository practiceRepository,
                               UserRepository userRepository,
                               CodeExecutionService codeExecutionService) {
        this.practiceRepository = practiceRepository;
        this.userRepository = userRepository;
        this.codeExecutionService = codeExecutionService;
    }

    @Transactional(readOnly = true)
    public List<CodePracticeResponseDto> getAllPractices() {
        return practiceRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(CodePracticeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CodePracticeResponseDto getPracticeById(Long id) {
        return CodePracticeResponseDto.from(findPracticeById(id));
    }

    @Transactional
    public CodePracticeResponseDto createPractice(CreateCodePracticeRequest request, Long userId) {
        User creator = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        CodePractice practice = new CodePractice();
        practice.setTitle(request.getTitle());
        practice.setDescription(request.getDescription());
        practice.setPoints(request.getPoints());
        practice.setTimeLimitSec(request.getTimeLimitSec());
        practice.setCreatedBy(creator);

        if (request.getUnitTests() != null) {
            for (String testCode : request.getUnitTests()) {
                PracticeUnitTest unitTest = new PracticeUnitTest();
                unitTest.setTestCode(testCode);
                unitTest.setPractice(practice);
                practice.getUnitTests().add(unitTest);
            }
        }

        return CodePracticeResponseDto.from(practiceRepository.save(practice));
    }

    @Transactional
    public CodePracticeResponseDto updatePractice(Long id, CreateCodePracticeRequest request) {
        CodePractice practice = findPracticeById(id);
        practice.setTitle(request.getTitle());
        practice.setDescription(request.getDescription());
        practice.setPoints(request.getPoints());
        practice.setTimeLimitSec(request.getTimeLimitSec());

        practice.getUnitTests().clear();
        if (request.getUnitTests() != null) {
            for (String testCode : request.getUnitTests()) {
                PracticeUnitTest unitTest = new PracticeUnitTest();
                unitTest.setTestCode(testCode);
                unitTest.setPractice(practice);
                practice.getUnitTests().add(unitTest);
            }
        }

        return CodePracticeResponseDto.from(practiceRepository.save(practice));
    }

    @Transactional
    public void deletePractice(Long id) {
        if (!practiceRepository.existsById(id)) {
            throw new CodePracticeNotFoundException(id);
        }
        practiceRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public CodeSubmissionResultDto submitPractice(Long id, SubmitCodeRequest request) {
        CodePractice practice = findPracticeById(id);
        List<String> unitTestCodes = practice.getUnitTests().stream()
                .map(PracticeUnitTest::getTestCode)
                .collect(Collectors.toList());
        return codeExecutionService.execute(request.getCode(), unitTestCodes, practice.getTimeLimitSec());
    }

    private CodePractice findPracticeById(Long id) {
        return practiceRepository.findById(id)
                .orElseThrow(() -> new CodePracticeNotFoundException(id));
    }
}
