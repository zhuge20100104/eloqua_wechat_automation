package com.oracle.auto.mobile.exceptions;


import com.oracle.auto.mobile.driver.MobileDriverEx;

public class ComponentSetPropertyException extends MobileExceptionBase {
    public ComponentSetPropertyException(MobileDriverEx driver, String locator, String property, Exception ex) {
        super(driver,
                "\n\tError              = fail to set component property. "
                        + "\n\tComponent          = " + locator
                        + "\n\tProperty           = " + property
                        + "\n\n"
                , ex);
    }

    public ComponentSetPropertyException(MobileDriverEx driver, String locator, String property, String message) {
        super(driver, "\nfail to set component property. "
                + "\n\tComponent          = " + locator
                + "\n\tProperty           = " + property
                + "\n\tDetail Error       = " + message
                + "\n\n");
    }
}
