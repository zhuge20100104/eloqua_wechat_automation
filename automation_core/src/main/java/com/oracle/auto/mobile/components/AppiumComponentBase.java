package com.oracle.auto.mobile.components;

import com.oracle.auto.mobile.exceptions.ComponentNotPresentException;
import com.oracle.auto.web.utility.PropertyConfiger;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.SwipeElementDirection;
import io.appium.java_client.TouchAction;

public abstract class AppiumComponentBase extends MobileComponentBase {
    private static final int COMPONENT_WAIT_READY_TIMEOUT = PropertyConfiger.instance().getEnvData("mobile.component.all.ready.timeout", 30);

    AppiumComponentBase(String locator) { this(locator, 0); }

    AppiumComponentBase(String locator, int element_index) {
        this.locator = locator;
        this.element_index = element_index;
    }

    public abstract void setContext();

//    protected void waitForReady() {
//        isVisible();
//        isPresent();
//    }

    protected void waitForReady() {
        getMobileDriverEx().setImplicitlyWaitTime(1);
        waitForElementReady(PropertyConfiger.instance().getEnvData("component." + getClass().getSimpleName() + ".ready.timeout", COMPONENT_WAIT_READY_TIMEOUT));
        getMobileDriverEx().setDefaultImplicitylyWaitTime();
    }

    private void waitForElementReady(int timeout) {
        int i = 0;
        int waitUnit = 1;
        while (!(isPresent() && isVisible())) {
            if ((i += waitUnit) > timeout) {
                getMobileDriverEx().setDefaultImplicitylyWaitTime();
                throw new ComponentNotPresentException(getMobileDriverEx(), locator, "time out to wait for the component to be ready in " + timeout + " seconds");
            }
            try {
                Thread.sleep(waitUnit * 1000);
                waitUnit = 1;
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    public void click() {
        //setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);
        mobileElement.click();
    }

    protected void setValue(String value) {
        //setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);
        mobileElement.click();
        mobileElement.clear();
        mobileElement.sendKeys(value);
    }

    protected String getValue() {
        //setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);
        return mobileElement.getText();
    }

    private void swipe(SwipeElementDirection swipeElementDirection, int duration, int... offsets) {
        //setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);

        if (offsets.length > 0) {
            switch (swipeElementDirection) {
                case UP:
                    mobileElement.swipe(SwipeElementDirection.UP, offsets[0], offsets[1], duration);
                    break;

                case DOWN:
                    mobileElement.swipe(SwipeElementDirection.DOWN, offsets[0], offsets[1], duration);
                    break;

                case LEFT:
                    mobileElement.swipe(SwipeElementDirection.LEFT, offsets[0], offsets[1], duration);
                    break;

                case RIGHT:
                    mobileElement.swipe(SwipeElementDirection.RIGHT, offsets[0], offsets[1], duration);
                    break;
            }
        } else {
            switch (swipeElementDirection) {
                case UP:
                    mobileElement.swipe(SwipeElementDirection.UP, duration);
                    break;

                case DOWN:
                    mobileElement.swipe(SwipeElementDirection.DOWN, duration);
                    break;

                case LEFT:
                    mobileElement.swipe(SwipeElementDirection.LEFT, duration);
                    break;

                case RIGHT:
                    mobileElement.swipe(SwipeElementDirection.RIGHT, duration);
                    break;

            }
        }
    }

    protected void swipeUp(int duration, int... offsets) {
        setContext();
        waitForReady();
        this.swipe(SwipeElementDirection.UP, duration, offsets);
    }

    protected void swipeDown(int duration, int... offsets) {
        setContext();
        waitForReady();
        this.swipe(SwipeElementDirection.DOWN, duration, offsets);
    }

    protected void swipeLeft(int duration, int... offsets) {
        setContext();
        waitForReady();
        this.swipe(SwipeElementDirection.LEFT, duration, offsets);
    }

    protected void swipeRight(int duration, int... offsets) {
        setContext();
        waitForReady();
        this.swipe(SwipeElementDirection.RIGHT, duration, offsets);
    }

    public void tap(int fingers, int duration) {
        setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);
        //mobileElement.click();
        getDriver().tap(fingers, mobileElement, duration);
    }


    protected void singleFingerTap() {
        //setContext();
        waitForReady();
        this.tap(1, 1500);
    }

    protected void doubleFingerTap() {
        waitForReady();
        this.tap(2, 1500);
    }

    protected void touch() {
        setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);
        TouchAction action = new TouchAction(getDriver());
        action.press(mobileElement).perform();
    }

    protected void multiTouch(int x, int y, int p, int q, int a, int b, int m, int n) {
        setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);

        MultiTouchAction multiTouch = new MultiTouchAction(getDriver());
        TouchAction action0 = new TouchAction(getDriver()).press(mobileElement, x, y).moveTo(mobileElement, p, q).release();
        TouchAction action1 = new TouchAction(getDriver()).press(mobileElement, a, b).moveTo(mobileElement, m, n).release();
        multiTouch.add(action0).add(action1).perform();
    }

    protected void moveTo(int x, int y) {
        setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);

        TouchAction action = new TouchAction(getDriver());
        action.press(mobileElement).moveTo(mobileElement, x, y).release().perform();
    }

    protected void scrollUp() {}

    protected void scrollDown() {}

    protected void scrollLeft() {}

    protected void scrollRight() {}

    protected void pinch() {
        setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);
        mobileElement.pinch();
    }

    protected void zoom() {
        setContext();
        waitForReady();
        MobileElement mobileElement = (MobileElement) getDriver().findElements(getMobileDriverEx().parseBy(locator)).get(element_index);
        mobileElement.zoom();
    }

    protected void selectFromDropdown(String value) { }
}
