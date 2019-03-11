package com.oracle.auto.mobile.components.elements.webview;

import com.oracle.auto.mobile.components.WebViewComponentBase;

public class WebButton extends WebViewComponentBase {

    public WebButton( String locator) {
        super(locator);
    }

    public void click(){
        //waitForReady();
        super.click();
    }

    public void singleFingerTap(){
        //waitForReady();
        super.singleFingerTap();
    }

}
