package utilities.reports;

import lombok.Getter;

@Getter
public class TestCount {
    private final int totalTests;
    private int passedTests;
    private int failedTests;
    private int skippedTests;

    public TestCount() {
        this.totalTests = 0;
        this.passedTests = 0;
        this.failedTests = 0;
        this.skippedTests = 0;
    }

    public void addPassed() {
        passedTests++;
    }

    public void addFailed() {
        failedTests++;
    }

    public void addSkipped() {
        skippedTests++;
    }


}