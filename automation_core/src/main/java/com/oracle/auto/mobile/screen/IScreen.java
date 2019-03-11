package com.oracle.auto.mobile.screen;


import com.oracle.auto.mobile.driver.MobileDriverEx;

public interface IScreen {
    MobileDriverEx getMobileDriverEx();

    void setMobileDriverEx(MobileDriverEx mobileDriverEx);

    void setScreenMaker(IScreenMaker maker);

    void waitForScreenReady();
}
