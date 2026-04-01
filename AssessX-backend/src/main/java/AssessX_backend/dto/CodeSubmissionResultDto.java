package AssessX_backend.dto;

public class CodeSubmissionResultDto {

    private final int passedTests;
    private final int totalTests;
    private final String output;

    public CodeSubmissionResultDto(int passedTests, int totalTests, String output) {
        this.passedTests = passedTests;
        this.totalTests = totalTests;
        this.output = output;
    }

    public int getPassedTests() { return passedTests; }
    public int getTotalTests() { return totalTests; }
    public String getOutput() { return output; }
}
