package utilities.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Mark a class or a method as part of the test.
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({METHOD, TYPE})
public @interface MobileTest {


    /**
     * The name of the module for which the testcases are being executed.
     *
     * @return the value (default empty)
     */
    String moduleName() default "";


    /**
     * The total number of manual testcases that are available.
     *
     * @return the value (default 0)
     */
    int manualTestcaseCount() default 0;

    /**
     * The total number of manual testcases that are automation friendly.
     *
     * @return the value (default 0)
     */
    int automatableTestcaseCount() default 0;

    /**
     * The total number of automation friendly testcases that are automated.
     *
     * @return the value (default 0)
     */
    int automatedCount() default 0;

}
