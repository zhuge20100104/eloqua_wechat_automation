package com.oracle.auto.mobile.exceptions;

import com.oracle.auto.mobile.driver.MobileDriverEx;

public class ComponentNotVisibleException extends MobileExceptionBase {


    public ComponentNotVisibleException(MobileDriverEx driver, String locator, Throwable ex) {
        super(driver,
                "\n\tError              = a component is not visible. "
                        + "\n\tComponent          = " + locator
                        + "\n\n"
                , ex);
    }

    public ComponentNotVisibleException(MobileDriverEx driver, String locator, String error) {
        super(driver,
                "\n\tError              = a component is not visible. "
                        + "\n\tComponent          = " + locator
                        + "\n\tDetail Error       = " + error
                        + "\n\n");
    }
}
