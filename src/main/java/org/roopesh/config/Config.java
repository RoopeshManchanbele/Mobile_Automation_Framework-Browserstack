package org.roopesh.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.configuration2.XMLConfiguration;
import org.roopesh.constants.FileConstant;
import org.roopesh.helpers.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Config class is used to access the config file properties
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Config {
    private static final Logger LOGGER = LoggerFactory.getLogger(Config.class);
    private static final String CONFIG_FILE_ERROR = "Config property cannot be null";
    public static Properties properties = new Properties();
    private static volatile XMLConfiguration xmlConfig;

    /**
     * Gets the {@link XMLConfiguration} that stores all the configuration values.
     * This method is thread-safe and will return the same instance of
     * {@link XMLConfiguration} across all threads.
     * <p>
     * The configuration is initialized lazily, meaning that the configuration file
     * is only read when this method is called for the first time.
     * <p>
     * Subsequent calls to this method will return the same instance of
     * {@link XMLConfiguration} without re-reading the configuration file.
     *
     * @return the {@link XMLConfiguration} instance.
     */
    static XMLConfiguration getConfig() {
        if (xmlConfig != null) {
            synchronized (XMLConfiguration.class) {
                return xmlConfig;
            }
        }
        initConfig();
        return xmlConfig;
    }

    /**
     * Reads and parses configuration file Initializes the configuration, reloading all data.
     */
    public static synchronized void initConfig() {
        Map<ConfigProperty, String> initialValues = new EnumMap<>(ConfigProperty.class);
        initConfig(initialValues);
    }

    /**
     * Returns the value of the specified configuration property.
     *
     * @param configProperty the property name.
     * @return the value of the specified configuration property.
     * @throws NullPointerException if {@code configProperty} is null.
     */
    public static String getConfigProperty(String configProperty) {
        try {
            properties.load(FileHelper.loadFile(FileConstant.DEFAULT_CONFIG_FILE));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        checkArgument(configProperty != null, CONFIG_FILE_ERROR);
        return properties.getProperty(configProperty);
    }

    /**
     * Convenience method to get a config property using an Enum value.
     * See {@link #getConfigProperty(String)} for details.
     *
     * @param configProperty the config property Enum value
     * @return the value of the specified configuration property
     * @throws NullPointerException if {@code configProperty} is null
     */
    public static String getConfigProperty(final ConfigProperty configProperty) {
        return getConfigProperty(configProperty.getName());
    }

    /**
     * Initializes the configuration, reloading all data.
     * <p>
     * This method is thread-safe and will only create a new instance of
     * {@link XMLConfiguration} if it has not been created before.
     * <p>
     * The configuration file is read only once, when this method is called
     * for the first time. Subsequent calls to this method will return the
     * same instance of {@link XMLConfiguration} without re-reading the
     * configuration file.
     * <p>
     * If supplied, the initial values in the {@code initialValues} map
     * will be set in the configuration. If the value is not present in the
     * map, the default value for that property will be used.
     *
     * @param initialValues the initial values for the configuration
     */
    public static synchronized void initConfig(final Map<ConfigProperty, String> initialValues) {

        // only do this if the global config is not already initialized.
        if (xmlConfig == null) {
            xmlConfig = new XMLConfiguration();
            // don't auto throw, let each config value decide
            xmlConfig.setThrowExceptionOnMissing(false);
            // because we can config on the fly, don't auto-save
            // Set defaults
            loadInitialValues();
        }

        // Load in our supplied values (if defined)
        loadValuesFromUser(initialValues);
        initDefaultPropertyFile();
    }

    /**
     * Loads the provided initial configuration values into the XML configuration.
     * <p>
     * This method iterates over the given map of initial values and sets each
     * configuration property in the {@link XMLConfiguration} instance. If the map
     * is empty, no properties are set.
     *
     * @param initialValues a map of {@link ConfigProperty} keys and their associated
     *                      string values to be loaded into the configuration.
     */
    private static void loadValuesFromUser(final Map<ConfigProperty, String> initialValues) {
        if (!initialValues.isEmpty()) {
            for (Map.Entry<ConfigProperty, String> eachConfig : initialValues.entrySet()) {
                xmlConfig.setProperty(eachConfig.getKey().getName(), eachConfig.getValue());
            }
        }
    }

    /**
     * Sets all configuration properties to their default values.
     * <p>
     * This method iterates over all {@link ConfigProperty} values and sets each
     * configuration property in the {@link XMLConfiguration} to its default value.
     */
    private static void loadInitialValues() {
        for (ConfigProperty configProps : ConfigProperty.values()) {
            xmlConfig.setProperty(configProps.getName(), configProps.getDefaultValue());
        }
    }


    /**
     * Loads the default configuration file into the global configuration.
     * <p>
     * This method loads the default configuration file specified by the
     * {@link FileConstant#DEFAULT_CONFIG_FILE} constant and sets the
     * configuration properties in the {@link XMLConfiguration} instance
     * accordingly. If the file is not present or cannot be loaded, an
     * error message is logged, but the configuration is not modified.
     */
    private static synchronized void initDefaultPropertyFile() {
        Properties properties = new Properties();
        try {
            properties.load(FileHelper.loadFile(FileConstant.DEFAULT_CONFIG_FILE));
        } catch (IOException ioException) {
            LOGGER.error("Given file have {} issue", ioException.getMessage());
        }
        for (Map.Entry<Object, Object> property : properties.entrySet()) {
            xmlConfig.setProperty(String.valueOf(property.getKey()), property.getValue());
        }
    }

    /**
     * Sets a configuration value. This is useful when you want to override or set a setting.
     *
     * @param configProperty      The configuration element to set
     * @param configPropertyValue The value of the configuration element
     */
    public static synchronized void setConfigProperty(ConfigProperty configProperty, Object configPropertyValue) {
        checkArgument(configProperty != null, "Config property cannot be null.");
        checkArgument(configPropertyValue != null, "Config property value cannot be null.");
        getConfig().setProperty(configProperty.getName(), configPropertyValue);
    }

    /**
     * Gets the boolean value of a configuration property.
     *
     * @param configProperty The property to retrieve.
     * @return The boolean value of the property.
     */
    public static boolean getBoolConfigProperty(ConfigProperty configProperty) {
        return getBoolConfigProperty(configProperty.getName());
    }

    /**
     * Retrieves the boolean value of a configuration property from the configuration.
     *
     * @param configProperty The name of the configuration property to retrieve.
     * @return The boolean value of the configuration property.
     * @throws NullPointerException If the given configProperty is null.
     */
    public static boolean getBoolConfigProperty(String configProperty) {
        checkArgument(configProperty != null, CONFIG_FILE_ERROR);
        return getConfig().getBoolean(configProperty);
    }

    @Getter
    public enum ConfigProperty {

        /**
         * If this parameter is <b>true</b>, then the test will run in the local machine.
         * The default is set to <b>true</b>, which means that tests will always run in the local machine.
         * If the value is <b>false</b>, then the test will run in the remote machine.
         */
        APPIUM_RUN_LOCALLY("runLocally", "true"),


        ACCOUNT_ID("accountID", "XS02150"),


        API_BASE_URL("APIBASEURL", "http://internal-trading-internal-ALB-1101911574.ap-south-1.elb.amazonaws.com"),


        /**
         * The parameter specifies the locator directory for the test run
         * By default, this value is <b>defaultLocators</b><br>
         * Default locator path: src/test/resources/defaultLocators<br>
         * <b>Note:</b> keep locators in test resource folder
         */
        LOCATOR_DIRECTORY("locatorDirectory", "locators"),

        /**
         * The parameter specifies the locator directory for the test run
         * By default, this value is <b>testData</b><br>
         * Default locator path: src/test/resources/testData<br>
         * <b>Note:</b> keep locators in test resource folder
         */
        TESTDATA_DIRECTORY("testDataDirectory", "testData"),

        /**
         * This parameter specifies the platform name. The test will run on the specified platform.
         * The platform can be either <b>IOS</b> or <b>Android</b>.
         * By default, this value is <b>ios</b>
         */
        PLATFORM("platform", "android"),

        /**
         * This parameter specifies the path to the Appium Log file.
         * By default, this value is <b>{@link System#getProperty(String)} {system value="user.dir"}</b>
         */
        APPIUM_LOG_FILE("appiumLogFile", System.getProperty("user.dir") + File.separator + "Logs"),

        /**
         * This parameter specifies whether email reporting is required after the run completes.
         * By default, this value is <b>true</b>
         */
        IS_EMAIL_REPORT_ENABLED("isEmailReportingEnabled", "true"),
        /**
         * This parameter specifies the smtp server user password.
         * By default, this value is <b>Empty</b>
         */
        EXTENT_REPORT_PATH("extentReportPath", ""),

        /**
         * This parameter specifies the email addresses to send the run execution report to.
         * By default, this value is <b>qa_automationreports@fyers.in</b>
         */
        EMAIL_TO_LIST("toList", "qa_automationreports@fyers.in"),

        /**
         * This parameter specifies the email addresses to send the carbon copy run execution report to.
         * By default, this value is Empty
         */
        EMAIL_CC_LIST("ccList",""),

        /**
         * This parameter specifies the email address from which the report should be shared.
         * By default, this value is <b>it-assets@fyers.in</b>
         */
        EMAIL_FROM_ID("fromId", "it-assets@fyers.in"),

        /**
         * This parameter specifies the email smtp server name.
         * By default, this value is <b>smtppro.zoho.in</b>
         */
        EMAIL_REPORT_SMTP_SERVER("smtpHost", "smtppro.zoho.in"),

        /**
         * This parameter specifies the smtp port number.
         * By default, this value is <b>465</b>
         */
        EMAIL_REPORT_SMTP_PORT("smtpPort", "465"),

        /**
         * This parameter specifies the smtp server username.
         * By default, this value is <b>Empty</b>
         */
        EMAIL_REPORT_SMTP_USER_NAME("smtpUserName", ""),

        /**
         * This parameter specifies the smtp server user password.
         * By default, this value is <b>Empty</b>
         */
        EMAIL_REPORT_SMTP_PASSWORD("smtpPassword", ""),
        /**
         * This parameter specifies the socket Factory Port Number.
         * By default, this value is <b>465</b>
         */
        EMAIL_REPORT_SOCKET_FACTORY_PORT("socketFactoryPort", "465"),
        /**
         * This parameter specifies the Socket Factory Class.
         * By default, this value is <b>javax.net.ssl.SSLSocketFactory</b>
         */
        EMAIL_REPORT_SOCKET_FACTORY_CLASS("socketFactoryClass", "javax.net.ssl.SSLSocketFactory"),
        /**
         * This parameter specifies the SMTP Socket Factory Fallback.
         * By default, this value is <b>Empty</b>
         */
        SMTP_SOCKET_FACTORY_FALLBACK("smtp.socketFactoryFallback", "false"),
        /**
         * This parameter specifies the POP3 Socket Factory Fallback.
         * By default, this value is <b>false</b>
         */
        POP3_SOCKET_FACTORY_FALLBACK("pop3.socketFactoryFallback", "false"),
        /**
         * This parameter specifies the Mail Store Protocol.
         * By default, this value is <b>pop3</b>
         */
        MAIL_STORE_PROTOCOL("mailStoreProtocol", "pop3"),
        /**
         * This parameter specifies the Transport Protocol.
         * By default, this value is <b>Empty</b>
         */
        TRANSPORT_PROTOCOL("transportProtocol", "smtp"),
        /**
         * This parameter specifies to enable TLS.
         * By default, this value is <b>true</b>
         */
        START_TLS_ENABLE("startTLSEnable", "true"),
        /**
         * This parameter specifies the SMTP Authentication.
         * By default, this value is <b>true</b>
         */
        SMTP_AUTH_ENABLE("smtpAuth", "true"),
        /**
         * This parameter specifies the Auth debugger.
         * By default, this value is <b>true</b>
         */
        DEBUG_AUTH_ENABLE("debugAuth", "true"),
        /**
         * This parameter specifies the Mail Debug.
         * By default, this value is <b>true</b>
         */
        DEBUG_ENABLE("mail.debug", "true"),
        /**
         * This parameter specifies the Socket Factory Class.
         * By default, this value is <b>Empty</b>
         */
        SMTP_SSL_PROTOCOLS("smtp.sslProtocols", ""),
        /**
         * This parameter specifies the SMTP Connection Timeout.
         * By default, this value is <b>30 seconds</b>
         */
        SMTP_CONNECTION_TIMEOUT("smtp.connectionTimeout", "300000"),
        /**
         * This parameter enable/disable Retry Analyzer.
         * By default, this value is <b>true</b>
         */
        IS_RETRY_ENABLED("enableRetry", "true");


        private final String name;
        private final String defaultValue;

        ConfigProperty(final String name, final String defaultValue) {
            checkArgument(name != null, "Config property name can not be null.");
            checkArgument(defaultValue != null, "Config property default value cannot be null.");
            this.name = name;
            this.defaultValue = defaultValue;
        }


    }
}

