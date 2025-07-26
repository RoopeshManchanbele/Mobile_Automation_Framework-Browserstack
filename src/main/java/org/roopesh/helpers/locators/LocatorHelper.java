package org.roopesh.helpers.locators;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.roopesh.config.Config;
import org.roopesh.config.Config.ConfigProperty;
import org.roopesh.helpers.FileHelper;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Locator Factory class to make using Page Objects simpler and easier.<br>
 * It helps to load the yaml locator and store in {@link Locators} object.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LocatorHelper {

    /**
     * It is used to initialize elements that are declared in the yaml file.
     *
     * @param path {@link String} The location of a YAML file to load
     * @return {@link Locators} Page instance it store all the locator
     */
    public static Locators getLocators(String path) {
        Constructor constructor = new Constructor(Locators.class, new LoaderOptions());
        TypeDescription typeDesc = new TypeDescription(Locators.class);
        typeDesc.addPropertyParameters("elements", String.class, Map.class);
        constructor.addTypeDescription(typeDesc);
        Yaml yamlFile = new Yaml(constructor);
        InputStreamReader fileInputStream = new InputStreamReader(FileHelper.loadFile(String.format("%s/%s", Config.getConfigProperty(ConfigProperty.LOCATOR_DIRECTORY), path)));
        Locators page = yamlFile.load(new BufferedReader(fileInputStream));
        try {
            fileInputStream.close();
        } catch (IOException e) {
            //ignore close error
        }
        return page;
    }

    /**
     * It is used to initialize elements that are declared in the yaml file.
     *
     * @param object {@link java.util.Objects} Class Object to get the locator path
     * @return {@link Locators} Page instance it store all the locator
     */
    public static Locators getLocators(Object object) {
        Constructor constructor = new Constructor(Locators.class, new LoaderOptions());
        TypeDescription typeDesc = new TypeDescription(Locators.class);
        typeDesc.addPropertyParameters("elements", String.class, Map.class);
        constructor.addTypeDescription(typeDesc);
        Yaml yamlFile = new Yaml(constructor);
        String pathName = object.getClass().getSimpleName();
        InputStreamReader fileInputStream = new InputStreamReader(FileHelper.loadFile(String.format("%s/%s.yaml", Config.getConfigProperty(ConfigProperty.LOCATOR_DIRECTORY), pathName)));
        Locators page = yamlFile.load(new BufferedReader(fileInputStream));
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

}
