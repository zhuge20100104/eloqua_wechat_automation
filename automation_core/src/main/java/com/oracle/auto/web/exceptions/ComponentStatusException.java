package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class ComponentStatusException extends WebExceptionBase {
	public ComponentStatusException(WebDriverSeleniumPageEx page, String locator, String error) {
		super( page,"\n\tComponent          = " + locator
				+ "\n\tDetail Error       = " + error
				+ "\n\n");
	}
}
