package com.oracle.auto.web.comp.html;


import org.springframework.stereotype.Component;

@Component
public class HTMLParagraph extends HTMLComponentBase {
    public HTMLParagraph(String locator) {
        super(locator);
    }

    public String getText() {
        waitForReady();
        return doGetText();
    }

}
