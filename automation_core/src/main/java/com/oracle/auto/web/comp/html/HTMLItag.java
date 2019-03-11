package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import org.springframework.stereotype.Component;

@Component
public class HTMLItag extends HTMLComponentBase implements
        IClickableComponent {
    public HTMLItag(String locator) {
        super(locator);
    }

    public HTMLItag() {
    }

    public void click() {
        // waitForReadyEnabled();
        waitForReady();
        doClick();
    }

    public String getText() {
        // waitForReadyEnabled();
        waitForReady();
        return page.getValue(locator);
    }


    public String getClassAttribute() {
        waitForReady();
        return doGetAttribute("class");
    }
}
