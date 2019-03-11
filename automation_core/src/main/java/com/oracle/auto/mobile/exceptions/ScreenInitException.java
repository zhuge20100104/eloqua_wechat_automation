package com.oracle.auto.mobile.exceptions;


import com.oracle.auto.mobile.driver.MobileDriverEx;

public class ScreenInitException extends MobileExceptionBase {
    public ScreenInitException(MobileDriverEx driver, String screenName, String error) {
        super(driver,
                "\n\tError              = " + error
                        + "\n\tExpected Screen       = " + screenName
                        + "\n\n");


    }

    public ScreenInitException(MobileDriverEx driver, String screenName, String error, Throwable ex) {
        super(driver,
                "\n\tError              = " + error
                        + "\n\tExpected Screen       = " + screenName
                        + "\n\n", ex);


    }


}
