package org.roopesh;

import io.appium.java_client.AppiumBy;
import org.roopesh.actions.Gestures;
import org.roopesh.actions.WaitForElement;
import org.roopesh.customExceptions.PageElementException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.roopesh.actions.WaitForElement.waitForElementToBeVisibleWithinTime;
import static org.roopesh.appium.AppiumDriverManager.driver;

public abstract class BasePage extends BaseFramework {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);

    protected Gestures gestures;
    protected WaitForElement webDriverWaitUtils;


    protected BasePage() {
        gestures = Gestures.getGestureInstance();
        webDriverWaitUtils = WaitForElement.getWaitForElementInstance();
    }

    /**
     * Method used to wait for an element to be present
     *
     * @param locator {@link By} locator identify
     */
    protected void waitForElementToBeVisible(By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
    }


    protected void clickIfClickable(By locator) {
        WaitForElement.waitForElementToClick(locator);
        gestures.clickElement(locator);
    }

    /**
     * Method used to wait for an element based on text to be present and then click on it.
     *
     * @param locatorText {@link String} locator text to click
     */
    protected void waitAndClickBasedOnLocatorText(final String locatorText) {

        By locator = AppiumBy.xpath("//*[contains(@content-desc,'" + locatorText + "')]");
        WaitForElement.waitUntilElementIsPresent(locator);
        getElement(locator).click();
    }

    /**
     * Method used to wait for an element to be present and then tap on it.
     *
     * @param locator {@link By} locator to tap
     */
    protected void waitAndTapElement(final By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        gestures.clickElement(locator);
    }

    /**
     * Method used to wait for an element to be present clear text and then send keys to it.
     *
     * @param locator {@link By} locator to send the value
     * @param value   {@link String} value to send
     */
    protected void waitAndSendKey(final By locator, final String value) {
        WaitForElement.waitUntilElementIsPresent(locator);
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(value);
    }

    /**
     * Method used to wait for an element to be present and then swipe from left to right
     *
     * @param locator {@link By} locator identify
     */
    protected void waitAndSwipeLeftToRight(By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        Gestures.swipeElement(getElement(locator), true);
    }

    /**
     * Method used to wait for an element to be present and then swipe from left to right
     *
     * @param locator {@link By} locator identify
     */
    protected void waitAndSwipeRightToLeft(By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        Gestures.swipeElement(getElement(locator), false);
    }


    /**
     * Method used to wait for an element to be present and then get its content description.
     *
     * @param locator {@link By} locator to get the text
     * @return {@link String} locator text
     */
    protected String waitAndGetContent(final By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        return getElement(locator).getDomAttribute("content-desc");
    }

    /**
     * Method used to check if an element is present or not.<br>
     * <b>Note:</b> To avoid NoSuchElement used try catch block, and it returns false if throw NoSuchElement Exception.
     *
     * @param locator {@link By} locator to check if present
     * @return {@link Boolean} return true if present else return false
     */
    protected boolean isElementPresent(final By locator) {
        boolean isPresent = false;
        try {
            isPresent = getElement(locator) != null;
        } catch (NoSuchElementException e) {
            LOGGER.debug("{} element not exist", locator);
        }
        return isPresent;
    }

    protected boolean isElementPresent(final String locatorText) {
        By locator = AppiumBy.xpath("//*[contains(@content-desc,'" + locatorText + "')]");
        boolean isPresent = false;
        try {
            isPresent = getElement(locator) != null;
        } catch (NoSuchElementException e) {
            LOGGER.debug("{} element not exist", locator);
        }
        return isPresent;
    }


    /**
     * Method used to wait for an element to be present and check if an element is present or not.<br>
     * <b>Note:</b> To avoid NoSuchElement used try catch block, and it returns false if throw NoSuchElement Exception.
     *
     * @param locator {@link By} locator to check if present
     * @return {@link Boolean} return true if present else return false
     */
    protected boolean waitAndIsElementPresent(final By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        return isElementPresent(locator);
    }

    /**
     * Method is used to wait for an element to be present and then check if it is displayed.
     *
     * @param locator {@link By} locator to check if it is displayed.
     * @return {@link Boolean} if element is displayed then return true else returns false.
     */
    protected boolean waitAndIsElementDisplayed(final By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        return getElement(locator).isDisplayed();
    }

    /**
     * Method is used to wait for an element to be present and then check if it is displayed.
     *
     * @param locatorText {@link String} locator to check if it is displayed.
     * @return {@link Boolean} if element is displayed then return true else returns false.
     */
    protected boolean waitAndIsElementDisplayed(final String locatorText) {
        By locator = AppiumBy.xpath("//*[contains(@content-desc,'" + locatorText + "')]");
        WaitForElement.waitUntilElementIsPresent(locator);
        return getElement(locator).isDisplayed();
    }


    /**
     * Method is s used to get an element by its locator.
     * If the element is found, the function returns the element. If the element is not found, the function throws an exception
     *
     * @param locator {@link By} locator to get the element
     * @return {@link WebElement}  element value
     */
    protected WebElement getElement(final By locator) {
        return driver().findElement(locator);
    }

    /**
     * Method is s used to get a list of element by its locator.
     * If the element is found, the function returns list of element. If the element is not found, the function return empty list.
     *
     * @param locator {@link By} locator to get the elements
     * @return {@link WebElement (list of web Element)}  element value
     */
    protected List<WebElement> getElements(final By locator) {
        return driver().findElements(locator);
    }


    /**
     * Method is s used to get text of element by its locator.
     * If the element is found, the function returns text of element.
     *
     * @param locator {@link By} locator to get the elements
     * @return {@link WebElement (list of web Element)}  element value
     */
    protected String getElementText(final By locator) {
        return getElement(locator).getText();
    }

    /**
     * Method is s used to get a list of element by its locator.
     * If the element is found, the function returns list of element. If the element is not found, the function return empty list.
     *
     * @param value {@link String} locator to get the elements
     * @return {@link WebElement (list of web Element)}  element value
     */
    protected String getElementContentByValue(String value) {
        return getElement(AppiumBy.xpath("//*[contains(@content-desc,'" + value + "')]")).getDomAttribute("content-desc");
    }

    /**
     * Method is s used to get a list of element by its locator.
     * If the element is found, the function returns list of element. If the element is not found, the function return empty list.
     *
     * @param firstValue  {@link String} first value of the locator
     * @param secondValue {@link String} second value of the locator
     * @return {@link WebElement (list of web Element)}  element value
     */
    protected String getElementContentByValue(String firstValue, String secondValue) {
        return getElement(AppiumBy.xpath("//*[contains(@content-desc,'" + firstValue + "') and contains(@content-desc,'" + secondValue + "')]")).getDomAttribute("content-desc");
    }

    /**
     * Method is s used to get a list of element by its locator.
     * If the element is found, the function returns list of element. If the element is not found, the function return empty list.
     *
     * @param firstValue  {@link String} first value of the locator
     * @param secondValue {@link String} second value of the locator
     * @param thirdValue  {@link String} second value of the locator
     * @return {@link WebElement (list of web Element)}  element value
     */
    protected String getElementContentByValue(String firstValue, String secondValue, String thirdValue) {
        return getElement(AppiumBy.xpath("//*[contains(@content-desc,'" + firstValue + "') and contains(@content-desc,'" + secondValue + "') and contains(@content-desc,'" + thirdValue + "')]")).getDomAttribute("content-desc");
    }

    /**
     * Method used to scroll down till element {@param locator} is found
     */
    protected void scrollDownTillElementIsPresent(final By locator) {
        long startTime = System.currentTimeMillis();
        long timeoutInMillis = 20000;
        while (!isElementPresent(locator)) {
            if (System.currentTimeMillis() - startTime > timeoutInMillis) {
                throw new TimeoutException("Element not found within 20 seconds.");
            }
            gestures.swipeBottomToTop(0.8, 0.4);
        }
    }

    /**
     * Method used to scroll down till element {@param value} is found.
     */
    protected void scrollDownTillElementIsPresent(String value) {
        long startTime = System.currentTimeMillis();
        long timeoutInMillis = 20000;
        while (!isElementPresent(AppiumBy.xpath("//*[contains(@content-desc,'" + value + "')]"))) {
            if (System.currentTimeMillis() - startTime > timeoutInMillis) {
                throw new TimeoutException("Element with content-desc: '" + value + "' not found within 20 seconds.");
            }
            gestures.swipeBottomToTop(0.8, 0.4);
        }
    }

    /**
     * Method used to click on element {@param locator} if found.
     */
    protected void clickIfElementPresent(final By locator) {
        try {
            if (waitForElementToBeVisibleWithinTime(locator))
                if (isElementPresent(locator)) {
                    waitAndTapElement(locator);
                }
        } catch (NoSuchElementException ne) {
            throw new PageElementException("Element not found", ne);
        }
    }

    /**
     * Swipes left to right on the element with the given content description.
     * This method waits for the element to be present before performing the swipe.
     *
     * @param elementText the content description of the element to swipe
     */
    protected void swipeSpecificElementLeftToRight(String elementText) {
        waitAndSwipeLeftToRight(AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]"));
    }

    /**
     * Swipes right to left on the element with the given content description.
     * This method waits for the element to be present before performing the swipe.
     *
     * @param elementText the content description of the element to swipe
     */
    protected void swipeSpecificElementRightToLeft(String elementText) {
        waitAndSwipeRightToLeft(AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]"));
    }


    /**
     * Clicks on the left element of a row specified by the given content description text.
     * <p>
     * This method waits for the element with the specified content description to be present
     * and then performs a tap action on the left sibling element of that row.
     *
     * @param elementText the content description of the element to identify the row
     */
    protected void clickLeftElementOfRow(String elementText) {
        waitAndTapElement(AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]/preceding-sibling::*[1]"));
    }


    /**
     * Clicks on the right element of a row specified by the given content description text.
     * <p>
     * This method waits for the element with the specified content description to be present
     * and then performs a tap action on the right sibling element of that row.
     *
     * @param elementText the content description of the element to identify the row
     */
    protected void clickRightElementOfRow(String elementText) {
        waitAndTapElement(AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]/following-sibling::*[1]"));
    }


    /**
     * Clicks on the right sibling element of a row specified by the given text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on the right sibling element of that row.
     *
     * @param elementText the text of the element to identify the row
     */
    protected void clickRightElementByText(String elementText) {
        waitAndTapElement(AppiumBy.xpath("//*[contains(@text,'" + elementText + "')]/following-sibling::*[1]"));
    }


    /**
     * Clicks on the second left element of a row specified by the given content description text.
     * <p>
     * This method waits for the element with the specified content description to be present
     * and then performs a tap action on the second left sibling element of that row.
     *
     * @param elementText the content description of the element to identify the row
     */
    protected void clickSecondLeftElementOfRow(String elementText) {
        waitAndTapElement(AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]/preceding-sibling::*[2]"));
    }

    /**
     * Clicks on the second right element of a row specified by the given content description text.
     * <p>
     * This method waits for the element with the specified content description to be present
     * and then performs a tap action on the second right sibling element of that row.
     *
     * @param elementText the content description of the element to identify the row
     */
    protected void clickSecondRightElementOfRow(String elementText) {
        waitAndTapElement(AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]/following-sibling::*[1]"));
    }

    /**
     * Clicks on the child element of a row specified by the given content description text.
     * <p>
     * This method waits for the element with the specified content description to be present
     * and then performs a tap action on the child element of that row.
     *
     * @param elementText the content description of the element to identify the row
     */
    protected void clickChildElementOfRow(String elementText) {
        waitAndTapElement(AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]/*[1]"));
    }

    /**
     * Clicks on the child element of a row specified by the given text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on the child element of that row.
     *
     * @param elementText the text of the element to identify the row
     */
    protected void clickChildElementByText(String elementText) {
        waitAndTapElement(AppiumBy.xpath("//*[contains(@text,'" + elementText + "')]/*[1]"));
    }

    /**
     * Waits for the element with the given locator to be present and then
     * clicks on the right side of the element.
     *
     * @param locator the By locator of the element to click
     */
    protected void waitAndClickRightButton(final By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        gestures.tapRightSideOfTheElement(getElement(locator));
    }

    /**
     * Waits for the element with the given locator to be present and then
     * clicks on the left side of the element.
     *
     * @param locator the By locator of the element to click
     */
    protected void waitAndClickLeftButton(final By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        gestures.tapLeftSideOfTheElement(getElement(locator));
    }

    /**
     * Waits for the stock price update of the given stock name.
     * <p>
     * This method waits for the element with the given stock name to be present
     * and then waits for the element value to change.
     *
     * @param stockName the name of the stock to wait for the price update
     */
    protected void waitAndCheckForStockPriceUpdate(String stockName) {
        WebElement element = driver().findElement(AppiumBy.xpath("//*[contains(@content-desc,'" + stockName + "')]"));
        webDriverWaitUtils.waitForElementValueChange(element);
    }

    /**
     * Returns true if the element located by the given locator is checked.
     * <p>
     * This method waits for the element to be present and then checks if the element is checked.
     *
     * @param locator the By locator of the element to check if it is checked
     * @return true if the element is checked, otherwise false
     */
    protected boolean checkboxValidator(By locator) {
        return Boolean.parseBoolean(getElement(locator).getDomAttribute("checked"));
    }

    /**
     * Hides the keyboard on the device if it is visible.
     */
    protected void hideKeyboard() {
        gestures.hideKeyboard();
    }

    /**
     * Navigates back one page.
     */
    protected void navigateBackOnce() {
        gestures.navigateBack();
    }

    /**
     * Navigates back until the specified element is present.
     * <p>
     * This method repeatedly navigates back until the element with the specified
     * content description is present on the page.
     *
     * @param locatorText the content description text of the element to wait for
     */
    protected void navigateBackTillElementIsPresent(String locatorText) {
        By locator = AppiumBy.xpath("//*[contains(@content-desc,'" + locatorText + "')]");
        while (!isElementPresent(locator)) {
            gestures.navigateBack();
        }
    }

    /**
     * Navigates back until the specified element is present.
     * <p>
     * This method repeatedly navigates back until the element with the specified
     * locator is present on the page.
     *
     * @param locator the By locator of the element to wait for
     */
    protected void navigateBackTillElementIsPresent(By locator) {
        long startTime = System.currentTimeMillis();
        long timeoutInMillis = 20000;
        while (!isElementPresent(locator)) {
            if (System.currentTimeMillis() - startTime > timeoutInMillis) {
                throw new TimeoutException("Element not found within 20 seconds.");
            }
            gestures.navigateBack();
        }
    }

    /**
     * Retrieves the specified content attribute of an element and returns it as a boolean.
     * <p>
     * This method fetches the content attribute of an element identified by the given locator
     * and converts its value to a boolean.
     *
     * @param locator     {@link By} locator to identify the element
     * @param contentName {@link String} name of the content attribute to retrieve
     * @return {@link Boolean} true if the content attribute value is "true", otherwise false
     */
    protected boolean getContent(By locator, String contentName) {
        return Boolean.parseBoolean(getElement(locator).getDomAttribute(contentName));
    }

    /**
     * Gets the text of the current Android notifications.
     * <p>
     * This method checks the Android notifications, gets all the text elements
     * and returns the text of those elements in a list.
     *
     * @return a list with the text of the current Android notifications
     */
    protected List<String> getAndroidNotifications() {
        List<String> notificationData = new java.util.ArrayList<>();
        gestures.checkAndroidNotification();
        List<WebElement> elements = driver().findElements(AppiumBy.xpath("//android.widget.TextView"));
        for (WebElement elem : elements) {
            notificationData.add(elem.getText());
        }
        return notificationData;
    }

    /**
     * Clears all the current Android notifications.
     * <p>
     * This method checks the Android notifications, scrolls down till the "Clear all notifications" button
     * is present, and then clicks on it.
     */
    protected void clearAndroidNotifications() {
        gestures.checkAndroidNotification();
        By clearButton = AppiumBy.accessibilityId("Clear all notifications.");
        scrollDownTillElementIsPresent(clearButton);
        clickIfElementPresent(clearButton);
    }

    /**
     * Long presses on the element with the given content description.
     * <p>
     * This method waits for the element with the given content description to be present
     * and then performs a long press action on the element.
     *
     * @param elementText the content description of the element to long press
     */
    protected void longPressElement(String elementText) {
        By locator = AppiumBy.xpath("//*[contains(@content-desc,'" + elementText + "')]");
        WebElement element = driver().findElement(locator);
        WaitForElement.waitUntilElementIsPresent(locator);
        gestures.longPress(element);
    }

    /**
     * Long presses on the element with the given locator.
     * <p>
     * This method waits for the element with the given locator to be present
     * and then performs a long press action on the element.
     *
     * @param locator the locator of the element to long press
     */
    protected void longPressElement(By locator) {
        WaitForElement.waitUntilElementIsPresent(locator);
        gestures.longPress(getElement(locator));
    }


    /**
     * Returns the price value present in the content description of the element
     * identified by the given locator.
     *
     * @param locator the locator of the element to get the price value
     * @return the price value as a string, or null if no match
     */
    protected String getPriceValue(By locator) {
        String contentDesc = getElement(locator).getDomAttribute("content-desc");

        if (contentDesc == null) {
            throw new IllegalArgumentException("Content description is null for the given locator.");
        }
        Pattern pattern = Pattern.compile("(-?₹\\s?[\\d.,]+)");
        Matcher matcher = pattern.matcher(contentDesc);
        if (matcher.find()) {
            return matcher.group(1).replace("₹", "").replace(",", "").trim();
        }
        return null;
    }

    /**
     * Retrieves the price value from the text content of the specified element.
     * <p>
     * This method finds the element using the provided {@link By} locator,
     * obtains its text content, and converts it to a {@link Double}.
     *
     * @param locator the {@link By} locator of the element to retrieve the price from
     * @return the price value as a {@link Double}
     * @throws NumberFormatException if the text content cannot be parsed as a double
     */
    protected Double getPrice(By locator) {
        String price = getElement(locator).getText();
        return Double.parseDouble(price);
    }


    /**
     * Extracts the price value from the given string.
     * <p>
     * This method takes a string and matches it against a regular expression
     * to extract the price value. The regular expression is designed to handle
     * optional negative sign, currency symbol, and decimal points. If the
     * regular expression matches, the method returns the extracted price value
     * as a string, else returns null.
     *
     * @param price the string to extract the price value from
     * @return the price value as a string, or null if no match
     */
    protected String getPriceValue(String price) {
        Pattern pattern = Pattern.compile("(-?₹\\s?[\\d.,]+)");
        assert price != null;
        Matcher matcher = pattern.matcher(price);

        if (matcher.find()) {
            return matcher.group(1).replace("₹", "").replace(",", "").trim();
        }
        return null;
    }

    /**
     * Performs a double click action on the element specified by the given locator.
     *
     * @param locator the By locator of the element to double click
     */
    protected void doubleClick(By locator) {
        gestures.doubleClick(getElement(locator));
    }

    /**
     * Performs a double click action on the element specified by the given locator text.
     * <p>
     * This method takes a string and converts it into a By locator using the
     * content description, and then performs a double click action on the element.
     *
     * @param locatorText the text of the locator to double click
     */
    protected void doubleClick(String locatorText) {
        By locator = AppiumBy.xpath("//*[contains(@content-desc,'" + locatorText + "')]");
        gestures.doubleClick(getElement(locator));
    }

    /**
     * Manually swipes an element to the right the specified number of times.
     * <p>
     * This method takes a By locator and an integer number of swipes, and
     * performs a manual swipe action on the element the specified number of
     * times.
     *
     * @param locator    the By locator of the element to swipe
     * @param swipeCount the number of times to swipe the element
     */
    protected void manualSwipeRight(By locator, int swipeCount) {
        gestures.customSwipeRight(getElement(locator), swipeCount);
    }

}
