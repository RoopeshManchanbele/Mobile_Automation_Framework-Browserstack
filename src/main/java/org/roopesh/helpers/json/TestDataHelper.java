package org.roopesh.helpers.json;

import com.google.gson.Gson;
import org.roopesh.config.Config;
import org.roopesh.helpers.FileHelper;

import java.io.IOException;
import java.io.InputStreamReader;

public class TestDataHelper {

    /**
     * It is used to initialize testData that are declared in the json file.
     *
     * @param object gets the name of the json File to be accessed
     * @return {@link TestData} Page instance it store all the testData
     */
    public static TestData getJSONData(Object object) {
        Gson gson = new Gson();
        String pathName = object.getClass().getSimpleName();
        InputStreamReader fileInputStream = new InputStreamReader(FileHelper.loadFile(String.format("%s/%s.json", Config.getConfigProperty(Config.ConfigProperty.TESTDATA_DIRECTORY), pathName)));
        TestData page = gson.fromJson(fileInputStream, TestData.class);
        try {
            fileInputStream.close();
        } catch (IOException e) {
            //ignore close error
        }
        return page;
    }

}
