package com.oracle.auto.mobile.exceptions;


import com.oracle.auto.mobile.driver.MobileDriverEx;

public class ComponentGetPropertyException extends MobileExceptionBase {
    public ComponentGetPropertyException(MobileDriverEx driver, String locator, String property, String message) {
        super(driver,
                "\n\tError              = fail to get property of a component. "
                        + "\n\tComponent          = " + locator
                        + "\n\tProperty           = " + property
                        + "\n\tDetail Error       = " + message
                        + "\n\n");
    }

    public ComponentGetPropertyException(MobileDriverEx driver, String locator, String property, Exception ex) {
        super(driver, "\nfail to get property of a component. "
                        + "\n\tComponent          = " + locator
                        + "\n\tProperty           = " + property
                        + "\n\n"
                , ex);
    }
}
