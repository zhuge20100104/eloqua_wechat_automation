package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import org.springframework.stereotype.Component;

@Component
public class HTMLImageLink extends HTMLComponentBase implements IClickableComponent {

    public HTMLImageLink(String locator) {
        super(locator);
    }

    public HTMLImageLink() {

    }

    public void click() {
        waitForReady();
        doClick();
    }

}
