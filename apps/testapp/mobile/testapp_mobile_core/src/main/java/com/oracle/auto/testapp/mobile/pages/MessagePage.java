package com.oracle.auto.testapp.mobile.pages;

import com.oracle.auto.mobile.components.elements.webview.WebDiv;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.testapp.mobile.ancestor.MobileWebScreenBase;


import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 8/30/2017.
 */

public class MessagePage extends MobileWebScreenBase {
    private WebDiv readMore;

    public void setReadMore(WebDiv readMore) {
        this.readMore = readMore;
    }

    public WebDiv getReadMore() {
        return readMore;
    }

    @PostConstruct
    public void init() {
        //registerComp(readMore);
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }

}
