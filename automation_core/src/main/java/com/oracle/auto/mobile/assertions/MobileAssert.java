package com.oracle.auto.mobile.assertions;

import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.mobile.exceptions.MobileAssertionError;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.junit.Assert;

import java.util.List;

public class MobileAssert {

    private static final String omitMask = PropertyConfiger.instance().getEnvData("assert.string.omit.mask", "...");

    static public void assertTrue(boolean condition) {
        assertTrue("", condition);
    }

    public static void assertTrue(String message, boolean condition) {
        try {
            Assert.assertTrue(message, condition);
        } catch (AssertionError error) {
            if (MobileDriverFactory.instance().getLastMobileDriverSession() != null)
                throw new MobileAssertionError(MobileDriverFactory.instance().getLastMobileDriverSession(), error);
            throw error;
        }
    }

    static public void assertFalse(boolean condition) {
        assertFalse("", condition);
    }

    static public void assertFalse(String message, boolean condition) {
        try {
            Assert.assertFalse(message, condition);
        } catch (AssertionError error) {
            if (MobileDriverFactory.instance().getLastMobileDriverSession() != null)
                throw new MobileAssertionError(MobileDriverFactory.instance().getLastMobileDriverSession(), error);
            throw error;
        }
    }

    static public void assertEquals(long expected, long actual) {
        assertEquals("", expected, actual);
    }

    public static void assertEquals(String message, long expected, long actual) {
        try {
            Assert.assertEquals(message, expected, actual);
        } catch (AssertionError error) {
            if (MobileDriverFactory.instance().getLastMobileDriverSession() != null)
                throw new MobileAssertionError(MobileDriverFactory.instance().getLastMobileDriverSession(), error);
            throw error;
        }
    }

    public static void doAssertEqualReg(String message, String expected, String actual) {
        if (expected.startsWith("(") && expected.endsWith(")")) {
            Assert.assertTrue(message + " expected = " + expected + " actual = " + actual,
                    actual.matches(expected));

        } else {
            Assert.assertEquals(message, expected, actual);
        }
    }

    public static void assertEquals(String message, String expected, String actual) {
        try {
            doAssertEqualReg(message, expected, actual);
        } catch (AssertionError error) {
            if (MobileDriverFactory.instance().getLastMobileDriverSession() != null)
                throw new MobileAssertionError(MobileDriverFactory.instance().getLastMobileDriverSession(), error);
            throw error;
        }
    }

    static public void assertEquals(String expected, String actual) {
        assertEquals("", expected, actual);
    }

    static public void assertNotNull(Object object) {
        try {
            Assert.assertNotNull(object);
        } catch (AssertionError error) {
            if (MobileDriverFactory.instance().getLastMobileDriverSession() != null)
                throw new MobileAssertionError(MobileDriverFactory.instance().getLastMobileDriverSession(), error);
            throw error;
        }
    }

    public static void assertEquals(String message, boolean expected,
                                    boolean actual) {
        try {
            Assert.assertEquals(message, expected, actual);
        } catch (AssertionError error) {
            if (MobileDriverFactory.instance().getLastMobileDriverSession() != null)
                throw new MobileAssertionError(MobileDriverFactory.instance().getLastMobileDriverSession(), error);
            throw error;
        }
    }

    // no support regular expression match because ignoring order will bring potential logic error to compare multiple items
    public static void assertEqualsIgnoreOrder(String message, List<String> expected, List<String> actual) {
        assertTrue(message + " list count are different, expected = " + expected + " actual = " + actual, expected.size() == actual.size());
        assertTrue(message + " expected = " + expected + " actual = " + actual, actual.containsAll(expected));
    }

    // no support regular expression match because ignoring order will bring potential logic error to compare multiple items
    public static void assertContains(String message, String expected, String actual) {
        int result = actual.indexOf(expected);
        boolean condition = false;
        if (actual.indexOf(expected) > -1){condition=true;}
        assertTrue(message + " expected = " + expected + " actual = " + actual, condition);
    }

}
