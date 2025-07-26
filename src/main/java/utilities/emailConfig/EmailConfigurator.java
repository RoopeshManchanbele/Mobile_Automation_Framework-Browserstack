package utilities.emailConfig;

import org.roopesh.config.Config;
import org.roopesh.config.Config.ConfigProperty;

public class EmailConfigurator {

    public static String getHost() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_REPORT_SMTP_SERVER);
    }

    public static String getPort() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_REPORT_SMTP_PORT);
    }

    public static String getUsername() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_REPORT_SMTP_USER_NAME);
    }

    public static String getPassword() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_REPORT_SMTP_PASSWORD);
    }

    public static String getFrom() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_FROM_ID);
    }

    public static String getRecepientList() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_TO_LIST);
    }

    public static String getCCList() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_CC_LIST);
    }

    public static String getSocketFactoryPort() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_REPORT_SOCKET_FACTORY_PORT);
    }

    public static String getSocketFactoryClass() {
        return Config.getConfigProperty(ConfigProperty.EMAIL_REPORT_SOCKET_FACTORY_CLASS);
    }

    public static String getSmtpSocketFactoryFallback() {
        return Config.getConfigProperty(ConfigProperty.SMTP_SOCKET_FACTORY_FALLBACK);
    }

    public static String getPop3SocketFactoryFallback() {
        return Config.getConfigProperty(ConfigProperty.POP3_SOCKET_FACTORY_FALLBACK);
    }

    public static String getMailStoreProtocol() {
        return Config.getConfigProperty(ConfigProperty.MAIL_STORE_PROTOCOL);
    }

    public static String getTransportProtocol() {
        return Config.getConfigProperty(ConfigProperty.TRANSPORT_PROTOCOL);
    }

    public static String getStartTLSEnable() {
        return Config.getConfigProperty(ConfigProperty.START_TLS_ENABLE);
    }

    public static String getSmtpAuth() {
        return Config.getConfigProperty(ConfigProperty.SMTP_AUTH_ENABLE);
    }

    public static String getDebugAuth() {
        return Config.getConfigProperty(ConfigProperty.DEBUG_AUTH_ENABLE);
    }

    public static String getMailDebug() {
        return Config.getConfigProperty(ConfigProperty.DEBUG_ENABLE);
    }

    public static String getSSLProtocols() {
        return Config.getConfigProperty(ConfigProperty.SMTP_SSL_PROTOCOLS);
    }

    public static String getSMTPConnectionTimeout() {
        return Config.getConfigProperty(ConfigProperty.SMTP_CONNECTION_TIMEOUT);
    }


}
