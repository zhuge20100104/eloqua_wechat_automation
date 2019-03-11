package com.oracle.auto.mobile.components.elements.webview;

import com.oracle.auto.mobile.components.WebViewComponentBase;

public class WebSpan extends WebViewComponentBase {

    public WebSpan( String locator) {
        super( locator);
    }

    public String getText() {
        waitForReady();
        return super.getValue();
    }
}
