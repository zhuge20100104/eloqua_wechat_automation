package com.oracle.auto.mobile.exceptions;

import com.oracle.auto.mobile.driver.MobileDriverEx;

public class MobileAssertionError extends MobileExceptionBase {

    public MobileAssertionError(MobileDriverEx driver, AssertionError error) {
        super(driver, error.getMessage(), error);
    }
}