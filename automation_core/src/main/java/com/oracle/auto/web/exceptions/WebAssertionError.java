package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class WebAssertionError extends WebExceptionBase {

	public WebAssertionError(WebDriverSeleniumPageEx page, AssertionError error) {
		super(page, error.getMessage(), error);
	}
}
