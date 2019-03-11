package com.oracle.auto.mobile.components.elements.webview;

import com.oracle.auto.mobile.components.WebViewComponentBase;

public class WebAnchor extends WebViewComponentBase {

    public WebAnchor( String locator) {
        super(locator);
    }

    public void click(){
        waitForReady();
        super.click();
    }

    public void singleFingerTap(){
        waitForReady();
        super.singleFingerTap();
    }

    public String getText(){
        waitForReady();
        return super.getValue();
    }

}
