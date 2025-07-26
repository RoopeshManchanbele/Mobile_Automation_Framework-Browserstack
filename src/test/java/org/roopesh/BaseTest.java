package org.roopesh;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.roopesh.appium.AppiumDriverManager;
import org.roopesh.appium.AutomationRunManager;
import org.roopesh.appium.TestSessionInfo;
import org.roopesh.config.Config;
import org.roopesh.customExceptions.PageElementException;
import org.openqa.selenium.SessionNotCreatedException;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utilities.otp.OTPResponse;
import utilities.otp.OTPUnblocker;
import utilities.reports.ExtentReportController;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.roopesh.helpers.AssertHelper.assertFail;


/**
 * Created By: Roopesh
 * Created Date: 27-09-2024
 */

public class BaseTest extends BaseFramework {

    public static RequestSpecification httpsRequest;
    private final Logger log = LogManager.getLogger(BaseTest.class);
    Response response;


    /**
     * Initializes the test suite. This method is annotated with {@link BeforeSuite} and therefore it will be executed once before all the tests in the suite are executed.
     * <p>
     * This method sets the platform property in the system properties and starts a new report using the {@link ExtentReportController}.
     */
    @BeforeSuite
    public void initSuite() {
        String platform = Config.getConfigProperty("platform");
        System.setProperty("platform", platform);
        ExtentReportController.startReport();
        log.info("Platform - {}", platform);
    }

    /**
     * Initializes the test. This method is annotated with {@link BeforeMethod} and therefore it will be executed once before each test in the suite is executed.
     * <p>
     * This method sets the platform property in the system properties, starts a new test using the {@link ExtentReportController}, removes the OTP limit and starts a new
     * Appium driver instance pointing to the Appium server depending on the platform property.
     * <p>
     * If the platform property is set to "android", it starts a new Android Appium driver instance.
     * If the platform property is set to "iOS", it starts a new iOS Appium driver instance.
     * If the platform property is set to "localRun", it starts a new local Appium driver instance.
     * <p>
     * If the Appium driver instance cannot be created, it asserts the failure and logs the stack trace.
     *
     * @param os         the platform to run the test on
     * @param methodName the test method
     * @param context    the test context
     */
    @Parameters("os")
    @BeforeMethod
    public void initTest(@Optional("android") String os, Method methodName, ITestContext context) throws MalformedURLException, URISyntaxException {
        TestSessionInfo sessionID = null;
        log.info("Application Loaded");
        ExtentReportController.startTest(methodName);
        try {
            os = System.getProperty("platform");
            switch (os) {
                case "android":
                    AutomationRunManager.androidBrowserstackDriver(methodName.getAnnotation(Test.class).testName(), context.getSuite().getName());
                    break;
                case "iOS":
                    AutomationRunManager.iOSBrowserstackDriver(context.getName());
                    break;
                case "localRun":
                    AutomationRunManager.localRun();
                    break;
                default:
                    //do nothing

            }
            sessionID = AppiumDriverManager.getSessionInfo();
            log.info("Session ID: {}", AppiumDriverManager.getSessionInfo());
            log.info("Test --- {} --- started", methodName.getAnnotation(Test.class).testName());
        } catch (SessionNotCreatedException se) {
            throw new PageElementException("Please check the session info:", se);
        } catch (Exception e) {

            if (AppiumDriverManager.getSessionInfo() == null) {
                AppiumDriverManager.setSessionInfo(sessionID);
                AutomationRunManager.androidBrowserstackDriver(methodName.getAnnotation(Test.class).testName(), context.getSuite().getName());
            } else {
                assertFail("Caught Your Exception: " + e.fillInStackTrace().toString());
            }
        }
    }


    /**
     * Cleans up after each test method and after all tests in the class.
     * <p>
     * This method is annotated with {@link AfterMethod} and {@link AfterClass},
     * ensuring that it is executed after each test method and once after all
     * tests in the class have been executed.
     * <p>
     * It deletes the instances associated with the current Appium driver and
     * destroys the Appium driver to release resources.
     */
    @AfterMethod
    @AfterClass
    public void offload() {
        deleteInstances(AppiumDriverManager.driver());
        AppiumDriverManager.destroyAppiumDriver();

    }

    /**
     * Closes the Extent report after all tests in the suite have been executed.
     * <p>
     * This method is annotated with {@link AfterSuite} and is executed once after all
     * tests in the suite have been executed.
     * <p>
     * It flushes the report by calling {@link ExtentReportController#flushReport()}.
     */
    @AfterSuite
    public void tearDown() {
        ExtentReportController.flushReport();
    }


}
