package org.roopesh.actions;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.roopesh.appium.AppiumDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.Kind;
import org.openqa.selenium.interactions.PointerInput.Origin;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofMillis;
import static org.roopesh.appium.AppiumDriverManager.driver;

public class Gestures {
    public static Dimension screenSize;
    public static int screenHeight;
    public static int screenWidth;
    private static volatile Gestures instance;
    private static Rectangle bounds;
    private final Logger log = LogManager.getLogger(Gestures.class);

    public static Gestures getGestureInstance() {
        if (instance == null) {
            synchronized (Gestures.class) {
                if (instance == null) {
                    instance = new Gestures();
                }
            }
        }
        return instance;
    }


    /**
     * Swipes a given element left or right.
     *
     * @param element The element to swipe.
     * @param leftToRight Whether to swipe left to right (true) or right to left (false).
     */
    public static void swipeElement(WebElement element, boolean leftToRight) {

        if (AppiumDriverManager.driver() == null || element == null) {
            throw new IllegalArgumentException("AppiumDriver and WebElement cannot be null");
        }

        int elementWidth = element.getSize().width;
        int elementHeight = element.getSize().height;
        int elementY = element.getLocation().getY();

        int startX, endX;
        if (leftToRight) {
            startX = (int) (elementWidth * 0.1);
            endX = (int) (elementWidth * 0.9);
        } else {
            startX = (int) (elementWidth * 0.9);
            endX = (int) (elementWidth * 0.1);
        }
        int startY = elementY + (elementHeight / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(ofMillis(500), PointerInput.Origin.viewport(), endX, startY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver().perform(List.of(swipe));
    }

    /**
     * Hides the keyboard on the device if it is visible.
     * <p>
     * This method is a wrapper around the {@link AndroidDriver#hideKeyboard()} method.
     */
    public void hideKeyboard() {
        ((AndroidDriver) driver()).hideKeyboard();
    }

    /**
     * Clicks on the given {@link By} locator.
     * <p>
     * This method is a wrapper around the {@link WebDriver#findElement(By)} and
     * {@link WebElement#click()} methods.
     *
     * @param locator the {@link By} locator to click on
     */
    public void clickElement(By locator) {
        AppiumDriverManager.driver().findElement(locator).click();

    }

    /**
     * Tap on the right side of the given element.
     * <p>
     * The position of the tap is calculated as the center of the element plus a
     * quarter of the element's width.
     *
     * @param element the element to tap on
     */
    public void tapRightSideOfTheElement(WebElement element) {
        bounds = element.getRect();
        int centerX = bounds.getX() + (bounds.getWidth() / 2);
        int X = centerX + (bounds.getWidth() / 4);
        int Y = bounds.getY() + (bounds.getHeight() / 2);
        PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
        Sequence clickElement = new Sequence(finger, 0);
        clickElement.addAction(finger.createPointerMove(ofMillis(0), Origin.viewport(), X, Y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver().perform(List.of(clickElement));

    }

    /**
     * Tap on the left side of the given element.
     * <p>
     * The position of the tap is calculated as the center of the element minus a
     * quarter of the element's width.
     *
     * @param element the element to tap on
     */
    public void tapLeftSideOfTheElement(WebElement element) {
        bounds = element.getRect();
        int centerX = bounds.getX() + (bounds.getWidth() / 2);
        int X = centerX - (bounds.getWidth() / 4);
        int Y = bounds.getY() + (bounds.getHeight() / 2);
        PointerInput finger = new PointerInput(Kind.TOUCH, "finger");
        Sequence clickElement = new Sequence(finger, 0);
        clickElement.addAction(finger.createPointerMove(ofMillis(0), Origin.viewport(), X, Y))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver().perform(List.of(clickElement));

    }

    /**
     * Perform a long press action on the given element.
     * <p>
     * The long press is performed at the center of the element, and the duration
     * of the long press is 5 seconds.
     *
     * @param element the element to long press
     * @throws NoSuchElementException if the element is not found
     */
    public void longPress(WebElement element) {
        try {

            bounds = element.getRect();

            bounds = element.getRect();
            int X = bounds.getX() + (bounds.getWidth() / 2);
            int Y = (bounds.getY() + (bounds.getHeight() / 2));
            System.out.println(element + " Bounds :" + X + " " + Y);
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence longPress = new Sequence(finger, 1);
            longPress.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), X, Y));
            longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            longPress.addAction(new Pause(finger, Duration.ofSeconds(5)));
            longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver().perform(List.of(longPress));
        } catch (NoSuchElementException e) {
            log.error(this.getClass().getName(), "findElement", "Element not found" + element);
            throw e;
        }

    }

    /**
     * Performs a swipe from bottom to top of the screen.
     * <p>
     * The start and end positions are specified as a fraction of the screen height.
     * For example, if startY is 0.8 and endY is 0.2, the swipe will start at 80% of the
     * screen from the bottom and end at 20% of the screen from the bottom.
     *
     * @param startY the starting position of the swipe as a fraction of the screen height
     * @param endY the ending position of the swipe as a fraction of the screen height
     */
    public void swipeBottomToTop(double startY, double endY) {
        screenSize = driver().manage().window().getSize();
        screenHeight = screenSize.height;
        screenWidth = screenSize.width;

        int startx = screenWidth / 2;
        int starty = (int) (screenHeight * startY);
        int endy = (int) (screenHeight * endY);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence scrollDown = new Sequence(finger, 1);
        scrollDown.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), startx, starty))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(ofMillis(3000), PointerInput.Origin.viewport(), startx, endy))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver().perform(List.of(scrollDown));
    }

    /**
     * Perform a double click action on the given element.
     * <p>
     * The double click is performed at the center of the element, and the delay
     * between the two clicks is 100ms.
     *
     * @param element the element to double click
     * @throws NoSuchElementException if the element is not found
     */
    public void doubleClick(WebElement element) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        Rectangle bounds = element.getRect();
        int X = bounds.getX() + (bounds.getWidth() / 2);
        int Y = (bounds.getY() + (bounds.getHeight() / 2));

        Sequence doubleClick = new Sequence(finger, 1);

        doubleClick.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), X, Y));
        doubleClick.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        doubleClick.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        doubleClick.addAction(new Pause(finger, Duration.ofMillis(100)));
        doubleClick.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        doubleClick.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver().perform(List.of(doubleClick));
    }

    /**
     * Navigates back in the browser history.
     * <p>
     * This is equivalent to pressing the back button in the browser.
     */
    public void navigateBack() {
        driver().navigate().back();
    }

    /**
     * Opens the notification panel on an Android device.
     * <p>
     * This is useful for checking notifications that are not accessible through the app UI.
     */
    public void checkAndroidNotification() {
        ((AndroidDriver) driver()).openNotifications();
    }

    /**
     * Perform a custom swipe action on the given element.
     * <p>
     * The custom swipe is performed from right to left, and the number of times
     * the swipe is performed is specified by the scrollTimes parameter.
     * <p>
     * The swipe is performed at the center of the element, and the start
     * position is at 20% of the screen width from the right edge, and the end
     * position is at 90% of the screen width from the left edge.
     * <p>
     * The duration of the swipe is 3 seconds.
     *
     * @param element the element to swipe
     * @param scrollTimes the number of times to swipe
     * @throws NoSuchElementException if the element is not found
     */
    public void customSwipeRight(WebElement element, int scrollTimes) {
        try {
            Rectangle bounds = element.getRect();
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Dimension screenSize = driver().manage().window().getSize();
            int screenWidth = screenSize.width;

            int x = (int) (screenWidth * 0.2);
            int y = bounds.getY() + (bounds.getHeight() / 2);

            int endx = (int) (screenWidth * 0.9);
            int i = 0;
            while (i < scrollTimes) {
                i++;
                Sequence swipe = new Sequence(finger, 1);
                swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), endx, y));
                swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
                swipe.addAction(finger.createPointerMove(Duration.ofMillis(3000), PointerInput.Origin.viewport(), x, y));
                swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
                driver().perform(List.of(swipe));

            }
        } catch (Exception e) {
            log.error("An error occurred", e);
        }
    }

}