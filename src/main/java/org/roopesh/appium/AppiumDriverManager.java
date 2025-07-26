package org.roopesh.appium;

import io.appium.java_client.AppiumDriver;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.roopesh.config.Config;

import javax.annotation.concurrent.ThreadSafe;

/**
 * The Appium Driver Manager class helps to manage Appium drivers.
 * It provides the Appium driver for the current thread and class,
 * making it easy to write tests based on Appium Driver in a multithreaded context
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ThreadSafe
public final class AppiumDriverManager {


    private static final ThreadLocal<AppiumDriver> APPIUM_DRIVER = new ThreadLocal<>();
    private static final ThreadLocal<TestSessionInfo> SESSION_INFO = new ThreadLocal<>();

    /**
     * This provides the Appium driver for the current thread.
     *
     * @return {@link AppiumDriver} current thread appium driver
     */
    public static AppiumDriver driver() {
        return APPIUM_DRIVER.get();
    }

    /**
     * Returns the {@link TestSessionInfo} associated with the current test session.
     *
     * @return The {@link TestSessionInfo} associated with the current test session.
     */
    public static TestSessionInfo getSessionInfo() {
        return SESSION_INFO.get();
    }

    /**
     * Sets the {@link TestSessionInfo} for the current test session.
     * <p>
     * This method associates the provided {@link TestSessionInfo} with the current thread,
     * allowing retrieval of session-specific information during the test execution.
     *
     * @param sessionInfo The {@link TestSessionInfo} to be associated with the current test session.
     */
    public static void setSessionInfo(TestSessionInfo sessionInfo) {
        SESSION_INFO.set(sessionInfo);
    }

    /**
     * To make Appium driver thread-safe, each Appium driver should be stored in a thread pool.
     * This provides a way to set the current Appium Driver
     *
     * @param driver {@link AppiumDriver} Current test appium driver
     */
    public static void setDriver(final AppiumDriver driver) {
        APPIUM_DRIVER.set(driver);
    }

    /**
     * Initializes the Appium driver for the current test session.
     * <p>
     * This method sets the provided Appium driver as the current driver for the thread
     * and creates a new {@link TestSessionInfo} instance with session details.
     * It associates the session-specific information with the current thread,
     * facilitating retrieval during test execution.
     *
     * @param driver The {@link AppiumDriver} to be set for the current test session.
     * @param testMethodName The name of the test method associated with the session.
     */
    public static void initializeDriver(final AppiumDriver driver, String testMethodName) {
        setDriver(driver);
        String sessionId = driver.getSessionId().toString();
        String platform = driver.getCapabilities().getPlatformName().toString();
        String deviceName = driver.getCapabilities().getCapability("deviceName").toString();

        TestSessionInfo testSessionInfo = new TestSessionInfo(sessionId, platform, deviceName, testMethodName);
        setSessionInfo(testSessionInfo);
    }

    /**
     * The Appium Driver Manager removes the executed drivers from the thread pool after test execution.
     * This ensures that the thread pool is always available to execute new tasks.
     */
    public static void destroyAppiumDriver() {
        if (driver() != null) {
            driver().quit();
            APPIUM_DRIVER.remove();
            SESSION_INFO.remove();
        }
    }


    /**
     * This method provides the test execution timeout.
     *
     * @return {@link Long} The test execution timeout in milliseconds.
     */
    public static long getExecutionTimeoutValue() {
        String stringTimeOut = Config.getConfigProperty("idleTimeout");
        return Long.parseLong(stringTimeOut.trim());
    }
}