package com.oracle.auto.web.comp.html;

import org.springframework.stereotype.Component;

@Component
public class HTMLCheckbox extends HTMLComponentBase {

    public HTMLCheckbox(String locator) {
        super(locator);
    }

    public void click() {
        waitForReady();
        doClick();
    }

    public boolean isChecked() {
        waitForReady();
        return page.isSelected(getIdLocator());
    }

    public boolean checked() {
        return page.isChecked(locator);
    }

    public boolean check(boolean checked) {
        for (int i = 0; i < 3; i++) {
            if (checked == checked()) {
                return true;
            }
            clickToCheck();
        }
        return false;
    }

    public void check() {
        if (isChecked())
            return;
        doClick();
    }

    public void unCheck() {
        if (!isChecked())
            return;
        doClick();
    }

    public void clickToCheck() {
        if (!checked()) {
            page.click(locator);
        }
    }

    public void clickToUncheck() {
        if (checked()) {
            page.click(locator);
        }
    }

    public String getAttribute(String attribute) {
        waitForReady();
        return doGetAttribute(attribute);
    }

    public String getTheAttribute(String attribute) {
       // waitForReady();
        return doGetAttribute(attribute);
    }
}
