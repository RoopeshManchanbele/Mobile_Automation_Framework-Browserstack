package org.roopesh.actions;

import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.roopesh.appium.AppiumDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static utilities.reports.ExtentReportController.getTest;

public class WaitForElement {
    private final Logger log = LogManager.getLogger(WaitForElement.class);
    AppiumDriver appiumDriver;

    private WaitForElement() {
        appiumDriver = AppiumDriverManager.driver();
    }

    /**
     * Get the instance of {@link WaitForElement} class. This class is responsible for waiting for an element in the app
     * to be visible and clickable.
     *
     * @return The instance of {@link WaitForElement}
     */
    public static WaitForElement getWaitForElementInstance() {
        return LazyHolder.INSTANCE;
    }

    /**
     * Waits for a specified condition to be met using a default timeout.
     * <p>
     * This method utilizes the WebDriverWait to wait until the given condition is satisfied.
     * The default timeout duration is determined by the {@link #timeoutInSeconds()} method.
     *
     * @param condition the expected condition to wait for
     */
    public static void waitCondition(ExpectedCondition<?> condition) {
        waitCondition(condition, timeoutInSeconds());
    }

    /**
     * Waits for a specified condition to be met using a custom timeout.
     * <p>
     * This method utilizes the WebDriverWait to wait until the given condition is satisfied.
     * The timeout duration is provided as a parameter to this method.
     *
     * @param condition        the expected condition to wait for
     * @param timeoutInSeconds the timeout duration in seconds
     */
    public static void waitCondition(ExpectedCondition<?> condition, long timeoutInSeconds) {
        new WebDriverWait(getWaitForElementInstance().appiumDriver, Duration.ofSeconds(timeoutInSeconds)).until(condition);
    }

    /**
     * Waits for an element to be clickable using the default timeout.
     * <p>
     * This method utilizes the WebDriverWait to wait until the given element is clickable.
     * The default timeout duration is determined by the {@link #timeoutInSeconds()} method.
     *
     * @param locator The By locator to wait for
     */
    public static void waitForElementToClick(By locator) {
        ExpectedCondition<WebElement> condition = ExpectedConditions.elementToBeClickable(locator);
        waitCondition(condition);
    }

    /**
     * Waits for an element to be present using the default timeout.
     * <p>
     * This method utilizes the WebDriverWait to wait until the specified element is visible.
     * The default timeout duration is determined by the {@link #timeoutInSeconds()} method.
     *
     * @param locator The By locator of the element to wait for
     */
    public static void waitUntilElementIsPresent(By locator) {
        ExpectedCondition<WebElement> condition = ExpectedConditions.visibilityOfElementLocated(locator);
        waitCondition(condition);
    }

    /**
     * Provides the default timeout duration in seconds to be used when waiting for an expected condition.
     * <p>
     * The default timeout duration is determined by the {@link AppiumDriverManager#getExecutionTimeoutValue()} method,
     * but it is divided by 10 to provide a more suitable timeout for waiting operations.
     *
     * @return the default timeout duration in seconds
     */
    private static long timeoutInSeconds() {
        return AppiumDriverManager.getExecutionTimeoutValue() / 10;
    }

    /**
     * Waits for an element to be visible for a maximum of 2 seconds.
     * <p>
     * This method will wait until the given element is visible within 2 seconds, or timeout and return false.
     * The time is set to 2 seconds to quickly check if the element is visible or not.
     * <p>
     * The element is considered visible if it is displayed and has a height and width greater than 0.
     * <p>
     *
     * @param locator The By locator of the element to wait for.
     * @return true if the element is visible within 2 seconds, false otherwise.
     */
    public static boolean waitForElementToBeVisibleWithinTime(By locator) {
        try {
            new WebDriverWait(getWaitForElementInstance().appiumDriver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * Waits for the element's value to change from its initial value. The wait timeout is 20 seconds.
     * <p>
     * This method is useful for waiting for a stock value to change. It will wait until the stock value
     * changes from its initial value, or until the timeout is reached.
     * <p>
     * The element's value is obtained by splitting the element's content-desc attribute by newline
     * characters and taking the third element of the resulting array.
     * <p>
     *
     * @param element The element whose value is to be waited for.
     */
    public void waitForElementValueChange(WebElement element) {
        String stockElement = element.getDomAttribute("content-desc");
        assert stockElement != null;
        String[] stockData = stockElement.split("\n");
        String oldValue = stockData[2];
        log.info("Initial Value: {}", oldValue);
        AtomicReference<String> newValue = new AtomicReference<>();

        try {
            await()
                    .atMost(20, TimeUnit.SECONDS)
                    .pollInterval(2, TimeUnit.MILLISECONDS)
                    .until(() -> {
                        String updatedStockElement = element.getDomAttribute("content-desc");
                        assert updatedStockElement != null;
                        String[] updatedStockData = updatedStockElement.split("\n");
                        newValue.set(updatedStockData[2]);
                        log.info("Current Value: {}", newValue);
                        assert newValue.get() != null;
                        return !newValue.get().equals(oldValue);
                    });
            log.info("New value : {} Old Value : {}", newValue.get(), oldValue);
            getTest().log(Status.PASS, "New value : " + newValue.get() + " Old Value : " + oldValue);
            getTest().log(Status.PASS, "Stock Value is getting updated");
            Assert.assertTrue(true, "Stock value is getting updated ");

        } catch (Exception e) {
            log.error("Value is not getting updated");
            getTest().log(Status.FAIL, "Stock Value is not getting updated");
            Assert.fail("Stock value is not getting updated");
        }
    }

    private static class LazyHolder {
        private static final WaitForElement INSTANCE = new WaitForElement();
    }
}
