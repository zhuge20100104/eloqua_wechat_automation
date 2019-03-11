package com.oracle.auto.mobile.components;

import com.google.gson.annotations.Expose;
import com.oracle.auto.mobile.components.interfaces.IMobileComponent;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.mobile.exceptions.ComponentNotPresentException;
import com.oracle.auto.mobile.exceptions.ComponentNotVisibleException;
import io.appium.java_client.AppiumDriver;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class MobileComponentBase implements IMobileComponent {
    private static final int ELEMENT_PRESENT_TIMEOUT = 1;
    private static final int ELEMENT_VISIBLE_TIMEOUT = 1;
    private static Logger log = Logger.getLogger(MobileComponentBase.class);
    private String lastError;
    private MobileDriverEx mobileDriverEx = null;
    protected AppiumDriver driver = null;

    MobileComponentBase() {
    }

    public AppiumDriver getDriver() {
        return getMobileDriverEx().getDriver();
    }

    @Expose
    protected String locator = "";

    @Expose
    String rawLocator = "";

    @Expose
    int element_index = 0;

    public void setMobileDriverEx(MobileDriverEx mobileDriverEx) {
        this.mobileDriverEx = Objects.requireNonNull(mobileDriverEx);
        this.driver = Objects.requireNonNull(mobileDriverEx.getDriver());
    }

    public MobileDriverEx getMobileDriverEx() {
        if (mobileDriverEx != null) {
            return mobileDriverEx;
        } else {
            return MobileDriverFactory.instance().getLastMobileDriverSession();
        }
    }

    @Override
    public boolean isPresent() {
        try {
            setIDLocator();
        } catch (Exception ex) {
            return false;
        }

        try {
            getMobileDriverEx().setImplicitlyWaitTime(1);
            (new WebDriverWait(getDriver(), ELEMENT_PRESENT_TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(getMobileDriverEx().parseBy(locator)));
            getMobileDriverEx().setDefaultImplicitylyWaitTime();
        } catch (Exception ex) {
            getMobileDriverEx().setDefaultImplicitylyWaitTime();
            return false;
            //throw new ComponentNotPresentException(getMobileDriverEx(), locator, "Element not present after " + ELEMENT_VISIBLE_TIMEOUT + " seconds");
        }
        return true;
    }

    @Override
    public boolean isVisible() {
        try {
            setIDLocator();
        } catch (Exception ex) {
            return false;
        }

        try {
            (new WebDriverWait(getDriver(), ELEMENT_VISIBLE_TIMEOUT)).until(ExpectedConditions.visibilityOfElementLocated(getMobileDriverEx().parseBy(locator)));
        } catch (Exception ex) {
            return false;
            //throw new ComponentNotVisibleException(getMobileDriverEx(), locator, "Element not visible after " + ELEMENT_VISIBLE_TIMEOUT + " seconds");
        }
        return true;

    }

    @Override
    public void waitForElementPresent(int timeout) {
        try {
            setIDLocator();
            (new WebDriverWait(getDriver(), timeout)).until(ExpectedConditions.presenceOfElementLocated(getMobileDriverEx().parseBy(locator)));
        } catch (Exception ex) {
            throw new ComponentNotPresentException(getMobileDriverEx(), locator, "Element not present after " + timeout + " seconds");
        }
    }

    @Override
    public void waitForElementVisible(int timeout) {
        try {
            (new WebDriverWait(getDriver(), timeout)).until(ExpectedConditions.visibilityOfElementLocated(getMobileDriverEx().parseBy(locator)));
        } catch (Exception ex) {
            throw new ComponentNotVisibleException(getMobileDriverEx(), locator, "Element not visible after " + timeout + " seconds");
        }
    }

    @Override
    public String getLastError() {
        return doGetLastError();
    }

    private String doGetLastError() {
        return lastError;
    }

    void doSetLastError(String err) {
        lastError = err;
    }

    private void setIDLocator() {
        //  WebDriverHelper.waitForAngular(getMobileDriverEx().getDriver());
        if (locator.startsWith("BDD_")) {
            String str = getMobileDriverEx().executeScript("return BDD_GenId(" + locator + ")");
            locator = "id=" + str;
        }
    }

    public String getLocator() {
        return locator;
    }

    public String getRawLocator() {
        return rawLocator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }
}