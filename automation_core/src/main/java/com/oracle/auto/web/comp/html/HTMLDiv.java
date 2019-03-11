package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.stereotype.Component;

@Component
public class HTMLDiv extends HTMLComponentBase implements IClickableComponent {

    public HTMLDiv(String locator) {
        super(locator);
    }

    public HTMLDiv() {

    }

    @Override
    public void click() {
        waitForReady();
        doClick();
    }

    public void contextClick() {
        waitForReady();
        doContextClick();
    }

    public void doubleClick(){
        waitForReady();
        doDoubleClick();
    }

    public String getText() {
        waitForReady();
        return super.doGetText();
    }

    public void setValue(String value) {
        waitForReady();
        WebDriverSeleniumPageEx.waitFor(1);
        doSetValue(value);
    }

    public String getClassAttribute() {
        waitForReady();
        return doGetAttribute("class");
    }

    public String getDisplayValue() {
        return getMethodPropIgnoreUndefined(".textContent.trim()").str();
    }

    public void moveToElement() {
        waitForReady();
        doMouseOver();
    }

    public String getAttribute(String attribute) {
        waitForReady();
        return doGetAttribute(attribute);
    }
}
