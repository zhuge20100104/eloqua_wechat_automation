package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import org.springframework.stereotype.Component;

@Component
public class HTMLSpan extends HTMLComponentBase implements IClickableComponent {

    public HTMLSpan(String locator) {
        super(locator);
    }

    public HTMLSpan() {
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

        String script = ";var comp = " + locator + ";comp.innerHTML='" + value + "'";
        page.executeScriptEx(script);
    }

    public String getDisplayValue() {
        return getMethodPropIgnoreUndefined(".textContent.trim()").str();
    }


    public String getClassAttribute() {
        waitForReady();
        return doGetAttribute("class");
    }


    public String getTextColor() {
        waitForReady();
        return doGetCssAttribute("color");
    }

    public String getAttribute(String attribute) {
        waitForReady();
        return doGetAttribute(attribute);
    }
}
