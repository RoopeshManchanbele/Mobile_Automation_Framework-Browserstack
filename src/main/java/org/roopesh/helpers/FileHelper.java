package org.roopesh.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

    /**
     * Method to load the resource files and convert to input stream.
     *
     * @param fileName {@link String} file name to load.
     * @return {@link InputStreamReader} A file that has been converted to an input stream format.
     */
    public static InputStream loadFile(String fileName) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream iStream = loader.getResourceAsStream(fileName);
        if (iStream == null) {
            try {
                iStream = new FileInputStream(fileName);
            } catch (FileNotFoundException e) {
                LOGGER.error(e.getMessage());
            }
        }
        if (iStream == null) {
            throw new IllegalArgumentException("[" + fileName + "] is not a valid resource");
        }
        return iStream;
    }


}
