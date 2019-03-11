package com.oracle.auto.mobile.exceptions;

import com.oracle.auto.mobile.driver.MobileDriverEx;

public class ComponentNotPresentException extends MobileExceptionBase {


    public ComponentNotPresentException(MobileDriverEx driver, String locator, Throwable ex) {
        super(driver,
                "\n\tError              = a component is not present. "
                        + "\n\tComponent          = " + locator
                        + "\n\n"
                , ex);
    }

    public ComponentNotPresentException(MobileDriverEx driver, String locator, String error) {
        super(driver,
                "\n\tError              = a component is not present. "
                        + "\n\tComponent          = " + locator
                        + "\n\tDetail Error       = " + error
                        + "\n\n");
    }
}
