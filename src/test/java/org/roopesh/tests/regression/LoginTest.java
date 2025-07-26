package org.roopesh.tests.regression;

import org.roopesh.BaseTest;
import org.roopesh.actions.Actions;
import org.roopesh.pageTestData.LoginData;
import org.roopesh.pages.LoginPage;
import org.testng.annotations.Test;
import utilities.annotations.MobileTest;

import static org.roopesh.helpers.AssertHelper.assertPass;
import static utilities.reports.ExtentReportController.logPass;

/**
 * Created By: Roopesh
 * Created Date: 11-10-2024
 */

@MobileTest(moduleName = "Login", manualTestcaseCount = 25, automatableTestcaseCount = 13, automatedCount = 13)
public class LoginTest extends BaseTest {

    @Test(priority = 1, testName = "Verify Login to the mobile application")
    public void loginTest() {
        LoginPage loginPage = getPage(LoginPage.class);
        LoginData loginData = getPage(LoginData.class);
        Actions actions = getPage(Actions.class);
        actions.tapIfPresent(loginPage.getInAppCloseButton());
        actions.tapOn(loginPage.getGetStarted());
        logPass("Clicked on Getting Started");
        actions.tapOn(loginPage.getContinueButton());
        logPass("Continue Clicked");
        actions.tapOn(loginPage.getContinueButton());
        logPass("Continue Clicked");
        actions.tapIfPresent(loginPage.getAlertHandler());
        actions.sendValues(loginPage.getEnterNumber(), loginData.getMobileNumber());
        logPass("Mobile Number Entered");
        actions.tapOn(loginPage.getContinueButton());
        logPass("Continue Clicked");
        actions.tapOn(loginPage.getSendOTPButton());
        actions.sendValues(loginPage.getEnterNumber(), loginData.getOtp());
        logPass("OTP Entered");
        actions.sendValues(loginPage.getEnterNumber(), loginData.getPin());
        logPass("4 digit pin is entered");
        actions.tapIfPresent(loginPage.getIUnderstandButton());
        actions.tapIfPresent(loginPage.getLaterButton());
        actions.tapIfPresent(loginPage.getCloseButton());
        logPass("Logged into the application successfully");
        assertPass("Logged into Application Successfully");
    }

}
