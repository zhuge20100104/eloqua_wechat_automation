package com.oracle.auto.web.comp.html;

import org.springframework.stereotype.Component;

@Component
public class HTMLHeader extends HTMLComponentBase {

    public HTMLHeader(String locator) {
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
