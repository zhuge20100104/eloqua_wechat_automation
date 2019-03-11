package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class JavascriptExecutionException extends WebExceptionBase {
	public JavascriptExecutionException(WebDriverSeleniumPageEx page, String scriptToRun, String error) {
		super(page,
				  "\n\tError              = a javascript is failed to run."
				+ "\n\tScript             = " + scriptToRun
				+ "\n\tDetail Error       = " + error
				+ "\n\n");
	}
	
	public JavascriptExecutionException(WebDriverSeleniumPageEx page, String scriptToRun, Exception ex) {
		super(page,
				  "\n\tError              = a javascript is failed to run."
				+ "\n\tScript             = " + scriptToRun
				+ "\n\n"
				, ex);
	}
}
