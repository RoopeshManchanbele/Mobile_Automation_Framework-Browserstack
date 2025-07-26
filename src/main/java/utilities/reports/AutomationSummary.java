package utilities.reports;


import lombok.Getter;

@Getter
public class AutomationSummary {
    private final int manualTestcaseCount;
    private final int automatableTestcaseCount;
    private final int automatedCount;

    public AutomationSummary(int manualTestcaseCount, int automatableTestcaseCount, int automatedCount) {
        this.manualTestcaseCount = manualTestcaseCount;
        this.automatableTestcaseCount = automatableTestcaseCount;
        this.automatedCount = automatedCount;
    }

}
