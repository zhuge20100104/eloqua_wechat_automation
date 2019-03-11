package com.oracle.auto.web.utility;

import com.oracle.auto.web.exceptions.WebAssertionError;
import com.oracle.auto.web.multithread.SeleniumPageFactory;
import org.junit.Assert;

import java.util.List;

public class WebAssert {
	private static final String omitMask = PropertyConfiger.instance().getEnvData("assert.string.omit.mask", "...");
	
	static public void assertTrue(boolean condition) {
		assertTrue("", condition);
	}

	static public void assertTrue(String message, boolean condition) {
		try {
			Assert.assertTrue(message, condition);
		} catch (AssertionError error) {
			if (SeleniumPageFactory.instance().getLastPage() != null)
				throw new WebAssertionError(SeleniumPageFactory.instance().getLastPage(),error);
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
			if (SeleniumPageFactory.instance().getLastPage() != null)
				throw new WebAssertionError(SeleniumPageFactory.instance().getLastPage(),error);
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
			if (SeleniumPageFactory.instance().getLastPage() != null)
				throw new WebAssertionError(SeleniumPageFactory.instance().getLastPage(),error);
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
			if (SeleniumPageFactory.instance().getLastPage() != null)
				throw new WebAssertionError(SeleniumPageFactory.instance().getLastPage(),error);
			throw error;
		}
	}

	// expected: "abcd1234", actual: "abcd..." is aso right
	public static void assertEqualsOmit(String message, String expected, String actual) {
		if (!actual.endsWith(omitMask)) {
			assertEquals(expected, actual);
		} else {
			String subStr = actual.substring(0, actual.length()-omitMask.length());
			assertTrue("message are not as expected by omit check. expected = [" + expected + "], actual = [" + actual + "].", expected.startsWith(subStr));
		}
	}
	
	public static void assertEqualsOmit(String expected, String actual) {
		assertEqualsOmit("", expected, actual);
	}
	
	static public void assertEquals(String expected, String actual) {
		assertEquals("", expected, actual);
	}

	static public void assertNotNull(Object object) {
		try {
			Assert.assertNotNull(object);
		} catch (AssertionError error) {
			if (SeleniumPageFactory.instance().getLastPage() != null)
				throw new WebAssertionError(SeleniumPageFactory.instance().getLastPage(),error);
			throw error;
		}
	}

	public static void assertEquals(String message, boolean expected,
			boolean actual) {
		try {
			Assert.assertEquals(message, expected, actual);
		} catch (AssertionError error) {
			if (SeleniumPageFactory.instance().getLastPage() != null)
				throw new WebAssertionError(SeleniumPageFactory.instance().getLastPage(),error);
			throw error;
		}
	}
	
	// also support regular expression match (by order)
	public static void assertEquals(String message, List<String> expected, List<String> actual) {
		assertTrue(message + " , list count are different, expected = " + expected + " actual = " + actual, expected.size() == actual.size());
		int size = expected.size();
		for (int i = 0; i < size; ++i) {
			assertEquals(message + ", index = " + i, expected.get(i), actual.get(i));
		}
	}

	// no support regular expression match because ignoring order will bring potential logic error to compare multiple items
	public static void assertEqualsIgnoreOrder(String message, List<String> expected, List<String> actual) {
		assertTrue(message + " list count are different, expected = " + expected + " actual = " + actual, expected.size() == actual.size());
		assertTrue(message + " expected = " + expected + " actual = " + actual, actual.containsAll(expected));
	}
	// no support regular expression match because ignoring order will bring potential logic error to compare multiple items
	public static void assertContains(String message, List<String> expected, List<String> actual) {
		assertTrue(message + " expected = " + expected + " actual = " + actual, actual.containsAll(expected));
	}

	public static void assertContains(String message, String expected, String actual) {
		assertTrue(message + " expected = " + expected + " actual = " + actual, actual.contains(expected));
	}

	public static void assertNotContains(String message, List<String> expected, List<String> actual) {
		assertTrue(message + " expected = " + expected + " actual = " + actual, !actual.containsAll(expected));
	}

}
