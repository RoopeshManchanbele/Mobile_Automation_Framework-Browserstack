package utilities.retryUtility;

import org.roopesh.config.Config;
import org.roopesh.config.Config.ConfigProperty;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        if (Config.getBoolConfigProperty(ConfigProperty.IS_RETRY_ENABLED)) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
