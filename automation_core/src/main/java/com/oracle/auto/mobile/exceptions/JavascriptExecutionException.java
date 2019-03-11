package com.oracle.auto.mobile.exceptions;


import com.oracle.auto.mobile.driver.MobileDriverEx;

public class JavascriptExecutionException extends MobileExceptionBase {
    public JavascriptExecutionException(MobileDriverEx driver, String script, Throwable ex) {
        super(driver,
                "\n\tError              = Component is not clickable. "
                        + "\n\tComponent          = " + script
                        + "\n\n"
                , ex);
    }

    public JavascriptExecutionException(MobileDriverEx driver, String script, String error) {
        super(driver,
                "\n\tError              = Component is not clickable. "
                        + "\n\tComponent          = " + script
                        + "\n\tDetail Error       = " + error
                        + "\n\n");
    }
}
