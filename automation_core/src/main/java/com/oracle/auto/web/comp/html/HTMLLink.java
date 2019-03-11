package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.stereotype.Component;

@Component
public class HTMLLink extends HTMLComponentBase implements IClickableComponent {

    public HTMLLink(String locator) {
        super(locator);
    }

    public HTMLLink() {
    }

    public void click() {
        waitForReady();
        WebDriverSeleniumPageEx.waitFor(2);
        doClick();
    }

    // this method is created to workaround the file upload link issue do not use this regularly
   /* public void clickAtLink(String coordinates) {
        waitForReady();
        WebDriverSeleniumPageEx.waitFor(2);
        doClickAt(coordinates);
    }*/

    public String getClassAttribute() {
        waitForReady();
        return doGetAttribute("class");
    }

    public String getTextContent() {
        waitForReady();
        return doGetText();
    }

    public String getDisplayValue() {
        return getMethodPropIgnoreUndefined(".textContent.trim()").str();
    }

    public void clickByScript() {
        runNoReturnMethod(".click()");
    }
}
