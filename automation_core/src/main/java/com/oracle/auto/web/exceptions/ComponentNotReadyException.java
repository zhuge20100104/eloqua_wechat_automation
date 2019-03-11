package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class ComponentNotReadyException extends WebExceptionBase {
	public ComponentNotReadyException(WebDriverSeleniumPageEx page,String locator, Exception ex) {
		super(page,
				  "\n\tError              = a component doens't present or partially not rendered/visible. "
				+ "\n\tComponent          = " + locator
				+ "\n\n"
				, ex);
	}
	public ComponentNotReadyException(WebDriverSeleniumPageEx page, String locator, String error) {
		super(page,
				  "\n\tError              = a component doens't present or partially not rendered/visible. "
				+ "\n\tComponent          = " + locator
				+ "\n\tDetail Error       = " + error
				+ "\n\n");
	}
}
