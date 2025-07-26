package org.roopesh;

import org.roopesh.appium.AppiumDriverManager;
import org.roopesh.customExceptions.PageElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseFramework {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseFramework.class);
    private static final Map<WebDriver, Map<String, Object>> INSTANCE_MAP = new HashMap<>();


    /**
     * Method to create/fetch a new POM page instance
     *
     * @param page {@link Class} POM page
     * @param <B>  B is subclass of BasePage
     * @return {@link Class} New Instance of the page class
     */

    public synchronized <B extends BasePage> B getPage(Class<B> page) {
        return getInstance(page, AppiumDriverManager.driver());
    }

    /**
     * Method to create/fetch a new POM page instance
     *
     * @param page   {@link Class} POM page
     * @param driver {@link WebDriver} Web driver object
     * @param <B>    B is subclass of BasePage
     * @return {@link Class} New Instance of the page class
     */
    @SuppressWarnings("unchecked")
    private synchronized <B extends BasePage> B getInstance(Class<B> page, WebDriver driver) {
        if (driver == null) {
            LOGGER.error("Driver object is NULL for {} class.", page.getName());
            return null;
        }
        Map<String, Object> driverMap = INSTANCE_MAP.computeIfAbsent(driver, _ -> new HashMap<>());
        Object variable = driverMap.get(page.getName());
        if (variable == null) {
            try {
                variable = page.getDeclaredConstructor().newInstance();
                driverMap.put(page.getName(), variable);
            } catch (Exception e) {
                throw new PageElementException(e);
            }
        }
        return (B) variable;
    }

    /**
     * Method to delete the newly created instance after the test execution is completed.
     *
     * @param driver {@link WebDriver} current page web driver instance
     */
    public synchronized void deleteInstances(WebDriver driver) {
        INSTANCE_MAP.remove(driver);
    }
}
