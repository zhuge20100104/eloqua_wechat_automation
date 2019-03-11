package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class PageNoItemException extends WebExceptionBase {
	public PageNoItemException(WebDriverSeleniumPageEx page, String pageName, String elment, String error) {
		super(page,
				"\n\tError              = there's no certain item in page for further operate. "
				+ "\n\tSuppose Element    = " + elment 
				+ "\n\tCurrent page       = " + pageName
				+ "\n\tDetail Error       = " + error
				+ "\n\n");
	}
}
