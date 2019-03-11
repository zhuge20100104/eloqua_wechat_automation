package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class FormValidationException extends WebExceptionBase {
	public FormValidationException(WebDriverSeleniumPageEx page, String pageName, String error) {
		super(page,
				  "\n\tError              = form is invalid with page's frontend validation, but still trying to submit the form. "
				+ "\n\tPage               = " + pageName
				+ "\n\tDetail Error       = " + error
				+ "\n\n");
	}
}
