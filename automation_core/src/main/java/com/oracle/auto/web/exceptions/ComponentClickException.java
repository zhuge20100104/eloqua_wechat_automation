package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class ComponentClickException extends WebExceptionBase {
	public ComponentClickException(WebDriverSeleniumPageEx page,String locator, Exception ex) {
		super(page,
				  "\n\tError              = fail to click a component. "
				+ "\n\tComponent          = " + locator
				+ "\n\n"
				, ex);
	}
	public ComponentClickException(WebDriverSeleniumPageEx page, String locator, String message) {
		super(page,
				  "\n\tError              = fail to click a component. "
				+ "\n\tComponent          = " + locator
				+ "\n\tDetail Error       = " + message
				+ "\n\n");
	}
}
