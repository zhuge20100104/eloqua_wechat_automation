package com.oracle.auto.mobile.components.interfaces;

import com.oracle.auto.mobile.driver.MobileDriverEx;

public interface IMobileComponent {

    boolean isVisible();

    boolean isPresent();

    void waitForElementPresent(int timeout);

    void waitForElementVisible(int timeout);

    void setMobileDriverEx(MobileDriverEx driver);

    MobileDriverEx getMobileDriverEx();

    String getLastError();

}
