package com.oracle.auto.mobile.exceptions;

import com.oracle.auto.mobile.driver.MobileDriverEx;

public class ComponentNotEnabledException extends MobileExceptionBase {


    public ComponentNotEnabledException(MobileDriverEx driver, String locator, Throwable ex) {
        super(driver,
                "\n\tError              = a component is disabled. "
                        + "\n\tComponent          = " + locator
                        + "\n\n"
                , ex);
    }

    public ComponentNotEnabledException(MobileDriverEx driver, String locator, String error) {
        super(driver,
                "\n\tError              = a component is disabled. "
                        + "\n\tComponent          = " + locator
                        + "\n\tDetail Error       = " + error
                        + "\n\n");
    }
}
