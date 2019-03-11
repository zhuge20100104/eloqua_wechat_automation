package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class ComponentNotEnabledException extends WebExceptionBase {
	public ComponentNotEnabledException(WebDriverSeleniumPageEx page,String locator, Exception ex) {
		super(page,
				  "\n\tError              = a component is disabled. "
				+ "\n\tComponent          = " + locator
				+ "\n\n"
				, ex);
	}
	public ComponentNotEnabledException(WebDriverSeleniumPageEx page, String locator, String error) {
		super(page,
				  "\n\tError              = a component is disabled. "
				+ "\n\tComponent          = " + locator
				+ "\n\tDetail Error       = " + error
				+ "\n\n");
	}
}
