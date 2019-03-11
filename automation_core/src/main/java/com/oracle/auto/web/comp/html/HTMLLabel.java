package com.oracle.auto.web.comp.html;

import org.springframework.stereotype.Component;

@Component
public class HTMLLabel extends HTMLComponentBase {
    public HTMLLabel(String locator) {
        super(locator);
    }

    public String getTextContent() {
        waitForReady();
        return doGetText();
    }

    public String getAttribute(String attributeName) {
        waitForReady();
        return doGetAttribute(attributeName);
    }
}
