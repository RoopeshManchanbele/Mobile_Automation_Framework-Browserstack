package org.roopesh.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * The File Constant Object helps to declare all the file names that are used in the ATF Mobile project.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileConstant {
    public static final String DEFAULT_CONFIG_FILE = "src/main/resources/config/config.properties";
    public static final String SYSTEM_DIRECTORY = System.getProperty("user.dir");
    public static final String REPORT_FOLDER = SYSTEM_DIRECTORY + File.separator + "target/report";
    public static final String REPORT_PATH = REPORT_FOLDER + File.separator + "%s/Automation.html";
    public static final String HTML_CONTENT_PATH = SYSTEM_DIRECTORY + File.separator + "/src/main/resources/htmlContent";

}
