package com.oracle.auto.web.pages.object;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

public interface IPage {
    void setPage(WebDriverSeleniumPageEx page);

    WebDriverSeleniumPageEx page();

    void setPageMaker(IPageMaker maker);

    void loadPageConfig();

    void waitForPageReady();
}
