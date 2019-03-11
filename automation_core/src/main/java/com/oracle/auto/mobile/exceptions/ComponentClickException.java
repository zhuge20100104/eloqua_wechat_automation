package com.oracle.auto.mobile.exceptions;


import com.oracle.auto.mobile.driver.MobileDriverEx;

public class ComponentClickException extends MobileExceptionBase {
    public ComponentClickException(MobileDriverEx driver, String locator, Throwable ex) {
        super(driver,
                "\n\tError              = Component is not clickable. "
                        + "\n\tComponent          = " + locator
                        + "\n\n"
                , ex);
    }

    public ComponentClickException(MobileDriverEx driver, String locator, String error) {
        super(driver,
                "\n\tError              = Component is not clickable. "
                        + "\n\tComponent          = " + locator
                        + "\n\tDetail Error       = " + error
                        + "\n\n");
    }
}
