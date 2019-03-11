package com.oracle.auto.web.comp.html;

import org.springframework.stereotype.Component;

@Component
public class HTMLListItem extends HTMLComponentBase {

    public HTMLListItem(String locator) {
        super(locator);
    }

    public HTMLListItem() {
    }

    public String getClassAttribute() {
        waitForReady();
        return doGetAttribute("class");
    }

    public String getText() {
        waitForReady();
        return super.doGetText();
    }

    public void click() {
        waitForReady();
        super.doClick();
    }
}
