/**
 *
 */
package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.stereotype.Component;

@Component
public class HTMLAnchor extends HTMLComponentBase implements IClickableComponent {
    public HTMLAnchor(String locator) {
        super(locator);
    }

    public HTMLAnchor() {
    }

    public void click() {
        //waitForReadyEnabled();
        waitForReady();
        WebDriverSeleniumPageEx.waitFor(2);
        doClick();
    }

    public String getText() {
        waitForReady();
        return page.getValue(getIdLocator());
    }

}
