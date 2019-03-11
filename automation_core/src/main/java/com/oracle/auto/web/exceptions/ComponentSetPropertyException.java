package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class ComponentSetPropertyException extends WebExceptionBase {
	public ComponentSetPropertyException(WebDriverSeleniumPageEx page,String locator, String property, Exception ex) {
		super(page,
				  "\n\tError              = fail to set component property. "
				+ "\n\tComponent          = " + locator
				+ "\n\tProperty           = " + property
				+ "\n\n"
				, ex);
	}
	public ComponentSetPropertyException(WebDriverSeleniumPageEx page, String locator, String property, String message) {
		super(page, "\nfail to set component property. "
				+ "\n\tComponent          = " + locator
				+ "\n\tProperty           = " + property
				+ "\n\tDetail Error       = " + message
				+ "\n\n");
	}
}
