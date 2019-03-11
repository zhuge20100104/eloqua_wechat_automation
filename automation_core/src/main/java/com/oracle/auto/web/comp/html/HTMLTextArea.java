package com.oracle.auto.web.comp.html;

import org.springframework.stereotype.Component;

@Component
public class HTMLTextArea extends HTMLTextbox {

    private boolean allowEmpty = true;

    public HTMLTextArea(String locator) {
        this(locator, true);
    }

    public HTMLTextArea(String locator, boolean allowEmpty) {
        super(locator);
        this.allowEmpty = allowEmpty;
    }

    public HTMLTextArea() {
    }

    public void click() {
        waitForReady();
        doClick();
    }

    public String getAttribute(String attribute) {
        waitForReady();
        return doGetAttribute(attribute);
    }

    public String getValueAttribute() {
        waitForReady();
        return doGetAttribute("value");
    }

}
