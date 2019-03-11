package com.oracle.auto.web.comp.html;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.IValueDisplay;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.stereotype.Component;

// xtype: displayfield
@Component
public class HTMLValueDisplay extends HTMLComponentBase implements IValueDisplay {
    @Expose
    private boolean allowEmpty = true;

    public HTMLValueDisplay() {

    }

    public HTMLValueDisplay(String locator) {
        super(locator);
    }

    public HTMLValueDisplay(String locator, boolean allowEmpty) {
        super(locator);
        this.allowEmpty = allowEmpty;
    }

    @Override
    public boolean isReady() {
        boolean ret = super.isReady();
        if (!ret) return ret;
        if (allowEmpty) return ret;

        // basing on test, the value display might disappear in a second and appear again.
        WebDriverSeleniumPageEx.waitFor(1);
        return super.isReady() && !super.doGetText().isEmpty();
    }

    @Override
    public boolean isEnabled() {
        // no need to be enabled or not
        return true;
    }

    // displayed value
    public String getValue() {
        waitForReady();
        return super.doGetText();

    }

    /**
     * backward compatible
     *
     * @return innerText of text
     */
    public String getDisplayValue() {
        return getValue();
    }
}
