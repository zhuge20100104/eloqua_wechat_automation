package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class PageInitException extends WebExceptionBase {
	public PageInitException(WebDriverSeleniumPageEx page, String pageName, String error) {
		super(page,
				  "\n\tError              = " + error
				+ "\n\tSuppose Page       = " + pageName
				+ "\n\n");
		
		
	}
}
