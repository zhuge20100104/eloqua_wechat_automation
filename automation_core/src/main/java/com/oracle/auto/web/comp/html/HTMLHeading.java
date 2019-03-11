package com.oracle.auto.web.comp.html;

public class HTMLHeading extends HTMLComponentBase {

    public HTMLHeading(String locator) {
        super(locator);
    }

    public String getText() {
        waitForReady();
        return super.doGetText();
    }

    public String getDisplayValue() {
        return getMethodPropIgnoreUndefined(".textContent.trim()").str();
    }

}
