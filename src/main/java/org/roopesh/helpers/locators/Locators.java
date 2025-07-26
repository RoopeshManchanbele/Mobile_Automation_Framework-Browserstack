package org.roopesh.helpers.locators;


import com.google.common.base.Preconditions;
import io.appium.java_client.AppiumBy;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.roopesh.config.Config;
import org.roopesh.config.Config.ConfigProperty;
import org.roopesh.customExceptions.PageElementException;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Locator is a model class that helps convert JSON locators to Java objects.
 * Locator class consist of map which store the element.
 * <pre>
 *     locatorName--->platform---|-->locator value
 *                               |-->locator type
 *  </pre>
 * Example:
 * <pre>{@code elements:
 *   aurigoMasterWorkLogo:
 *     ios:
 *       type: id
 *       value: "Aurigo MasterWorks Logo"}</pre>
 */
@Data
public class Locators {
    private static final Logger LOGGER = LoggerFactory.getLogger(Locators.class);

    private final Map<String, Map<String, Map<String, String>>> elements = new ConcurrentHashMap<>();

    private static final String LOCATOR_TYPE = "type";
    private static final String LOCATOR_VALUE = "value";
    private static final String INVALID_LOCATOR_ERR_MSG = "Locator cannot be null (or) empty.";

    /**
     * Method helps to get the locator value in string format with given name and platform.
     *
     * @param key      {@link String} locator name.
     * @param platform {@link String} platform name.
     * @return {@link String} String formatted locator value.
     */
    public String getStringLocator(String key, String platform) {
        if (!this.elements.containsKey(key)) {
            String message = String.format("%s locator missing in this page", key);
            LOGGER.error(message);
            throw new PageElementException(message);
        }
        Map<String, Map<String, String>> platformLocators = this.elements.get(key);
        if (!platformLocators.containsKey(platform)) {
            String message = String.format("%s locator missing %s platform in this page", key, platform);
            LOGGER.error(message);
            throw new PageElementException(message);
        }
        Map<String, String> locatorTypeValueMap = platformLocators.get(platform);
        return String.format("%s=%s", locatorTypeValueMap.get(LOCATOR_TYPE), locatorTypeValueMap.get(LOCATOR_VALUE));
    }

    /**
     * Method helps to get the locator value in string format with given name.
     *
     * @param key {@link String} locator name.
     * @return {@link String} String formatted locator value.
     */
    public String getStringLocator(String key) {
        return getStringLocator(key, Config.getConfigProperty(Config.ConfigProperty.PLATFORM));
    }

    public String getLocatorType(String key) {
        String platform = Config.getConfigProperty(Config.ConfigProperty.PLATFORM);
        if (!this.elements.containsKey(key)) {
            String message = String.format("%s locator missing in this page", key);
            LOGGER.error(message);
            throw new PageElementException(message);
        }
        Map<String, Map<String, String>> platformLocators = this.elements.get(key);
        if (!platformLocators.containsKey(platform)) {
            String message = String.format("%s locator missing %s platform in this page", key, platform);
            LOGGER.error(message);
            throw new PageElementException(message);
        }
        Map<String, String> locatorTypeValueMap = platformLocators.get(platform);
        return String.format("%s", locatorTypeValueMap.get(LOCATOR_TYPE));
    }

    /**
     * Method to get the By type locator with a given name and platform.
     * Example:
     * <pre>{@code
     * Locator locator=LocatorHelper.getLocators("LoginPage.yaml");
     * locator.get("userNameField","ios")}</pre>
     *
     * @param key      {@link String} locator name.
     * @param platform {@link String} platform name.
     * @return {@link By} By type locator.
     */
    public By get(String key, String platform) {
        return resolveByType(getStringLocator(key, platform));
    }

    /**
     * Method to get the By type locator with a given name, platform and addition String values.
     * Example:
     * <pre>{@code
     * Locator locator=LocatorHelper.getLocators("LoginPage.yaml");
     * locator.get("userName","ios",new String[]{"Elon Musk"});}</pre>
     *
     * @param key      {@link String} locator name.
     * @param platform {@link String} platform name.
     * @param format   {@link String} addition string value that replace the value.
     * @return {@link By} By type locator.
     */
    public By get(String key, String platform, String[] format) {
        String locator = getStringLocator(key, platform);
        MessageFormat messageFormat = new MessageFormat(locator);
        String formatLocator = messageFormat.format(format);
        return resolveByType(formatLocator);
    }

    /**
     * Method to get the By type locator with a given name.
     * Example:
     * <pre>{@code
     * Locator locator=LocatorHelper.getLocators("LoginPage.yaml");
     * locator.get("userNameField");}</pre>
     *
     * @param key {@link String} locator name.
     * @return {@link By} By type locator.
     */
    public By get(String key) {
        String platform = Config.getConfigProperty(ConfigProperty.PLATFORM);
        return get(key, platform);
    }

    /**
     * Method to resolve the By class and return the generic By type.
     * Example:
     * <pre>{@code
     * Locator locator=LocatorHelper.getLocators("LoginPage.yaml");
     * locator.get("userNameField");}</pre>
     *
     * @param locator {@link String} locator name to get the By type locator.
     * @return {@link By} By type locator.
     */
    public By resolveByType(String locator) {
        Preconditions.checkArgument(StringUtils.isNotBlank(locator), INVALID_LOCATOR_ERR_MSG);
        By locatorBy;
        locator = locator.trim();
        locatorBy = getElementType(locator);
        return locatorBy;
    }

    /**
     * Method to get the element type and return the generic By type.
     * Example:
     * <pre>{@code
     * Locator locator=LocatorHelper.getLocators("LoginPage.yaml");
     * locator.get("userNameField");}</pre>
     *
     * @param locator {@link String} locator name to get the By type locator.
     * @return {@link By} By type locator.
     */
    private By getElementType(String locator) {
        Preconditions.checkArgument(StringUtils.isNotBlank(locator), INVALID_LOCATOR_ERR_MSG);
        By valueToReturn = null;
        String seleniumLocator = locator.trim();
        int typeDelimiterIndex = seleniumLocator.indexOf('=');
        String locatorType = typeDelimiterIndex != -1 ? seleniumLocator.substring(0, typeDelimiterIndex) : seleniumLocator;
        switch (locatorType) {
            case "id":
                valueToReturn = AppiumBy.id(seleniumLocator.substring(typeDelimiterIndex + 1));
                break;
            case "name":
                valueToReturn = AppiumBy.name(seleniumLocator.substring(typeDelimiterIndex + 1));
                break;
            case "link":
                valueToReturn = AppiumBy.linkText(seleniumLocator.substring(typeDelimiterIndex + 1));
                break;
            case "xpath":
                valueToReturn = AppiumBy.xpath(seleniumLocator.substring(typeDelimiterIndex + 1));
                break;
            case "css":
                valueToReturn = AppiumBy.cssSelector(seleniumLocator.substring(typeDelimiterIndex + 1));
                break;
            case "classname":
                valueToReturn = AppiumBy.className(seleniumLocator.substring(typeDelimiterIndex + 1));
                break;
            case "accessibilityId":
                valueToReturn = AppiumBy.accessibilityId(seleniumLocator.substring(typeDelimiterIndex + 1));
            default:
                if (seleniumLocator.startsWith("/") || seleniumLocator.startsWith("./")) {
                    valueToReturn = By.xpath(seleniumLocator);
                    break;
                }
        }
        return valueToReturn;
    }

}
