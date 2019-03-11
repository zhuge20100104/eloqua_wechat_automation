package com.oracle.auto.web.comp.interfaces;

import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

public interface IComponent {

    // if disabled
    boolean isEnabled();

    // isReady for read and write: basic Ext is loaded, then element present, rendered, visible and valid
    boolean isReady();

    boolean hideDefault();

    String getLastError();

    void waitForElementReady(int timeout);

    void waitForElementEnabled(int timeout);

    void setPage(WebDriverSeleniumPageEx page);

    WebDriverSeleniumPageEx getPage();

    void setConfig(String jsonValue);
}
