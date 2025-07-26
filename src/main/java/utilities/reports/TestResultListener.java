package utilities.reports;

import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.roopesh.appium.AppiumDriverManager;
import org.roopesh.config.Config;
import org.roopesh.customExceptions.PageElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.io.FileHandler;
import org.testng.*;
import org.testng.annotations.Test;
import utilities.annotations.MobileTest;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static utilities.emailConfig.Mailer.sendEmail;

public class TestResultListener implements ITestListener, ISuiteListener {
    private static final Map<ITestNGMethod, String> passedTests = new LinkedHashMap<>();
    private static final Map<ITestNGMethod, String> failedTests = new LinkedHashMap<>();
    private static final Map<ITestNGMethod, String> skippedTests = new LinkedHashMap<>();
    private final Logger log = LogManager.getLogger(TestResultListener.class);
    private final Map<String, TestCount> moduleTestCount = new LinkedHashMap<>();
    private final Map<String, AutomationSummary> automationSummary = new LinkedHashMap<>();
    private String title;
    private long startTime;

    private static List<Object> getAutomationSummaryCount(List<List<Object>> summaryCount) {
        int totalManualTestcases = 0;
        int totalAutomatableTestcases = 0;
        int totalAutomatedTestcases = 0;
        for (List<Object> summary : summaryCount) {
            totalManualTestcases += Integer.parseInt(String.valueOf(summary.get(1)));
            totalAutomatableTestcases += Integer.parseInt(String.valueOf(summary.get(2)));
            totalAutomatedTestcases += Integer.parseInt(String.valueOf(summary.get(3)));
        }
        List<Object> automationSummaryCount = new ArrayList<>();
        automationSummaryCount.add(totalManualTestcases);
        automationSummaryCount.add(totalAutomatableTestcases);
        automationSummaryCount.add(totalAutomatedTestcases);
        return automationSummaryCount;
    }

    @Override
    public void onTestStart(ITestResult result) {
        String moduleName = result.getMethod().getConstructorOrMethod().getDeclaringClass().getDeclaredAnnotation(MobileTest.class).moduleName();
        moduleTestCount.computeIfAbsent(moduleName, _ -> new TestCount());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests.put(result.getMethod(), result.getMethod().getConstructorOrMethod().getDeclaringClass().getDeclaredAnnotation(MobileTest.class).moduleName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = ExtentReportController.getTest();
        String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
        test.addScreenCaptureFromPath(screenshotPath);
        failedTests.put(result.getMethod(), result.getMethod().getConstructorOrMethod().getDeclaringClass().getDeclaredAnnotation(MobileTest.class).moduleName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests.put(result.getMethod(), result.getMethod().getConstructorOrMethod().getDeclaringClass().getDeclaredAnnotation(MobileTest.class).moduleName());
    }

    @Override
    public void onStart(ISuite context) {
        title = context.getName();
        moduleTestCount.clear();
        startTime = System.currentTimeMillis();
        log.info("Suite {} started...", context.getName());
    }


    @Override
    public void onFinish(ISuite context) {
        int totalPassed;
        int totalFailed;
        int totalSkipped;
        int totalTestsCount;
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        long seconds = (duration / 1000) % 60;
        long minutes = (duration / (1000 * 60)) % 60;
        long hours = (duration / (1000 * 60 * 60)) % 24;
        String testRunName = context.getParameter("testRunName");
        String executionTime = hours + " : " + minutes + " : " + seconds;

        List<String> passedTestcases = new ArrayList<>();
        for (Map.Entry<ITestNGMethod, String> entry : passedTests.entrySet()) {
            String testcaseName = entry.getKey().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Test.class).testName();
            passedTestcases.add(testcaseName);
            moduleTestCount.get(entry.getValue()).addPassed();
        }
        List<String> failedTestcases = new ArrayList<>();
        for (HashMap.Entry<ITestNGMethod, String> entry : failedTests.entrySet()) {
            String testcaseName = entry.getKey().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Test.class).testName();
            failedTestcases.add(testcaseName);
            moduleTestCount.get(entry.getValue()).addFailed();
        }
        List<String> skippedTestcases = new ArrayList<>();
        for (HashMap.Entry<ITestNGMethod, String> entry : skippedTests.entrySet()) {
            String testcaseName = entry.getKey().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Test.class).testName();
            if (!passedTestcases.contains(testcaseName) && !failedTestcases.contains(testcaseName)) {
                skippedTestcases.add(testcaseName);
                moduleTestCount.get(entry.getValue()).addSkipped();
            }
        }

        totalPassed = passedTestcases.size();
        totalFailed = failedTestcases.size();
        totalSkipped = skippedTestcases.size();
        totalTestsCount = totalPassed + totalFailed + totalSkipped;

        List<Object> moduleWiseTests = getModuleWiseTests();
        Result automationSummary = getAutomationSummary(context);

        if (Boolean.parseBoolean(Config.getConfigProperty(Config.ConfigProperty.IS_EMAIL_REPORT_ENABLED))) {
            log.info("Email Reporting is Enabled");
            sendEmail(title, testRunName, executionTime, totalTestsCount, totalPassed, totalFailed, totalSkipped, failedTestcases, skippedTestcases, moduleWiseTests, automationSummary.summaryCount(), automationSummary.automationSummaryCount());
        } else {
            log.info("Email Reporting is Disabled");
        }

    }

    private List<Object> getModuleWiseTests() {
        List<Object> moduleWiseTests = new ArrayList<>();
        for (Map.Entry<String, TestCount> entry : moduleTestCount.entrySet()) {
            List<Object> moduleData = new ArrayList<>();
            moduleData.add(entry.getKey());
            int total = entry.getValue().getPassedTests() + entry.getValue().getFailedTests() + entry.getValue().getSkippedTests();
            moduleData.add(total);
            moduleData.add(entry.getValue().getPassedTests());
            moduleData.add(entry.getValue().getFailedTests());
            moduleData.add(entry.getValue().getSkippedTests());
            moduleWiseTests.add(moduleData);
        }
        return moduleWiseTests;
    }

    private Result getAutomationSummary(ISuite context) {
        for (ITestNGMethod method : context.getAllMethods()) {
            Class<?> testClass = method.getRealClass();
            MobileTest mobileTest = testClass.getAnnotation(MobileTest.class);

            if (mobileTest != null) {
                String moduleName = mobileTest.moduleName();
                AutomationSummary summary = new AutomationSummary(mobileTest.manualTestcaseCount(), mobileTest.automatableTestcaseCount(), mobileTest.automatedCount());
                automationSummary.put(moduleName, summary);
            }
        }

        List<List<Object>> summaryCount = getSummaryCount();

        List<Object> automationSummaryCount = getAutomationSummaryCount(summaryCount);
        return new Result(summaryCount, automationSummaryCount);
    }

    private List<List<Object>> getSummaryCount() {
        List<List<Object>> summaryCount = new ArrayList<>();
        for (Map.Entry<String, AutomationSummary> summaryEntry : automationSummary.entrySet()) {
            List<Object> summaryData = new ArrayList<>();
            summaryData.add(summaryEntry.getKey());
            summaryData.add(summaryEntry.getValue().getManualTestcaseCount());
            summaryData.add(summaryEntry.getValue().getAutomatableTestcaseCount());
            summaryData.add(summaryEntry.getValue().getAutomatedCount());
            summaryCount.add(summaryData);
        }
        return summaryCount;
    }

    public String captureScreenshot(String testName) {
        String screenshotDir = "screenshots/";
        File screenshot = AppiumDriverManager.driver().getScreenshotAs(OutputType.FILE);

        File directory = new File(screenshotDir);
        if (!directory.exists()) {
            directory.mkdir();
        }

        String destinationName;
        try {
            destinationName = screenshotDir + testName + ".png";
            File destinationFile = new File(destinationName);
            FileHandler.copy(screenshot, destinationFile);
            System.out.println("Screenshot saved at: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            throw new PageElementException(e);
        }
        return destinationName;
    }

    private record Result(List<List<Object>> summaryCount, List<Object> automationSummaryCount) {
    }
}
