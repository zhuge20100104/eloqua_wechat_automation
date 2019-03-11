package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.stereotype.Component;

@Component
public class HTMLInput extends HTMLComponentBase implements IClickableComponent {

    public HTMLInput(String locator) {
        super(locator);
    }

    @Override
    public void click() {
        waitForReady();
        doClick();
    }

    public String getTextContent() {
        waitForReady();
        return doGetText();
    }

    public void setValue(String value) {
        waitForReady();
        // this wait is needed in some places like transmittal page
        WebDriverSeleniumPageEx.waitFor(1);
        doSetValue(value);
    }

    public void setValueWithENTER(String value) {
        waitForReady();
        // this wait is needed in some places like transmittal page
        WebDriverSeleniumPageEx.waitFor(1);
        doSetValue(value);
        sendEnterKeys();
    }


    public void insertValue(String value) {
        waitForReady();
        doInsertValue(value);
    }

    public void clearDate() {
        waitForReady();
        WebDriverSeleniumPageEx.waitFor(1);
        doClearDate();
    }

    public String getValueAttribute() {
        waitForReady();
        return doGetAttribute("value");
    }

    public void setAttribute(String attributeName, String attributeValue) {
        String baseScript = this.locator.substring(0, locator.length() - 3);
        page.executeScript("return " + baseScript + ".attr('" + attributeName + "','" + attributeValue + "')");
    }

    public String getAttribute(String attributeName) {
        waitForReady();
        return doGetAttribute(attributeName);
    }

}
