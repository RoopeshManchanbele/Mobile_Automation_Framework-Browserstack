package org.roopesh.appium;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import org.roopesh.config.Config;
import org.openqa.selenium.MutableCapabilities;
import org.testng.Assert;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AutomationRunManager {
    private static final String userName = Config.getConfigProperty("userName");
    private static final String accesskey = Config.getConfigProperty("accessKey");
    private static final String browserStackLink = Config.getConfigProperty("browserStackURL");
    private static AppiumDriver appiumDriver;

    /**
     * Create a new Android driver instance for BrowserStack.
     *
     * @param testName  the name of the test
     * @param suiteName the name of the test suite
     * @throws MalformedURLException if the URL is malformed
     * @throws URISyntaxException    if the URI is malformed
     */
    public static void androidBrowserstackDriver(String testName, String suiteName) throws MalformedURLException, URISyntaxException {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        String formattedDate = formatter.format(localDate);
        MutableCapabilities capabilities = new UiAutomator2Options();
        capabilities.setCapability("name", testName);
        capabilities.setCapability("buildName", suiteName + ": " + formattedDate);
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("interactiveDebugging", true);
        URI uri = new URI("https://" + userName + ":" + accesskey + browserStackLink);
        URL url = uri.toURL();
        appiumDriver = new AndroidDriver(url, capabilities);
        AppiumDriverManager.initializeDriver(appiumDriver, testName);
    }

    /**
     * Create a new iOS driver instance for BrowserStack.
     *
     * @param testName the name of the test
     * @throws MalformedURLException if the URL is malformed
     * @throws URISyntaxException    if the URI is malformed
     */
    public static void iOSBrowserstackDriver(String testName) throws MalformedURLException, URISyntaxException {

        MutableCapabilities capabilities = new UiAutomator2Options();
        capabilities.setCapability("autoGrantPermissions", true);
        capabilities.setCapability("interactiveDebugging", true);
        capabilities.setCapability("name", testName);
        URI uri = new URI("https://" + userName + ":" + accesskey + browserStackLink);
        URL url = uri.toURL();
        appiumDriver = new IOSDriver(url, capabilities);
        AppiumDriverManager.initializeDriver(appiumDriver, testName);
    }

    /**
     * Start a local Appium test run.
     * <p>
     * This method creates a new Appium driver instance pointing to the local Appium server.
     *
     */
    public static void localRun() {
        try {
            String testName = "Local Test";
            URI uri = new URI("http://127.0.0.1:4723/");
            URL url = uri.toURL();
            UiAutomator2Options capabilities = new UiAutomator2Options();
            capabilities.setApp("C:\\Users\\EM992\\Desktop\\CodeBase\\AppiumTest\\src\\apk\\automation29Prod.apk");
            capabilities.setCapability("deviceName", "GooglePixel7");
            capabilities.setPlatformName("Android");
            capabilities.setCapability("automationName", "UiAutomator2");
            capabilities.setCapability("name", testName);
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("interactiveDebugging", true);
            appiumDriver = new AndroidDriver(url, capabilities);
            AppiumDriverManager.initializeDriver(appiumDriver, testName);
        } catch (Exception e) {
            Assert.fail("Caught Your Exception: " + e.fillInStackTrace().toString());
        }

    }
}
