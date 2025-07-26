package utilities.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.roopesh.constants.FileConstant;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportController {
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    private static final Logger log = LogManager.getLogger(ExtentReportController.class);
    static ExtentTest test;
    private static ExtentReports extentReports;

    /**
     * Method helps to start extent report.
     */
    public static synchronized void startReport() {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
        String strDate = formatter.format(today);
        String reportDate = strDate.replaceAll("[ :]", "_");
        String path = String.format(FileConstant.REPORT_PATH, reportDate);
        System.setProperty("automationReportPath", path);
        System.setProperty("automationReportDate", reportDate);
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(path);
        htmlReporter.config().setDocumentTitle("Automation Report " + strDate);
        htmlReporter.config().setReportName("Automation Report");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().thumbnailForBase64(true);
        extentReports = new ExtentReports();
        extentReports.attachReporter(htmlReporter);
    }

    public static synchronized ExtentTest getTest() {
        return extentTest.get();
    }

    public static void logPass(String message) {
        getTest().log(Status.PASS, message);
        log.info(message);
    }

    public static void logFail(String message) {
        getTest().log(Status.FAIL, message);
        log.info(message);
    }

    public static void logInfo(String message) {
        getTest().log(Status.INFO, message);
        log.info(message);
    }

    public static void startTest(Method method) {
        Test annotations = method.getAnnotation(Test.class);
        String testName = annotations.testName();
        test = extentReports.createTest(testName);
        extentTest.set(test);
    }

    public static void flushReport() {
        extentReports.flush();
    }
}