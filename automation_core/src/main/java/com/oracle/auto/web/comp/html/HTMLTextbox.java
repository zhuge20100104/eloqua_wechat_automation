package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IValuable;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

@Component
public class HTMLTextbox extends HTMLComponentBase implements IValuable {
    private boolean allowEmpty = true;

    public HTMLTextbox(String locator) {
        this(locator, true);
    }

    public HTMLTextbox(String locator, boolean allowEmpty) {
        super(locator);
        this.allowEmpty = allowEmpty;
    }

    public HTMLTextbox() {
    }

    @Override
    public boolean isReady() {
        return super.isReady() && (allowEmpty ? true : !doGetValue().isEmpty());
    }

    // value
    public String getValue() {
        waitForReady();
        WebDriverSeleniumPageEx.waitFor(1);
        return doGetValue();
    }

    public void setValue(String value) {
        waitForReady();
        doSetValue(value);
    }

    public void insertValue(String value) {
        waitForReady();
        doInsertValue(value);
    }

    public void insertLineBreak() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).perform();
    }

    public String getDisplayValue() {
        waitForReady();
        return getMethodPropIgnoreUndefined(".textContent.trim()").str();
    }

    public void clearValue() {
        waitForReady();
        doClearText();
    }
}
