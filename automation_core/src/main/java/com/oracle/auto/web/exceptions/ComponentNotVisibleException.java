/**
 * 
 */
package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class ComponentNotVisibleException extends WebExceptionBase {
	public ComponentNotVisibleException(WebDriverSeleniumPageEx page,
			String locator, Exception ex) {
		super( page,"\n\tError              = a component is not visible. "
				+ "\n\tComponent          = " + locator + "\n\n", ex);
	}

	public ComponentNotVisibleException(WebDriverSeleniumPageEx page,
			String locator, String error) {
		super( page,"\n\tError              = a component is not visible. "
				+ "\n\tComponent          = " + locator
				+ "\n\tDetail Error       = " + error + "\n\n");
	}
}