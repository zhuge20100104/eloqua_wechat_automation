package com.oracle.auto.web.comp.html;

import org.springframework.stereotype.Component;

@Component
public class HTMLUl extends HTMLComponentBase {

    public HTMLUl(String locator) {
        super(locator);
    }

    public String getClassAttribute() {
        waitForReady();
        return doGetAttribute("class");
    }

}
