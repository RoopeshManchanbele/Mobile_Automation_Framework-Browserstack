package org.roopesh.helpers;

import org.testng.Assert;

/**
 * Created By: Roopesh
 * Created Date: 21-11-2024
 */
public class AssertHelper {

    /**
     * This method is used to assert a pass condition.
     * It will pass the test regardless of the condition.
     * The message provided will be displayed in the test report.
     *
     * @param message The message to be displayed in the report.
     */
    public static void assertPass(String message) {
        Assert.assertTrue(true, message);
    }

    /**
     * Asserts that the condition is true and logs the given message if not.
     * If the condition is true, the test will pass.
     * If the condition is false, the test will fail and the given message will be shown in the test report.
     *
     * @param condition The condition to be checked.
     * @param message The message to be displayed in the report.
     */
    public static void assertPass(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    /**
     * Asserts that the condition is true. If the condition is false, the test will fail.
     * <p>
     * This method is a convenience method for {@link Assert#assertTrue(boolean)}.
     * <p>
     * The benefit of using this method over {@link Assert#assertTrue(boolean)} is that this method does not
     * require providing a message. Therefore, this method is more concise and easier to read.
     *
     * @param condition The condition to be checked.
     */
    public static void assertPass(boolean condition) {
        Assert.assertTrue(condition);
    }

    /**
     * This method is used to assert a failure condition.
     * It will fail the test and log the provided message in the test report.
     *
     * @param message The message to be displayed in the report.
     */
    public static void assertFail(String message) {
        Assert.fail(message);
    }

    /**
     * Asserts that two strings are equal. If they are not equal, the test will fail.
     * <p>
     * This method is a convenience method for {@link Assert#assertEquals(Object, Object)}.
     * <p>
     * The benefit of using this method over {@link Assert#assertEquals(Object, Object)} is that this method does not
     * require providing a message. Therefore, this method is more concise and easier to read.
     *
     * @param actual   The actual string to be compared.
     * @param expected The expected string to be compared.
     */
    public static void assertEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected);
    }

}
