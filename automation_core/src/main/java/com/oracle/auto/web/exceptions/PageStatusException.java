package com.oracle.auto.web.exceptions;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

@SuppressWarnings("serial")
public class PageStatusException extends WebExceptionBase {
    public PageStatusException(WebDriverSeleniumPageEx page, String pageName, String error) {
        super(page, "\n\tError              = there're error on a page."
                + "\n\tPage object        = " + pageName
                + "\n\tDetail Error       = " + error
                + "\n\n");
    }
}
