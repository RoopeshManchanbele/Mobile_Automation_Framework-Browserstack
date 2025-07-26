package utilities.emailConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.roopesh.constants.FileConstant;
import org.roopesh.customExceptions.PageElementException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.time.Year;
import java.util.List;
import java.util.Properties;


public class Mailer {
    private static final String SMTP_HOST = "mail.smtp.host";
    private static final String SMTP_PORT = "mail.smtp.port";
    private static final String SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    private static final String SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    private static final String SOCKET_FACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    private static final String STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    private static final String SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_DEBUG = "mail.debug";
    private static final String MAIL_STORE_PROTOCOL = "mail.store.protocol";
    private static final String TRANSPORT_PROTOCOL = "mail.transport.protocol";
    private static final String DEBUG_AUTH = "mail.debug.auth";
    private static final String POP3_SOCKET_FACTORY_FALLBACK = "mail.pop3.socketFactory.fallback";
    private static final String SMTP_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout";
    private static final String MAIL_SMTP_PROTOCOLS = "mail.smtp.ssl.protocols";
    private static final Logger log = LogManager.getLogger(Mailer.class);
    private static Session session;

    /**
     * Send an email with a report attached.
     *
     * @param suiteName              The name of the test suite.
     * @param executionTime          The execution time of the test suite.
     * @param totalTests             The total number of tests.
     * @param passedTests            The number of passed tests.
     * @param failedTests            The number of failed tests.
     * @param skippedTest            The number of skipped tests.
     * @param failedTestCases        The list of failed test cases.
     * @param skippedTestcases       The list of skipped test cases.
     * @param moduleWiseTestCount    The list of module wise test count.
     * @param summaryCount           The list of summary count.
     * @param automationSummaryCount The list of automation summary count.
     */
    public static void sendEmail(String suiteName, String testRunName, String executionTime, int totalTests, int passedTests, int failedTests, int skippedTest, List<String> failedTestCases, List<String> skippedTestcases, List<Object> moduleWiseTestCount, List<List<Object>> summaryCount, List<Object> automationSummaryCount) {
        createEmailSession();
        try {
            String reportPath = System.getProperty("automationReportPath");
            File htmlReport = new File(reportPath);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailConfigurator.getFrom(), "Automation Team"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EmailConfigurator.getRecepientList()));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(EmailConfigurator.getCCList()));
            message.setSubject(suiteName);

            // Attach the report file
            MimeBodyPart attachment = new MimeBodyPart();
            attachment.attachFile(new File(reportPath));
            attachment.setFileName("Mobile Automation Report_" + System.getProperty("automationReportDate") + ".html");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(attachment);
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(createMailContent(suiteName, testRunName, executionTime, totalTests, passedTests, failedTests, skippedTest, failedTestCases, skippedTestcases, moduleWiseTestCount, summaryCount, automationSummaryCount), "text/html");
            multipart.addBodyPart(htmlPart);

            // Set the message content
            if (htmlReport.exists()) {
                System.out.println(reportPath);
            }
            message.setContent(multipart);
            log.info("Attempting to attach report from: {}", reportPath);

            Transport.send(message);
        } catch (MessagingException me) {
            throw new RuntimeException("Error sending email", me);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Create an email session for sending emails.
     * <p>
     * This method takes no parameters and sets up a JavaMail session with the
     * properties from the EmailConfigurator class.
     *
     * @see EmailConfigurator
     */
    public static void createEmailSession() {
        Properties emailProperties = new Properties();
        emailProperties.put(SMTP_HOST, EmailConfigurator.getHost());
        emailProperties.put(SMTP_PORT, EmailConfigurator.getPort());
        emailProperties.put(SOCKET_FACTORY_CLASS, EmailConfigurator.getSocketFactoryClass());
        emailProperties.put(SOCKET_FACTORY_FALLBACK, EmailConfigurator.getSmtpSocketFactoryFallback());
        emailProperties.put(STARTTLS_ENABLE, EmailConfigurator.getStartTLSEnable());
        emailProperties.put(SOCKET_FACTORY_PORT, EmailConfigurator.getSocketFactoryPort());
        emailProperties.put(SMTP_AUTH, EmailConfigurator.getSmtpAuth());
        emailProperties.put(MAIL_DEBUG, EmailConfigurator.getMailDebug());
        emailProperties.put(MAIL_STORE_PROTOCOL, EmailConfigurator.getMailStoreProtocol());
        emailProperties.put(TRANSPORT_PROTOCOL, EmailConfigurator.getTransportProtocol());
        emailProperties.put(DEBUG_AUTH, EmailConfigurator.getDebugAuth());
        emailProperties.put(POP3_SOCKET_FACTORY_FALLBACK, EmailConfigurator.getPop3SocketFactoryFallback());
        emailProperties.put(MAIL_SMTP_PROTOCOLS, EmailConfigurator.getSSLProtocols());
        emailProperties.put(SMTP_CONNECTION_TIMEOUT, EmailConfigurator.getSMTPConnectionTimeout());

        session = Session.getDefaultInstance(emailProperties, new javax.mail.Authenticator() {
            @Override
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConfigurator.getUsername(), EmailConfigurator.getPassword());
            }
        });
    }

    /**
     * Generate an HTML email report using a Velocity template.
     * <p>
     * The template is loaded from a file named {@code mailReport.vm} in the
     * {@link FileConstant#HTML_CONTENT_PATH} directory. The following data is merged
     * into the template:
     * <p> Parameters: </p>
     * <ul>
     * <li> {@code suiteName}: The name of the test suite.
     * <li> {@code executionTime}: The time taken to execute the test suite.
     * <li> {@code totalTests}: The total number of tests executed.
     * <li> {@code passedTests}: The number of tests that passed.
     * <li> {@code failedTests}: The number of tests that failed.
     * <li> {@code skippedTests}: The number of tests that were skipped.
     * <li> {@code currentYear}: The current year.
     * <li> {@code failedTestcases}: A list of failed test cases.
     * <li> {@code skippedTestcases}: A list of skipped test cases.
     * <li> {@code moduleWiseTests}: A list of objects containing module-wise test
     *      counts.
     * <li> {@code summaryCount}: A list of objects containing the summary count.
     * <li> {@code automationSummaryCount}: A list of objects containing the
     *      automation summary count.
     * </ul>
     * <p>
     * The generated HTML is returned as a string and can be used as the content of
     * an email message.
     */
    public static String createMailContent(String suiteName, String testRunName, String executionTime, int totalTests, int passedTests, int failedTests, int skippedTest, List<String> failedTestcases, List<String> skippedTestcases, List<Object> moduleWiseTestCount, List<List<Object>> summaryCount, List<Object> automationSummaryCount) {
        int currentYear = Year.now().getValue();
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "file");
        properties.setProperty("file.resource.loader.path", FileConstant.HTML_CONTENT_PATH);
        Velocity.init(properties);

        Template template = Velocity.getTemplate("mailReport.vm");

        VelocityContext context = new VelocityContext();
        context.put("suiteName", suiteName);
        context.put("testRunName", testRunName);
        context.put("executionTime", executionTime);
        context.put("totalTests", totalTests);
        context.put("passedTests", passedTests);
        context.put("failedTests", failedTests);
        context.put("skippedTests", skippedTest);
        context.put("currentYear", currentYear);
        context.put("failedTestcases", failedTestcases);
        context.put("skippedTestcases", skippedTestcases);
        context.put("moduleWiseTests", moduleWiseTestCount);
        context.put("summaryCount", summaryCount);
        context.put("automationSummaryCount", automationSummaryCount);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        try (FileWriter fileWriter = new FileWriter("email-template.html")) {
            fileWriter.write(writer.toString());
        } catch (Exception e) {
            throw new PageElementException(e);
        }

        return writer.toString();
    }

}
