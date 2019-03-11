package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class ComponentGetPropertyException extends WebExceptionBase {
	public ComponentGetPropertyException(WebDriverSeleniumPageEx page,String locator, String property, String message) {
		super(page,
				  "\n\tError              = fail to get property of a component. "
				+ "\n\tComponent          = " + locator
				+ "\n\tProperty           = " + property
				+ "\n\tDetail Error       = " + message
				+ "\n\n");
	}
	
	public ComponentGetPropertyException(WebDriverSeleniumPageEx page, String locator, String property, Exception ex) {
		super( page,"\nfail to get property of a component. "
				+ "\n\tComponent          = " + locator
				+ "\n\tProperty           = " + property
				+ "\n\n"
				, ex);
	}
	
}
