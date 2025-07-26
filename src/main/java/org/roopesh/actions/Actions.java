package org.roopesh.actions;

import org.roopesh.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static org.roopesh.appium.AppiumDriverManager.driver;
import static utilities.reports.ExtentReportController.logFail;
import static utilities.reports.ExtentReportController.logInfo;

/**
 * Created By: Roopesh
 * Created Date: 18-11-2024
 */

public class Actions extends BasePage {
    /**
     * Taps on the given {@link By} locator.
     *
     * @param locator the {@link By} locator to tap on
     */
    public void tapOn(By locator) {
        waitAndTapElement(locator);
    }


    /**
     * Taps on the element specified by the given {@link By} locator if it is present.
     *
     * @param locator the {@link By} locator of the element to tap
     */
    public void tapIfPresent(By locator) {
        clickIfElementPresent(locator);
    }


    /**
     * Hides the keyboard on the device if it is visible.
     */
    @Override
    public void hideKeyboard() {
        super.hideKeyboard();
    }

    /**
     * Clicks on the given {@link By} locator if the element is clickable.
     * <p>
     * This method is a wrapper around {@link #clickIfClickable(By)}.
     */
    public void clickIfElementIsClickable(By locator) {
        clickIfClickable(locator);
    }


    /**
     * Taps on the element specified by the given locator text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on it.
     *
     * @param locatorText the text of the locator to tap on
     */
    public void tapOn(String locatorText) {
        waitAndClickBasedOnLocatorText(locatorText);
    }


    /**
     * Taps on the right side of the button specified by the given {@link By} locator.
     * <p>
     * This method waits for the element with the specified locator to be present
     * and then performs a tap action on its right side.
     *
     * @param locator the {@link By} locator of the element to tap
     */
    public void tapRightSideOfTheButton(By locator) {
        waitAndClickRightButton(locator);
    }


    /**
     * Taps on the right button of a row specified by the given text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on the right button of that row.
     *
     * @param locatorText the text of the locator to identify the row
     */
    public void tapRightButton(String locatorText) {
        clickRightElementOfRow(locatorText);
    }

    /**
     * Taps on the right button of a row specified by the given text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on the right button of that row.
     *
     * @param locatorText the text of the locator to identify the row
     */
    public void tapRightButtonByText(String locatorText) {
        clickRightElementByText(locatorText);
    }

    /**
     * Taps on the child element of a row specified by the given text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on the child element of that row.
     *
     * @param locatorText the text of the locator to identify the row
     */
    public void tapChildButtonByText(String locatorText) {
        clickChildElementByText(locatorText);
    }

    /**
     * Taps on the child element of a row specified by the given text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on the child element of that row.
     *
     * @param locatorText the text of the locator to identify the row
     */
    public void tapOnChildButton(String locatorText) {
        clickChildElementOfRow(locatorText);
    }

    /**
     * Taps on the left side of the button specified by the given {@link By} locator.
     * <p>
     * This method waits for the element with the specified locator to be present
     * and then performs a tap action on its left side.
     *
     * @param locator the {@link By} locator of the element to tap
     */
    public void tapLeftSideOfTheButton(By locator) {
        waitAndClickLeftButton(locator);
    }

    /**
     * Taps on the left side of the button specified by the given text.
     * <p>
     * This method waits for the element with the specified text to be present
     * and then performs a tap action on its left side.
     *
     * @param locatorText the text of the locator to identify the button
     */
    public void tapLeftButton(String locatorText) {
        clickLeftElementOfRow(locatorText);
    }


    /**
     * Enters the given value into the text field specified by the given locator.
     * <p>
     * This method waits for the element with the specified locator to be present,
     * taps on it, and then enters the given value into the text field.
     * Finally, it hides the keyboard.
     *
     * @param locator the {@link By} locator of the text field
     * @param value   the value to enter into the text field
     */
    public void sendValues(By locator, String value) {
        waitAndTapElement(locator);
        waitAndSendKey(locator, value);
        hideKeyboard();
    }

    /**
     * Scrolls down until the element specified by the given {@link By} locator is present.
     * <p>
     * This method waits for the element to be present and then scrolls down until the element is visible.
     *
     * @param locator the {@link By} locator of the element to scroll down to
     */
    public void scrollDownTo(By locator) {
        scrollDownTillElementIsPresent(locator);
    }

    /**
     * Scrolls down until the element specified by the given text is present.
     * <p>
     * This method waits for the element to be present and then scrolls down until the element is visible.
     *
     * @param locatorText the text of the element to scroll down to
     */
    public void scrollDownTo(String locatorText) {
        scrollDownTillElementIsPresent(locatorText);
    }

    /**
     * Navigates back to the previous screen or page.
     * <p>
     * This method performs a single back navigation action, typically used to go back to the previous state in the application.
     */
    public void navigateBack() {
        navigateBackOnce();
    }

    /**
     * Swipes right on the specified element until another element is present.
     * <p>
     * This method repeatedly performs a swipe right action on the given {@link By} element
     * until the specified {@link By} forLocator element becomes present on the screen.
     * The swipe action is performed from left to right.
     * <p>
     * The method will time out and throw a {@link TimeoutException} if the Locator
     * element is not found within 20 seconds.
     *
     * @param element    the {@link By} locator of the element to swipe right on
     * @param forLocator the {@link By} locator of the element to wait for
     */
    public void swipeRight(By element, By forLocator) {
        long startTime = System.currentTimeMillis();
        long timeoutInMillis = 20000;
        while (!isElementPresent(forLocator)) {
            if (System.currentTimeMillis() - startTime > timeoutInMillis) {
                throw new TimeoutException("Element not found within 5 seconds.");
            }
            waitAndSwipeLeftToRight(element);
        }
    }

    /**
     * Navigates back to the previous screen or page until the specified element is present.
     * <p>
     * This method repeatedly performs a back navigation action until the specified {@link By} element
     * becomes present on the screen.
     * The method will time out and throw a {@link TimeoutException} if the element is not found within 20 seconds.
     *
     * @param locator the {@link By} locator of the element to navigate back to
     */
    public void navigateBackTo(By locator) {
        navigateBackTillElementIsPresent(locator);
    }

    /**
     * Navigates back to the previous screen or page until the specified element is present.
     * <p>
     * This method repeatedly performs a back navigation action until the element with the specified
     * text becomes present on the screen.
     * The method will time out and throw a {@link TimeoutException} if the element is not found within 20 seconds.
     *
     * @param locatorText the text of the element to navigate back to
     */
    public void navigateBackTo(String locatorText) {
        navigateBackTillElementIsPresent(locatorText);
    }

    /**
     * Waits for the stock price update of the given stock name.
     * <p>
     * This method waits for the stock price update of the given stock name.
     * The method will time out and throw a {@link TimeoutException} if the element is not found within 20 seconds.
     *
     * @param stockName the name of the stock to wait for
     */
    public void validateLTPUpdate(String stockName) {
        waitAndCheckForStockPriceUpdate(stockName);
    }

    /**
     * Swipes left on the specified element.
     * <p>
     * This method performs a swipe left action on the element with the specified text.
     * The swipe action is performed from right to left.
     * <p>
     * The method will time out and throw a {@link TimeoutException} if the Locator
     * element is not found within 20 seconds.
     *
     * @param locatorText the text of the element to swipe left on
     */
    public void swipeLeft(String locatorText) {
        swipeSpecificElementRightToLeft(locatorText);
    }

    /**
     * Swipes right on the specified element.
     * <p>
     * This method performs a swipe right action on the element with the specified text.
     * The swipe action is performed from left to right.
     *
     * @param locatorText the text of the element to swipe right on
     */
    public void swipeRight(String locatorText) {
        swipeSpecificElementLeftToRight(locatorText);
    }

    /**
     * Manually swipes the element to the right the specified number of times.
     * <p>
     * This method takes a By locator and an integer number of swipes, and
     * performs a manual swipe action on the element the specified number of
     * times.
     *
     * @param locator    the By locator of the element to swipe
     * @param swipeCount the number of times to swipe the element
     */
    public void swipeRight(By locator, int swipeCount) {
        manualSwipeRight(locator, swipeCount);
    }

    /**
     * Swipes right on the element with the given locator.
     * <p>
     * This method performs a swipe right action on the element with the specified locator.
     * The swipe action is performed from left to right.
     * <p>
     * The method will time out and throw a {@link TimeoutException} if the Locator
     * element is not found within 20 seconds.
     *
     * @param locator the By locator of the element to swipe right on
     */
    public void swipeRight(By locator) {
        waitAndSwipeLeftToRight(locator);
    }


    /**
     * Manually swipes right on the element with the given firstElement locator
     * until the element with the given locator is found.
     * <p>
     * This method takes two By locators, and performs a manual swipe action on the
     * element with the firstElement locator until the element with the locator
     * is found.
     * <p>
     * The swipe action is performed from left to right, and the method will time
     * out and throw a {@link TimeoutException} if the Locator element is not found
     * within 20 seconds.
     * <p>
     * The method will also throw a {@link AssertionError} if the Locator element is
     * not found after 10 swipes.
     *
     * @param firstElement the By locator of the element to swipe on
     * @param locator      the By locator of the element to find
     */
    public void swipeRightFor(By firstElement, By locator) {
        boolean flag;
        try {
            WebElement ele = getElement(firstElement);
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Rectangle bounds = ele.getRect();
            Dimension screenSize = driver().manage().window().getSize();
            int screenWidth = screenSize.width;

            int x = (int) (screenWidth * 0.2);
            int y = bounds.getY() + (bounds.getHeight() / 2);
            int endx = (int) (screenWidth * 0.9);

            // Initial check for the element
            flag = isElementPresent(locator);
            if (flag) {
                return;
            }
            int swipeTimes = 10;
            for (int i = 0; i < swipeTimes && !flag; i++) {

                Sequence swipe = new Sequence(finger, 1);
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), endx, y));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(2000), PointerInput.Origin.viewport(), x, y));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver().perform(List.of(swipe));
                flag = isElementPresent(locator);
            }

            if (!flag) {
                logInfo("Element not found: swipeForTrendingOptions");
                Assert.fail("Element not found: swipeForTrendingOptions");
            }
        } catch (Exception e) {
            logFail("Exception in swipeRightFor method: " + e);
        }
    }

    /**
     * Retrieves the text content of the element specified by the given locator.
     * <p>
     * This method will wait for the element to be present and visible before
     * retrieving the content.
     * <p>
     * The method will return the text content of the element, or an empty string
     * if the element is not found.
     *
     * @param locator the By locator of the element to retrieve the content from
     * @return the text content of the element
     */
    public String verifyContent(By locator) {
        return waitAndGetContent(locator);
    }

    /**
     * Retrieves the text content of the element specified by the given
     * locatorText.
     * <p>
     * This method will wait for the element to be present and visible before
     * retrieving the content.
     * <p>
     * The method will return the text content of the element, or an empty string
     * if the element is not found.
     *
     * @param locatorText the text of the element to retrieve the content from
     * @return the text content of the element
     */
    public String verifyContent(String locatorText) {
        return getElementContentByValue(locatorText);
    }

    /**
     * Determines if the element with the given locator is present.
     * <p>
     * This method will return true if the element is present, and false if it is not.
     * <p>
     * Note that this method does not wait for the element to be present, it will
     * return false immediately if the element is not present.
     *
     * @param locator the By locator of the element to check
     * @return true if the element is present, false otherwise
     */
    public boolean isTrue(By locator) {
        return isElementPresent(locator);
    }

    /**
     * Retrieves the text content of the specified element.
     * <p>
     * This method finds the element using the provided {@link By} locator
     * and returns its text content.
     *
     * @param locator the {@link By} locator of the element to retrieve the text from
     * @return the text content of the element
     */
    public String getText(By locator) {
        return getElementText(locator);
    }

    /**
     * Determines if the element with the given locatorText is displayed.
     * <p>
     * This method will wait for the element to be present and visible before
     * returning true or false.
     * <p>
     * Note that this method returns true if the element is present and visible,
     * false if the element is not present or not visible.
     *
     * @param locatorText the text of the element to check
     * @return true if the element is present and visible, false otherwise
     */
    public boolean isTrue(String locatorText) {
        return isElementPresent(locatorText);
    }

    /**
     * Checks if the element specified by the given locator is enabled.
     * <p>
     * This method retrieves the element using the provided {@link By} locator
     * and returns true if the element is enabled, otherwise false.
     *
     * @param locator the {@link By} locator of the element to check
     * @return true if the element is enabled, otherwise false
     */
    public boolean isEnabled(By locator) {
        return getElement(locator).isEnabled();
    }

    /**
     * Perform a double tap on the element with the given locator.
     * <p>
     * This method is a convenience wrapper around {@link #doubleClick(By)}.
     *
     * @param locator the By locator of the element to double tap.
     */
    public void doubleTap(By locator) {
        doubleClick(locator);
    }

    /**
     * Perform a double tap on the element identified by the given locator text.
     * <p>
     * This method serves as a wrapper around {@link #doubleClick(String)},
     * allowing for a double tap action on the specified element.
     *
     * @param locatorText the text used to identify the element to double tap.
     */
    public void doubleTap(String locatorText) {
        doubleClick(locatorText);
    }

    /**
     * Long press the element identified by the given locator text.
     * <p>
     * This method is a convenience wrapper around {@link #longPressElement(By)}.
     * It finds the element using the given locator text and then performs a long press action on it.
     *
     * @param locatorText the text used to identify the element to long press.
     */
    @Override
    public void longPressElement(String locatorText) {
        super.longPressElement(locatorText);
    }

    /**
     * Long press the element identified by the given locator.
     * <p>
     * This method overrides the base class implementation to perform a
     * long press action on the element found using the specified locator.
     *
     * @param locator the By locator used to identify the element to long press.
     */
    @Override
    public void longPressElement(By locator) {
        super.longPressElement(locator);
    }

    /**
     * Retrieves a list of all the elements that match the given
     * By locator.
     *
     * @param values the By locator used to identify the elements
     * @return list of elements that match the given locator
     */
    public List<WebElement> getValues(By values) {
        return getElements(values);
    }

    /**
     * Retrieves a list of values from the element identified by the given By locator.
     * <p>
     * The element's content is retrieved and split into an array of strings
     * using the newline character as the delimiter.
     * <p>
     * This method is useful for retrieving a list of options from a dropdown
     * or a list of values from a table.
     *
     * @param list the By locator used to identify the element containing the values
     * @return list of values as a String array
     */
    public String[] getValuesList(By list) {
        return waitAndGetContent(list).split("\n");
    }

    /**
     * Retrieves a list of values from the element identified by the given string.
     * <p>
     * The element's content is retrieved using the specified string locator value
     * and split into an array of strings using the newline character as the delimiter.
     * <p>
     * This method is useful for extracting a list of values from elements
     * such as dropdowns or tables, where the content is identified by a string value.
     *
     * @param list the string used to identify the element containing the values
     * @return list of values as a String array
     */
    public String[] getValuesList(String list) {
        return getElementContentByValue(list).split("\n");
    }


    /**
     * Checks if the checkbox element specified by the given locator is checked.
     * <p>
     * This method retrieves the checkbox element using the provided {@link By} locator
     * and returns true if the checkbox is checked, otherwise false.
     *
     * @param locator the {@link By} locator of the checkbox element to check
     * @return true if the checkbox is checked, otherwise false
     */
    public boolean isChecked(By locator) {
        return checkboxValidator(locator);
    }


}