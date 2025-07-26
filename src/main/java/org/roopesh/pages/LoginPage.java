package org.roopesh.pages;


import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.roopesh.BasePage;
import org.roopesh.helpers.locators.LocatorHelper;
import org.roopesh.helpers.locators.Locators;
import org.openqa.selenium.By;

@Getter
public class LoginPage extends BasePage {

    private final Logger log = LogManager.getLogger(LoginPage.class);
    private final By inAppCloseButton;
    private final By getStarted;
    private final By continueButton;
    private final By enterNumber;
    private final By alertHandler;
    private final By sendOTPButton;
    private final By clientID;
    private final By iUnderstandButton;
    private final By laterButton;
    private final By closeButton;

    public LoginPage() {
        Locators locators = LocatorHelper.getLocators(this);
        inAppCloseButton = locators.get("inAppCloseButton");
        getStarted = locators.get("getStarted");
        continueButton = locators.get("continue");
        enterNumber = locators.get("enterNumberOrPin");
        sendOTPButton = locators.get("sendOTPButton");
        clientID = locators.get("clientID");
        iUnderstandButton = locators.get("understandButton");
        laterButton = locators.get("laterButton");
        closeButton = locators.get("closeButton");
        alertHandler = locators.get("alertHandler");

    }

}
