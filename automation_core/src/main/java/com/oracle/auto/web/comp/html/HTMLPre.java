package com.oracle.auto.web.comp.html;

public class HTMLPre extends HTMLComponentBase {
    public HTMLPre(String locator) {
        super(locator);
    }

    public String getText() {
        waitForReady();
        return doGetText();
    }

}
