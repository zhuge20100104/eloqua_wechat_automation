package com.oracle.auto.mobile.components.elements.webview;


import com.oracle.auto.mobile.components.WebViewComponentBase;

public class WebDiv extends WebViewComponentBase {

    public WebDiv( String locator) {
        super( locator);
    }

    public void click(){
        waitForReady();
        super.click();
    }

    public String getText() {
        waitForReady();
        return super.getValue();
    }
}
